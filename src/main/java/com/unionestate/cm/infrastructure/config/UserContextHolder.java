package com.unionestate.cm.infrastructure.config;

import com.unionestate.cm.infrastructure.common.datasource.KnownTenants;
import com.unionestate.commons.sso.UserToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContextHolder {

    private static final ThreadLocal<UserToken> CONTEXT_THREAD = ThreadLocal.withInitial(() -> {
        UserToken defaultUserToken = new UserToken();
        defaultUserToken.setUsername("defaultUserToken");
        defaultUserToken.setOrganization("public");
        return defaultUserToken;
    });

    public static void set(UserToken token) {
        CONTEXT_THREAD.set(token);
    }

    public static void setTenant(String tenant) {
        CONTEXT_THREAD.get().setOrganization(tenant);
    }

    public static String getTenant() {
        return CONTEXT_THREAD.get().getOrganization();
    }

    public static String getUser() {
        return CONTEXT_THREAD.get().getUsername();
    }

    public static UserToken getUserToken() {
        return CONTEXT_THREAD.get();
    }

    public static HttpHeaders getAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(CONTEXT_THREAD.get().toBase64EncodedString());
        return headers;
    }

    public static UserToken copyUserToken() {
        try {
            return UserToken.deserializeUserTokenJson(CONTEXT_THREAD.get().serialize());
        } catch (Exception e) {
            return new UserToken();
        }
    }

    public static void clear() {
        CONTEXT_THREAD.remove();
    }

    public static UserToken fromTenant(String tenant, String username) {
        UserToken userToken = new UserToken();
        userToken.setOrganization(tenant);
        userToken.setUsername(username);
        UserContextHolder.set(userToken);
        return userToken;
    }

    public static void switchToSystemTenant() {
        UserContextHolder.setTenant(KnownTenants.SYSTEM.description);
    }
}
