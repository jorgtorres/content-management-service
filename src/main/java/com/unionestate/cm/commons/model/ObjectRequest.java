package com.unionestate.cm.commons.model;

import com.unionestate.commons.model.UestCollection;
import com.unionestate.commons.sso.UserToken;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ObjectRequest {

    private UserToken userToken;
    private String objectId;
    private UestCollection collection;
    @Builder.Default
    private Integer associationExpansionLevel = 0;
    @Builder.Default
    private String[] properties = new String[0];
    private Integer versionId;
    @Builder.Default
    private String[] expand = new String[0];
}