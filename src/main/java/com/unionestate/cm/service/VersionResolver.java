package com.unionestate.cm.service;

import com.unionestate.cm.model.ObjectVersion;

public interface VersionResolver {

    ObjectVersion resolveVersion(String objectId, Integer version);

    ObjectVersion resolveVersion(String objectId);
}
