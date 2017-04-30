package com.sceptra.repository;

import com.sceptra.domain.DefineWord;
import com.sceptra.domain.Defined;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.ArrayList;


public interface DefinedRelRepository extends GraphRepository<Defined> {
    ArrayList<Defined> findByRel(String rel);
}
