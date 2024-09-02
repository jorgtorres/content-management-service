package com.unionestate.cm.repository;

import com.unionestate.common.rds.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.unionestate.common.rds.config.HibernateConfig.UEST_PERSISTENCE_UNIT_NAME;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenericRepositoryImpl implements GenericRepository {

    @PersistenceContext(unitName = UEST_PERSISTENCE_UNIT_NAME)
    private final EntityManager entityManager;

    @Override
    public <T extends BaseEntity> UUID create(T entity) {
        return null;
    }

    @Override
    public <T extends BaseEntity> boolean delete(T entity) {
        return false;
    }

    @Override
    public <T extends BaseEntity> T findById(Class<T> entityClass, UUID id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public EntityManager getCurrentEntityManager() {
        return entityManager;
    }
}
