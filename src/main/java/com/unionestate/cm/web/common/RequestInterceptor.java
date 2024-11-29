package com.unionestate.cm.web.common;

import com.unionestate.cm.infrastructure.config.UserContextHolder;
import com.unionestate.common.rds.config.multitenant.TenantContext;
import com.unionestate.commons.sso.UserToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import static org.apache.tomcat.websocket.Constants.AUTHORIZATION_HEADER_NAME;

@Component
public class RequestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object object) throws Exception {
        if (request.getRequestURI().endsWith("/version")) {
            return true;
        }

        UserToken userToken = deserializeUserToken(request.getHeader(AUTHORIZATION_HEADER_NAME));
        if (userToken == null) {
            response.getWriter().write("Not Authorised");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        UserContextHolder.set(userToken);
        TenantContext.setCurrentTenant(userToken.getOrganization());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
        UserContextHolder.clear();
    }

    private UserToken deserializeUserToken(String authorization) {
        try {
            return UserToken.deserialize(authorization);
        } catch (Exception e) {
            return null;
        }
    }
}
