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
}
