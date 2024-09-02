package com.unionestate.cm.repository;

import com.unionestate.common.rds.entity.BaseEntity;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public interface GenericRepository {

    /**
     * Saves the Object
     *
     * @param entity
     * @return
     */
    <T extends BaseEntity> UUID create(T entity);

    /**
     * Deletes the Object based on ID
     *
     * @param entity
     * @return
     */
    <T extends BaseEntity> boolean delete(T entity);

    /**
     * Finds the object based on Id
     *
     * @param id
     * @return
     */
    <T extends BaseEntity> T findById(Class<T> entityClass, UUID id);

    EntityManager getCurrentEntityManager();
}
