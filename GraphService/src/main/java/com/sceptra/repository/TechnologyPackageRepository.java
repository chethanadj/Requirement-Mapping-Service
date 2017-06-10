package com.sceptra.repository;

import com.sceptra.model.TechnologyPackage;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface TechnologyPackageRepository extends CrudRepository<TechnologyPackage, Integer> {

    TechnologyPackage findByTechnologyName(String technology_name);
    ArrayList<TechnologyPackage> findByTechnologyNameAndTechnologyPackage
            (String technologyName,String technologyPackage);
}

