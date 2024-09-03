package com.unionestate.uestlibs.contentservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionestate.cm.commons.model.ObjectRequest;
import com.unionestate.cm.exception.CMException;
import com.unionestate.cm.repository.GenericRepository;
import com.unionestate.cm.utils.Constants;
import com.unionestate.common.rds.entity.BaseEntity;
import com.unionestate.common.rds.mapper.Mapper;
import com.unionestate.common.rds.mapper.impl.EntityMappingMetadataLookup;
import com.unionestate.common.rds.mapper.impl.MapperFactory;
import com.unionestate.commons.api.exceptions.ExceptionHelper;
import com.unionestate.commons.api.exceptions.UestErrorCode;
import com.unionestate.commons.model.AdminOverride;
import com.unionestate.commons.model.UestCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service("RDSContentServiceImpl")
@Slf4j
@RequiredArgsConstructor
public class RDSContentServiceImpl implements ContentService {

    private final GenericRepository repository;
    private final ObjectMapper objectMapper;
    private final MapperFactory mapperFactory;
    private final EntityMappingMetadataLookup entityMappingMetadataLookup;

    @Override
    @Transactional
    public Map<String, Object> getObjectById(ObjectRequest objectRequest) throws CMException {
        return getObjectByIdNoCache(objectRequest);
    }

    @Override
    @Transactional
    public Map<String, Object> getObjectByIdNoCache(ObjectRequest objectRequest) throws CMException {
        val result = getObjectById(
                objectRequest.getObjectId(),
                objectRequest.getCollection(),
                objectRequest.getAssociationExpansionLevel(),
                objectRequest.getProperties(),
                AdminOverride.FALSE,
                objectRequest.getVersionId(),
                objectRequest.getExpand(),
                true
        );

        val currentObject = objectMapper.convertValue(result, Constants.MapStringObjectType);
        if (objectRequest.getCollection().isVersioned()) {
            currentObject.put("isLatest", true);
        }
        return currentObject;
    }

    @Override
    @SneakyThrows
    @Transactional
    public Map<String, Object> getObjectById(String objectId, UestCollection singularObjectType, Integer associationExpansionLevel, String[] properties,
            AdminOverride isAdminOverride, Integer versionId, String[] expand, boolean historyLatestOnly) {
        Class<? extends BaseEntity> entityClass = RDSContentServiceUtils.getClassFromUESTCollection(singularObjectType, entityMappingMetadataLookup);
        UUID uuid = UUID.fromString(objectId);
        BaseEntity entity = repository.findById(entityClass, uuid);
        if (null == entity) {
            log.warn("Object of type {} with Id {} not found", singularObjectType, objectId);
            ExceptionHelper.throwException(HttpStatus.NOT_FOUND, UestErrorCode.NOT_FOUND,
                    String.format("Object of type %s with Id %s not found", singularObjectType, objectId));
        }

        Mapper mapper = mapperFactory.getEntityMapperService(singularObjectType.toString()).getMapper();
        Map<String, Object> results = null;

        try {
            results = associationExpansionLevel == null ?
                    mapper.entityToMap(entity, singularObjectType.getObjectType(), 0, properties, expand)
                    : mapper.entityToMap(entity, singularObjectType.getObjectType(), associationExpansionLevel, properties, expand);
        } catch (Exception ex) {
            log.warn("associationExpansionLevel failed with objectId: " + objectId);
            ExceptionHelper.throwException(HttpStatus.INTERNAL_SERVER_ERROR, UestErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        if (results == null || results.isEmpty()) {
            log.warn("Resource not found, get object: " + objectId);
            ExceptionHelper.throwException(HttpStatus.NOT_FOUND, UestErrorCode.NOT_FOUND,
                    String.format("Object of type %s with Id %s not found", singularObjectType, objectId));
        }

        repository.getCurrentEntityManager().detach(entity);
        return results;
    }
}
