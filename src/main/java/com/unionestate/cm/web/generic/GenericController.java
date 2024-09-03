package com.unionestate.cm.web.generic;

import com.unionestate.cm.commons.model.ObjectRequest;
import com.unionestate.cm.commons.model.ObjectRequest.ObjectRequestBuilder;
import com.unionestate.cm.exception.CMException;
import com.unionestate.cm.infrastructure.config.UserContextHolder;
import com.unionestate.cm.model.ObjectVersion;
import com.unionestate.cm.service.VersionResolver;
import com.unionestate.cm.web.generic.resource.GenericQueryResource;
import com.unionestate.commons.model.UestCollection;
import com.unionestate.commons.sso.UserToken;
import com.unionestate.uestlibs.contentservice.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenericController implements GenericQueryResource {

    //private final AuthorizationManager authorizationManager; // use dependency-graph and roles-permissions.json file for REST permissions
    private final VersionResolver versionResolver;
    private final ContentService contentService;

    @Override
    public ResponseEntity<Map<String, Object>> getRolesMap() throws CMException {
        UserToken userToken = UserContextHolder.getUserToken();
        Map<String, Object> response = new HashMap<>();
        response.put(userToken.getOrganization(), List.of());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getObjectById(
            String objectId,
            UestCollection collection,
            Integer associationExpansionLevel,
            Integer lvl,
            String[] properties,
            Integer versionId,
            Integer v,
            String[] expand) throws CMException {
        UserToken userToken = UserContextHolder.getUserToken();

        if (associationExpansionLevel == null && lvl != null) {
            associationExpansionLevel = lvl;
        }
        if (!collection.isExpandable()) {
            associationExpansionLevel = -2; // bypass association transform for non-expandable resources
        }
        if (versionId == null && v != null) {
            versionId = v;
        }

        ObjectVersion version = versionResolver.resolveVersion(objectId, versionId);

        ObjectRequestBuilder builder = ObjectRequest.builder();
        builder
                .objectId(version.getObjectId())
                .collection(collection)
                .userToken(userToken);

        if (associationExpansionLevel != null) {
            builder.associationExpansionLevel(associationExpansionLevel);
        }
        if (properties != null) {
            builder.properties(properties);
        }
        if (version.getVersion() != null) {
            builder.versionId(version.getVersion());
        }
        if (expand != null) {
            builder.expand(expand);
        }

        ObjectRequest objectRequest = builder.build();
        Map<String, Object> object = getObjectById(objectRequest);
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    private Map<String, Object> getObjectById(ObjectRequest objectRequest) throws CMException {
        try {
            return contentService.getObjectById(objectRequest);
        } catch (Exception e) {
            log.error("Redis call failed " + e.getMessage() + " Trying no-cache call... ");
            return contentService.getObjectByIdNoCache(objectRequest);
        }
    }

}
