package com.sceptra.repository;

import com.sceptra.model.RequirementHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface RequirementHistoryRepository extends CrudRepository<RequirementHistory, Integer> {
//SELECT COUNT(*) FROM RequirementHistory WHERE MATCH (requirement,requirement_stems) AGAINST (:paragraph IN NATURAL LANGUAGE MODE);

    @Query(nativeQuery = true,value = "SELECT All FROM requirement_history MATCH (requirement,requirement_stems) AGAINST (:paragraph IN NATURAL LANGUAGE MODE)")
    ArrayList<RequirementHistory> find(@Param("paragraph") String paragraph);
    RequirementHistory findByRequirement(String requirement);

}
