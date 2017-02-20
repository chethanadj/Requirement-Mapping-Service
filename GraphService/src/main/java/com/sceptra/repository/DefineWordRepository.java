package com.sceptra.repository;

import com.sceptra.domain.DefineWord;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by chiranz on 2/10/17.
 */
public interface DefineWordRepository extends GraphRepository<DefineWord> {

    DefineWord findByDescription(String description);

}
