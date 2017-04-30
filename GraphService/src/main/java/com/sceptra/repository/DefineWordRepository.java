package com.sceptra.repository;

import com.sceptra.domain.DefineWord;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface DefineWordRepository extends GraphRepository<DefineWord> {

    DefineWord findByDescription(String description);

}
