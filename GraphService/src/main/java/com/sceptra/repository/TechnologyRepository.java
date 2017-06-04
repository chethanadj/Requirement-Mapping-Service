package com.sceptra.repository;

import com.sceptra.model.TechnologyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by chiranz on 5/30/17.
 */
public interface TechnologyRepository extends CrudRepository<TechnologyEntity, Integer> {

    ArrayList<TechnologyEntity> findByTechnologyName(String technology_name);
    ArrayList<TechnologyEntity> findByTechnologyUsages(String Technology_usages);
}

