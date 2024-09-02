package com.unionestate.uestlibs.contentservice;

import com.unionestate.cm.commons.model.ObjectRequest;
import com.unionestate.cm.exception.CMException;
import com.unionestate.commons.model.AdminOverride;
import com.unionestate.commons.model.UestCollection;

import java.util.Map;

public interface ContentService {

    Map<String, Object> getObjectById(ObjectRequest objectRequest) throws CMException;

    Map<String, Object> getObjectByIdNoCache(ObjectRequest objectRequest) throws CMException;

    Map<String, Object> getObjectById(String objectId, UestCollection collection,
            Integer associationExpansionLevel,
            String[] properties, AdminOverride isAdminOverride, Integer versionId, String[] expand,
            boolean historyLatestOnly);
}
