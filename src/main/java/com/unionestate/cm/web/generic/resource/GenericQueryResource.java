package com.unionestate.cm.web.generic.resource;

import com.unionestate.commons.model.UestCollection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface GenericQueryResource {

    @GetMapping(value = "/rolesMap", produces = "application/json")
    ResponseEntity<Map<String, Object>> getRolesMap() throws Exception;

    @GetMapping(value = "/{collection}/{objectId:.+}", produces = "application/json")
    ResponseEntity<Map<String, Object>> getObjectById(
            @PathVariable String objectId,
            @PathVariable UestCollection collection,
            @RequestParam(value = "associationExpansionLevel", required = false) Integer associationExpansionLevel,
            @RequestParam(value = "lvl", required = false) Integer lvl,
            @RequestParam(value = "properties", required = false) String[] properties,
            @RequestParam(value = "versionId", required = false) Integer versionId,
            @RequestParam(value = "v", required = false) Integer v,
            @RequestParam(value = "expand", required = false) String[] expand);
}
