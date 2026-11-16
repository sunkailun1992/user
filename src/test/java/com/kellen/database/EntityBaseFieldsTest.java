package com.kellen.database;

import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.AuthExternalClient;
import com.kellen.auth.entity.AuthExternalIdentity;
import com.kellen.auth.entity.AuthExternalSignatureNonce;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.AuthRoleDataScope;
import com.kellen.auth.entity.AuthRoleResource;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserRole;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.entity.EntityBase;
import com.kellen.entity.TenantEntityBase;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/** 13 个认证实体都继承租户基类，基类已有的公共字段（含 id、version、code、description、sorting）不在实体里重复声明。 */
class EntityBaseFieldsTest {

    static final List<Class<? extends TenantEntityBase>> ENTITIES = List.of(AuthDept.class, AuthExternalClient.class,
            AuthExternalIdentity.class, AuthExternalSignatureNonce.class, AuthOAuthClient.class, AuthResource.class,
            AuthRole.class, AuthRoleDataScope.class, AuthRoleResource.class, AuthTenant.class, AuthUser.class,
            AuthUserRole.class, AuthUserTenant.class);

    @Test
    void entitiesDoNotRedeclareBaseFields() {
        Set<String> base = Stream.of(EntityBase.class, TenantEntityBase.class)
                .flatMap(type -> Arrays.stream(type.getDeclaredFields()))
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(Field::getName)
                .collect(Collectors.toSet());
        for (Class<?> entity : ENTITIES) {
            for (Class<?> type = entity; type != TenantEntityBase.class; type = type.getSuperclass()) {
                assertThat(Arrays.stream(type.getDeclaredFields()).map(Field::getName))
                        .as(type.getSimpleName()).doesNotContainAnyElementsOf(base);
            }
        }
    }
}
