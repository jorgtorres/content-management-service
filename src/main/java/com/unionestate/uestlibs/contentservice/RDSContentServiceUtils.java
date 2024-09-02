package com.unionestate.uestlibs.contentservice;

import com.unionestate.appconfig.UestProperties;
import com.unionestate.common.rds.entity.BaseEntity;
import com.unionestate.common.rds.mapper.impl.EntityMappingMetadataLookup;
import com.unionestate.commons.dao.DocumentCoordinates;
import com.unionestate.commons.model.UestCollection;
import com.unionestate.commons.sso.UserToken;

public class RDSContentServiceUtils {

    static Class<? extends BaseEntity> getClassFromUESTCollection(UestCollection uestCollection, EntityMappingMetadataLookup entityMappingMetadataLookup) {
        return entityMappingMetadataLookup.getLookup().getJsonTableMetadata(uestCollection.getObjectType()).getClazz();
    }

    static Class<? extends BaseEntity> getClassFromDocumentCoordinates(DocumentCoordinates documentCoordinates,
            EntityMappingMetadataLookup entityMappingMetadataLookup) {
        return entityMappingMetadataLookup.getLookup().getJsonMetadata()
                .get(documentCoordinates.getCollection().getObjectType()).getClazz();
    }

    static String getObjectKey(DocumentCoordinates coords, UestProperties uestProperties) {
        return coords.getCollection().isVersioned()
                ? uestProperties.getEnvironmentName() + "/" + coords.getCollection() + "/" + coords.getObjectId() + "-" + coords.getVersion()
                : uestProperties.getEnvironmentName() + "/" + coords.getCollection() + "/" + coords.getObjectId();
    }

    static String getVersionedDocUri(UestCollection objectType, String objectId, int version) {
        return String.format("/%s/%s-%s", objectType.toValue(), objectId, version);
    }

    static String getNonVersionedDocUri(UestCollection objectType, String objectId) {
        return String.format("/%s/%s", objectType.toValue(), objectId);
    }

    static String getTenantBinaryBucketName(UserToken userToken, String s3Prefix) {
        return s3Prefix + "-" + userToken.getOrganization().replace(".", "-");
    }
}
