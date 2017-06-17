package com.sceptra.repository;

import com.sceptra.domain.developer.Developer;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by chiranz on 6/17/17.
 */
public interface DeveloperRepository extends CrudRepository<Developer,String> {

    ArrayList<Developer> findByNameAndTechnology(String name, String technology);
    ArrayList<Developer> findByTechnology(String technology);
}
