package com.sceptra.repository;

import com.sceptra.domain.DefineWord;
import com.sceptra.domain.Defined;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.ArrayList;

/**
 * Created by chiranz on 2/11/17.
 */
public interface DefinedRelRepository extends GraphRepository<Defined> {
    ArrayList<Defined> findByRel(String rel);
}
