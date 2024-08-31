package com.unionestate.cm.infrastructure.config;

import com.unionestate.commons.sso.UserToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
}
