package com.unionestate.cm.service.impl;

import com.unionestate.cm.model.ObjectVersion;
import com.unionestate.cm.model.ObjectVersion.ObjectVersionBuilder;
import com.unionestate.cm.service.VersionResolver;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Logic is lifted from DocumentFactoryBase. It's beyond stupid I know.
 * I feel dirty
 */
@Service
class VersionResolverImpl implements VersionResolver {

    private final Pattern bastardizedUUID = Pattern
            .compile("^([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})(-(\\d+))?$", Pattern.CASE_INSENSITIVE);

    private final Pattern versionedJson = Pattern.compile("^(.+\\.json)(-(\\d+))?$", Pattern.CASE_INSENSITIVE);

    @Override
    public ObjectVersion resolveVersion(String objectId, Integer versionId) {
        return resolve(objectId, versionId);
    }

    @Override
    public ObjectVersion resolveVersion(String objectId) {
        return resolve(objectId, null);

    }

    private ObjectVersion resolve(String objectId, Integer versionId) {
        ObjectVersionBuilder builder = ObjectVersion.builder();

        if (versionId != null) {
            return builder
                    .objectId(objectId)
                    .version(versionId)
                    .build();
        }
        ObjectVersion version = applyRegex(bastardizedUUID, builder, objectId);
        if (version != null) {
            return version;
        }

        version = applyRegex(versionedJson, builder, objectId);
        if (version != null) {
            return version;
        }

        return builder
                .objectId(objectId).build();
    }

    private ObjectVersion applyRegex(Pattern pattern, ObjectVersionBuilder builder, String objectId) {
        Matcher match = pattern.matcher(objectId);
        if (match.matches()) {
            String objId = match.group(1);
            String ver = match.group(3);

            if (ver == null) {
                return builder
                        .objectId(objId)
                        .build();
            }
            return builder
                    .objectId(objId)
                    .version(Integer.valueOf(ver))
                    .build();
        }
        return null;
    }

}
