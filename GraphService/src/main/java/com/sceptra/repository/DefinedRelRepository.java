package com.sceptra.repository;

import com.sceptra.domain.requirement.Defined;
import com.sceptra.domain.requirement.KeyWord;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.ArrayList;


public interface DefinedRelRepository extends GraphRepository<Defined> {
    ArrayList<Defined> findByRel(String rel);
    ArrayList<Defined> findBykeyWord(KeyWord keyWord);
}
