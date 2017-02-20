package com.sceptra.repository;

import com.sceptra.domain.KeyWord;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface KeyWordRepository extends GraphRepository<KeyWord> {

    KeyWord findByDescription(String description);


}
