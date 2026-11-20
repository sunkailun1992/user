package com.kellen.auth.keycloak;

import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import com.kellen.utils.json.JsonUtil;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Keycloak 账号同步：按官方 Admin REST API 建、改、停用、重置口令、删除账号。MockWebServer 充当 Admin API。
 */
class KeycloakAccountClientTest {

    private MockWebServer server;

    private KeycloakAccountClient client;

    @BeforeEach
    void start() throws Exception {
        server = new MockWebServer();
        server.start(InetAddress.getByName("127.0.0.1"), 0);
        String base = server.url("/admin/realms/github-dev").newBuilder().host("127.0.0.1").build().toString();
        client = new KeycloakAccountClient(RestClient.builder().baseUrl(base).build());
    }

    @AfterEach
    void stop() throws Exception {
        server.shutdown();
    }

    @Test
    void createAccountPostsUserWithUserIdAttributeAndTemporaryPassword() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(201)
                .addHeader("Location", server.url("/admin/realms/github-dev/users/kc-123").toString()));
        String subject = client.createAccount("u-new", "alice", "Alice", "Temp-Password-1");
        assertThat(subject).isEqualTo("kc-123");
        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getRequestUrl().encodedPath()).isEqualTo("/admin/realms/github-dev/users");
        Map<String, Object> body = JsonUtil.toBean(request.getBody().readUtf8(), Map.class);
        assertThat(body).containsEntry("username", "alice").containsEntry("firstName", "Alice").containsEntry("enabled", true)
                .containsEntry("requiredActions", List.of("UPDATE_PASSWORD"));
        assertThat(body.get("attributes")).isEqualTo(Map.of("user_id", List.of("u-new")));
        assertThat(body.get("credentials")).isEqualTo(List.of(Map.of("type", "password", "value", "Temp-Password-1", "temporary", true)));
    }

    @Test
    void createAccountConflictMeansUsernameTaken() {
        server.enqueue(new MockResponse().setResponseCode(409).setBody("{\"errorMessage\":\"User exists with same username\"}"));
        assertThatThrownBy(() -> client.createAccount("u-new", "alice", "Alice", "Temp-Password-1"))
                .isInstanceOfSatisfying(UserException.class, ex -> assertThat(ex.getReturnCode()).isEqualTo(ReturnCode.USERNAME_ALREADY_EXISTS));
    }

    @Test
    void serverErrorIsReportedWithoutResponseDetailsOrPassword() {
        server.enqueue(new MockResponse().setResponseCode(500).setBody("internal-detail-should-not-leak"));
        assertThatThrownBy(() -> client.createAccount("u-new", "alice", "Alice", "Temp-Password-1"))
                .isInstanceOf(UserException.class)
                .satisfies(ex -> assertThat(ex.getMessage()).doesNotContain("internal-detail-should-not-leak").doesNotContain("Temp-Password-1"));
    }

    @Test
    void updateProfileKeepsOtherAttributes() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).addHeader("Content-Type", "application/json")
                .setBody("{\"id\":\"kc-123\",\"username\":\"alice\",\"firstName\":\"Alice\",\"enabled\":true,"
                        + "\"attributes\":{\"user_id\":[\"u-1\"],\"locale\":[\"zh-CN\"]}}"));
        server.enqueue(new MockResponse().setResponseCode(204));
        client.updateProfile("kc-123", "alice2", "Alice Two");
        assertThat(server.takeRequest().getMethod()).isEqualTo("GET");
        RecordedRequest put = server.takeRequest();
        assertThat(put.getMethod()).isEqualTo("PUT");
        assertThat(put.getRequestUrl().encodedPath()).isEqualTo("/admin/realms/github-dev/users/kc-123");
        Map<String, Object> body = JsonUtil.toBean(put.getBody().readUtf8(), Map.class);
        assertThat(body).containsEntry("username", "alice2").containsEntry("firstName", "Alice Two").containsEntry("enabled", true);
        assertThat(body.get("attributes")).isEqualTo(Map.of("user_id", List.of("u-1"), "locale", List.of("zh-CN")));
    }

    @Test
    void setEnabledFalseKeepsOtherFields() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).addHeader("Content-Type", "application/json")
                .setBody("{\"id\":\"kc-123\",\"username\":\"alice\",\"enabled\":true,\"attributes\":{\"user_id\":[\"u-1\"]}}"));
        server.enqueue(new MockResponse().setResponseCode(204));
        client.setEnabled("kc-123", false);
        server.takeRequest();
        Map<String, Object> body = JsonUtil.toBean(server.takeRequest().getBody().readUtf8(), Map.class);
        assertThat(body).containsEntry("enabled", false).containsEntry("username", "alice");
        assertThat(body.get("attributes")).isEqualTo(Map.of("user_id", List.of("u-1")));
    }

    @Test
    void resetPasswordSendsTemporaryCredential() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(204));
        client.resetPassword("kc-123", "Temp-Password-2");
        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("PUT");
        assertThat(request.getRequestUrl().encodedPath()).isEqualTo("/admin/realms/github-dev/users/kc-123/reset-password");
        assertThat(JsonUtil.toBean(request.getBody().readUtf8(), Map.class))
                .isEqualTo(Map.of("type", "password", "value", "Temp-Password-2", "temporary", true));
    }

    @Test
    void deleteAccountTreatsMissingAccountAsDeleted() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(404));
        client.deleteAccount("kc-gone");
        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("DELETE");
        assertThat(request.getRequestUrl().encodedPath()).isEqualTo("/admin/realms/github-dev/users/kc-gone");
    }

    @Test
    void deleteAccountFailureIsReported() {
        server.enqueue(new MockResponse().setResponseCode(500));
        assertThatThrownBy(() -> client.deleteAccount("kc-123")).isInstanceOf(UserException.class);
    }
}
