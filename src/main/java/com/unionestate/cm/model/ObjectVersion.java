package com.unionestate.cm.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ObjectVersion {

    private final String objectId;
    private final Integer version;

    boolean hasVersion() {
        return version == null;
    }

}
