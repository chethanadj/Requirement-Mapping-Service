package com.sceptra.repository;

import com.sceptra.model.TechnologyEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by chiranz on 5/30/17.
 */
public interface TechnologyRepository extends CrudRepository<TechnologyEntity, Integer> {

    TechnologyEntity findByTechnology_name(String technology_name);
}

