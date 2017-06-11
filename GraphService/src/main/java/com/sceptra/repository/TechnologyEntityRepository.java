package com.sceptra.repository;

import com.sceptra.domain.technology.TechnologyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by chiranz on 5/30/17.
 */
public interface TechnologyEntityRepository extends CrudRepository<TechnologyEntity, Integer> {

    ArrayList<TechnologyEntity> findByTechnologyName(String technologyName);
    ArrayList<TechnologyEntity> findByTechnologyUsages(String TechnologyUsages);
    ArrayList<TechnologyEntity> findByTechnologyUsagesAndTechnologyName
            (String technologyUsages,String technologyName);
}

