package com.sceptra.service;

import com.sceptra.domain.requirement.Requirement;
import com.sceptra.domain.requirement.RequirementHistory;
import com.sceptra.processor.requirement.LevenshteinDistance;
import com.sceptra.repository.RequirementHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class RequirementHistoryService {

    private final String base = "history";
    @Autowired
    RequirementHistoryRepository requirementHistoryRepository;
    @Autowired
    LevenshteinDistance levenshteinDistance;

    @RequestMapping(value = base,
            produces = "application/json",
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<List<RequirementHistory>> getOldSimillarRecords(
            @RequestBody(required = true) Requirement requirement,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        String requirementParagraph = requirement.getParagraph();
        List<RequirementHistory> requirementHistories = requirementHistoryRepository
                .find(requirementParagraph);
        return new ResponseEntity(requirementHistories, HttpStatus.FOUND);

    }

    @RequestMapping(value = base + "/withdistance",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Map<String, Integer>> getOldSimillarRecordsWithDistance(
            @RequestBody(required = true) Requirement requirement,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        List<RequirementHistory> requirementHistories = requirementHistoryRepository
                .find(requirement.getParagraph());

        Map<String, Integer> distanceMap = new HashMap<>();

        for (RequirementHistory history : requirementHistories) {
            Integer distance = distanceMap.get(history.getRequirement());
            if (distance == null) {

                distance = levenshteinDistance
                        .getLD(requirement.getParagraph(), history.getRequirement());

                distanceMap.put(history.getRequirement(), distance);
            }
        }

        return new ResponseEntity(distanceMap, HttpStatus.FOUND);

    }

    @RequestMapping(value = base,
            produces = "application/json",
            method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseEntity<RequirementHistory> updateARequirement(
            @RequestBody(required = true) RequirementHistory requirementHistory,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        if (requirementHistory.getAcceptance() != null) {//
            RequirementHistory requirement1 = requirementHistoryRepository
                    .findByRequirement(requirementHistory.getRequirement());

            if (requirement1 == null) {
                return new ResponseEntity(requirementHistory, HttpStatus.NOT_FOUND);

            }

            return new ResponseEntity
                    (updateRequirementScore
                            (requirement1, requirementHistory.getAcceptance()), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(requirementHistory, HttpStatus.BAD_REQUEST);
        }
    }

    private RequirementHistory updateRequirementScore(RequirementHistory requirement1, Integer score) {

        requirement1.setAcceptance(score);
        return requirementHistoryRepository.save(requirement1);

    }
}
