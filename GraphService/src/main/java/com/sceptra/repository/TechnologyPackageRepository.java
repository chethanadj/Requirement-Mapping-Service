package com.sceptra.repository;

import com.sceptra.domain.technology.TechnologyPackage;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface TechnologyPackageRepository extends CrudRepository<TechnologyPackage, Integer> {

    TechnologyPackage findByTechnologyName(String technology_name);
    TechnologyPackage findByAndTechnologyPackage(String technology_package);
    ArrayList<TechnologyPackage> findByTechnologyNameAndTechnologyPackage
            (String technologyName,String technologyPackage);
}

