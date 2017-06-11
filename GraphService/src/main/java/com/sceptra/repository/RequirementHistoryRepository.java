package com.sceptra.repository;

import com.sceptra.domain.requirement.RequirementHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RequirementHistoryRepository extends CrudRepository<RequirementHistory, Integer> {
//SELECT COUNT(*) FROM requirement_history WHERE MATCH (requirement,requiremet_stems) AGAINST (con IN NATURAL LANGUAGE MODE);

    @Query(nativeQuery = true,
            value = "CALL searchrequirements(:paragraph)")
    List<RequirementHistory> find(@Param("paragraph") String paragraph);

    RequirementHistory findByRequirement(String requirement);

}
