package com.sceptra.service;

import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.domain.requirement.Requirement;
import com.sceptra.model.RequirementHistory;
import com.sceptra.model.TechnologyEntity;
import com.sceptra.model.TechnologyPackage;
import com.sceptra.processor.requirement.KeywordMap;
import com.sceptra.repository.RequirementHistoryRepository;
import com.sceptra.repository.TechnologyPackageRepository;
import com.sceptra.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class RequirementService {

    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    KeywordMap keywordMap;
    @Autowired
    TechnologyPackageRepository technologyPackageRepository;
    @Autowired
    RequirementHistoryRepository historyRepository;

    @RequestMapping(value = "requirement/{topPercentage}", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<KeyWord> addKeyWord(
            @PathVariable("topPercentage") Integer value,
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        RequirementHistory requirement = historyRepository.findByRequirement(para.getParagraph());
        ArrayList<String> stemList = keywordMap.getStemList(para.getParagraph());
        Map<String, Double> paraMap = keywordMap.getParents(stemList);
        if (requirement == null) {

            paraMap.forEach((k, v) -> {
                RequirementHistory history = new RequirementHistory();
                history.setRequirement(para.getParagraph());
                history.setKeyword(k);
                history.setKeywordScore(v);
                history.setRequiremetStems(stemList.toString());
                historyRepository.save(history);
                System.out.println("Requirement Added to history : " + history.toString());

            });

        }

        Map<String, Double> out = getFilteredListFromThreshold(paraMap, value);
        return new ResponseEntity(out, HttpStatus.FOUND);

    }

    @RequestMapping(value = "findpackagesforrequirement", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<HashSet<String>> findPackagesForRequirement(
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<String> stemList = keywordMap.getStemList(para.getParagraph());
        Map<String, Double> paraMap = keywordMap.getParents(stemList);
        HashSet<String> techNames = getTechNamesForTerms(paraMap);
        HashSet<String> packages = getPackagesforTechNames(techNames);
        return new ResponseEntity(packages, HttpStatus.FOUND);

    }

    private HashSet<String> getTechNamesForTerms(Map<String, Double> paraMap) {

        HashSet<String> techNames = new HashSet();
        paraMap.forEach((k, v) -> {

            if (v > 0.3) {
                ArrayList<TechnologyEntity> temp = technologyRepository
                        .findByTechnologyUsages(k);

                if (temp != null && !temp.isEmpty()) {
                    temp.forEach(technologyEntity -> {
                        techNames.add(technologyEntity.getTechnologyName());
                    });

                }
            }

        });

        return techNames;
    }

    private Map<String, Double> getFilteredListFromThreshold(Map<String, Double> doubleMap, Integer value) {
        Map<String, Double> map = new HashMap<>();
        List<Double> list = new ArrayList<>(doubleMap.values());
        final Double[] total = {0.0};
        int n = (int) Math.round(list.size() * (100 - value) / 100);
        Double threshold = list.get(n);
        doubleMap.forEach((k, v) -> {
            if (v >= threshold) {
                map.put(k, v);
            }
        });
        return map;
    }

    private HashSet<String> getPackagesforTechNames(HashSet<String> technames) {

        HashSet<String> packages = new HashSet<>();

        technames.forEach(techname -> {

            TechnologyPackage temp = technologyPackageRepository.findByTechnologyName(techname);

            if (temp != null) {
                packages.add(temp.getTechnologyPackage());
            }
        });
        return packages;
    }

}
