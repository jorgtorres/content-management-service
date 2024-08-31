package com.unionestate.cm.web.controller;

import com.unionestate.cm.infrastructure.config.UserContextHolder;
import com.unionestate.commons.sso.UserToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GenericController {

    //private final AuthorizationManager authorizationManager; // use dependency-graph and roles-permissions.json file for REST permissions
    @RequestMapping(value = "/rolesMap", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, Object>> getRolesMap() throws Exception {
        UserToken userToken = UserContextHolder.getUserToken();
        Map<String, Object> response = new HashMap<>();
        response.put(userToken.getOrganization(), List.of());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
