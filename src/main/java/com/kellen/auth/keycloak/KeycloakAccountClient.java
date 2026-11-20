package com.kellen.auth.keycloak;

import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Keycloak 账号同步：按官方 Admin REST API（{@code /admin/realms/<域>/users}）建、改、停用、重置口令、删除账号。
 *
 * <p>账号以 user 库为准：建账号时写入属性 {@code user_id}（令牌里的业务用户 ID）、临时口令与必需动作 {@code UPDATE_PASSWORD}
 * （首次登录必须改口令）；改资料与启停先取回整份账号再写回，不丢其他属性。失败时抛 {@link UserException}，只给稳定错误码，
 * 不带 Keycloak 的响应正文或口令。管理令牌由客户端凭证注册取得（{@code RestClient} 上的拦截器）。</p>
 *
 * @author 孙凯伦
 */
public class KeycloakAccountClient {

    private static final Logger log = LoggerFactory.getLogger(KeycloakAccountClient.class);

    private static final ParameterizedTypeReference<Map<String, Object>> REPRESENTATION = new ParameterizedTypeReference<>() {
    };

    private final RestClient restClient;

    /**
     * 构造账号同步客户端。
     *
     * @param restClient 基础地址为 {@code …/admin/realms/<域>}、带管理令牌的客户端
     */
    public KeycloakAccountClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * 建账号。
     *
     * @param userId            user 库的用户 ID（写入属性 user_id）
     * @param username          用户名
     * @param nickname          昵称（Keycloak 的 firstName，展示用）
     * @param temporaryPassword 临时口令，首次登录必须修改
     * @return Keycloak 用户 ID（写回 auth_user.idp_subject）
     */
    public String createAccount(String userId, String username, String nickname, String temporaryPassword) {
        Map<String, Object> account = new LinkedHashMap<>();
        account.put("username", username);
        account.put("firstName", nickname);
        account.put("enabled", true);
        account.put("attributes", Map.of("user_id", List.of(userId)));
        account.put("requiredActions", List.of("UPDATE_PASSWORD"));
        account.put("credentials", List.of(credential(temporaryPassword)));
        try {
            ResponseEntity<Void> created = restClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON).body(account)
                    .retrieve().toBodilessEntity();
            URI location = created.getHeaders().getLocation();
            if (location == null) {
                throw failure("建账号", null);
            }
            String path = location.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (HttpClientErrorException.Conflict conflict) {
            throw new UserException(ReturnCode.USERNAME_ALREADY_EXISTS, "用户名已存在");
        } catch (RestClientException ex) {
            throw failure("建账号", ex);
        }
    }

    /**
     * 改用户名与昵称（取回整份账号再写回）。
     *
     * @param idpSubject Keycloak 用户 ID
     * @param username   用户名
     * @param nickname   昵称
     */
    public void updateProfile(String idpSubject, String username, String nickname) {
        update(idpSubject, "改账号资料", account -> {
            account.put("username", username);
            account.put("firstName", nickname);
        });
    }

    /**
     * 启用或停用账号（取回整份账号再写回）。
     *
     * @param idpSubject Keycloak 用户 ID
     * @param enabled    是否启用
     */
    public void setEnabled(String idpSubject, boolean enabled) {
        update(idpSubject, enabled ? "启用账号" : "停用账号", account -> account.put("enabled", enabled));
    }

    /**
     * 重置为临时口令，下次登录必须修改。
     *
     * @param idpSubject        Keycloak 用户 ID
     * @param temporaryPassword 临时口令
     */
    public void resetPassword(String idpSubject, String temporaryPassword) {
        try {
            restClient.put().uri("/users/{id}/reset-password", idpSubject).contentType(MediaType.APPLICATION_JSON)
                    .body(credential(temporaryPassword)).retrieve().toBodilessEntity();
        } catch (RestClientException ex) {
            throw failure("重置口令", ex);
        }
    }

    /**
     * 删除账号；账号已不存在时视为已删除。
     *
     * @param idpSubject Keycloak 用户 ID
     */
    public void deleteAccount(String idpSubject) {
        try {
            restClient.delete().uri("/users/{id}", idpSubject).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound gone) {
            log.info("Keycloak 账号已不存在，按已删除处理");
        } catch (RestClientException ex) {
            throw failure("删除账号", ex);
        }
    }

    private void update(String idpSubject, String action, java.util.function.Consumer<Map<String, Object>> change) {
        try {
            Map<String, Object> account = restClient.get().uri("/users/{id}", idpSubject).retrieve().body(REPRESENTATION);
            if (account == null) {
                throw failure(action, null);
            }
            Map<String, Object> updated = new LinkedHashMap<>(account);
            change.accept(updated);
            restClient.put().uri("/users/{id}", idpSubject).contentType(MediaType.APPLICATION_JSON).body(updated).retrieve().toBodilessEntity();
        } catch (RestClientException ex) {
            throw failure(action, ex);
        }
    }

    private static Map<String, Object> credential(String temporaryPassword) {
        Map<String, Object> credential = new LinkedHashMap<>();
        credential.put("type", "password");
        credential.put("value", temporaryPassword);
        credential.put("temporary", true);
        return credential;
    }

    private static UserException failure(String action, RestClientException cause) {
        String status = cause instanceof RestClientResponseException response ? String.valueOf(response.getStatusCode().value())
                : cause == null ? "无响应" : cause.getClass().getSimpleName();
        log.warn("Keycloak {}失败：{}", action, status);
        if (cause instanceof RestClientResponseException response && response.getStatusCode().value() == HttpStatus.BAD_REQUEST.value()) {
            return new UserException(ReturnCode.REQUEST_PARAMETER_ERROR, "账号信息不符合 Keycloak 的要求");
        }
        return new UserException(ReturnCode.THIRD_PARTY_SERVICE_ERROR, "账号同步到 Keycloak 失败");
    }
}
