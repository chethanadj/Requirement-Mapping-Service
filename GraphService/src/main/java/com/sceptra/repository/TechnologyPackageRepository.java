package com.sceptra.repository;

import com.sceptra.model.TechnologyPackage;
import org.springframework.data.repository.CrudRepository;


public interface TechnologyPackageRepository extends CrudRepository<TechnologyPackage, Integer> {

    TechnologyPackage findByTechnologyName(String technology_name);
}

