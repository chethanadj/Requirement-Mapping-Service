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

    @RequestMapping(value = base + "/packages",
            produces = "application/json",
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<List<RequirementHistory>> getOldSimillarRecordsPackages(
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

        if (requirementHistory == null
                || requirementHistory.getId() == null
                || requirementHistory.getAcceptance() == null) {

            return new ResponseEntity<RequirementHistory>(requirementHistory, HttpStatus.BAD_REQUEST);
        }

        RequirementHistory requirementHistory1 = requirementHistoryRepository
                .findOne(requirementHistory.getId());

        if (requirementHistory1 == null) {
            return new ResponseEntity<RequirementHistory>
                    (requirementHistory, HttpStatus.NOT_FOUND);

        }
        requirementHistory1.setAcceptance(requirementHistory.getAcceptance());
        return new ResponseEntity<RequirementHistory>
                (requirementHistoryRepository.save(requirementHistory1), HttpStatus.CREATED);
    }


}
