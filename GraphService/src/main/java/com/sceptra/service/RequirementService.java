package com.sceptra.service;

import com.sceptra.domain.requirement.Requirement;
import com.sceptra.domain.requirement.RequirementHistory;
import com.sceptra.domain.technology.Technology;
import com.sceptra.domain.technology.TechnologyEntity;
import com.sceptra.domain.technology.TechnologyPackage;
import com.sceptra.processor.developer.DeveloperFilter;
import com.sceptra.processor.requirement.KeywordMap;
import com.sceptra.repository.RequirementHistoryRepository;
import com.sceptra.repository.TechnologyEntityRepository;
import com.sceptra.repository.TechnologyPackageRepository;
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

    private final String base = "requirement";
    @Autowired
    TechnologyEntityRepository technologyRepository;
    @Autowired
    KeywordMap keywordMap;
    @Autowired
    TechnologyPackageRepository technologyPackageRepository;
    @Autowired
    RequirementHistoryRepository historyRepository;
    @Autowired
    DeveloperFilter filter;

    @RequestMapping(value = base,
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Map<String, Double>> getKeywords(
            @RequestBody(required = true) Requirement requirement,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<RequirementHistory> requirementHistoryList = historyRepository
                .findByRequirement(requirement.getParagraph());
        RequirementHistory requirementHistory = null;
        if (requirementHistoryList != null && requirementHistoryList.size() >= 1) {
            requirementHistory = requirementHistoryList.get(0);
        }

        ArrayList<String> stemList = keywordMap
                .getStemList(requirement.getParagraph());
        if (stemList.size() == 1) {
            stemList.clear();
            stemList.add(requirement.getParagraph().trim().toLowerCase());
        }
        
        Map<String, Double> wordMap = keywordMap.getWordMap(stemList);
        if (requirementHistory == null) {

            wordMap.forEach((k, v) -> {
                RequirementHistory history = new RequirementHistory();
                history.setRequirement(requirement.getParagraph());
                history.setKeyword(k);
                history.setKeywordScore(v);
                history.setRequirementStems(stemList.toString());
                historyRepository.save(history);
                System.out.println("Requirement Added to history : " + history.toString());

            });

        }

        return new ResponseEntity(wordMap, HttpStatus.FOUND);

    }

    @RequestMapping(value = base + "/{topPercentage}",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Map<String, Double>> getTopKeywordsWord(
            @PathVariable("topPercentage") Integer value,
            @RequestBody(required = true) Requirement requirement,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<RequirementHistory> requirementHistoryList = historyRepository
                .findByRequirement(requirement.getParagraph());
        RequirementHistory requirementHistory = null;
        if (requirementHistoryList != null && requirementHistoryList.size() >= 1) {
            requirementHistory = requirementHistoryList.get(0);
        }

        ArrayList<String> stemList = keywordMap
                .getStemList(requirement.getParagraph());
        if (stemList.size() == 1) {
            stemList.clear();
            stemList.add(requirement.getParagraph().trim().toLowerCase());
        }        
        Map<String, Double> wordMap = keywordMap.getWordMap(stemList);
        if (requirementHistory == null) {

            wordMap.forEach((k, v) -> {
                RequirementHistory history = new RequirementHistory();
                history.setRequirement(requirement.getParagraph());
                history.setKeyword(k);
                history.setKeywordScore(v);
                history.setRequirementStems(stemList.toString());
                historyRepository.save(history);
                System.out.println("Requirement Added to history : " + history.toString());

            });

        }

        Map<String, Double> listFromThreshold = getFilteredListFromThreshold(wordMap, value);
        return new ResponseEntity(listFromThreshold, HttpStatus.FOUND);

    }

    @RequestMapping(value = base + "/getpackages",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<HashSet<String>> findPackagesForRequirement(
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<String> stemList = keywordMap.getStemList(para.getParagraph());
        if (stemList.size() == 1) {
            stemList.clear();
            stemList.add(para.getParagraph().trim().toLowerCase());
        }
        Map<String, Double> paraMap = keywordMap.getWordMap(stemList);
        HashSet<String> techNames = getTechNamesForTerms(paraMap);
        HashSet<String> packages = getPackagesforTechNames(techNames);
        return new ResponseEntity(packages, HttpStatus.FOUND);

    }

    @RequestMapping(value = base + "/getpackages/{topPercentage}",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<HashSet<String>> findPackagesForFilteredRequirement(
            @PathVariable("topPercentage") Integer value,
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<String> stemList = keywordMap.getStemList(para.getParagraph());
        if (stemList.size() == 1) {
            stemList.clear();
            stemList.add(para.getParagraph().trim().toLowerCase());
        }
        Map<String, Double> wordMap = keywordMap.getWordMap(stemList);
        Map<String, Double> listFromThreshold = getFilteredListFromThreshold(wordMap, value);
        HashSet<String> techNames = getTechNamesForTerms(listFromThreshold);
        HashSet<String> packages = getPackagesforTechNames(techNames);
        return new ResponseEntity(packages, HttpStatus.FOUND);

    }


    @RequestMapping(value = base + "/users",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<Technology>> findUsersForRequirement(
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<String> stemList = keywordMap.getStemList(para.getParagraph());
        if (stemList.size() == 1) {
            stemList.clear();
            stemList.add(para.getParagraph().trim().toLowerCase());
        }
        Map<String, Double> paraMap = keywordMap.getWordMap(stemList);
        HashSet<String> techNames = getTechNamesForTerms(paraMap);
        HashSet<String> packages = getPackagesforTechNames(techNames);
        Map<String, ArrayList<Technology>> userList = filter.getDeveloperList(packages);
        return new ResponseEntity(userList, HttpStatus.FOUND);

    }

    @RequestMapping(value = base + "/users/{topPercentage}",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Map<String, ArrayList<Technology>>> findUsersForTopFilteredRequirement(
            @PathVariable("topPercentage") Integer value,
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<String> stemList = keywordMap.getStemList(para.getParagraph());
        if (stemList.size() == 1) {
            stemList.clear();
            stemList.add(para.getParagraph().trim().toLowerCase());
        }
        Map<String, Double> wordMap = keywordMap.getWordMap(stemList);
        Map<String, Double> listFromThreshold = getFilteredListFromThreshold(wordMap, value);
        HashSet<String> techNames = getTechNamesForTerms(listFromThreshold);
        HashSet<String> packages = getPackagesforTechNames(techNames);
        Map<String, ArrayList<Technology>> userList = filter.getDeveloperList(packages);
        return new ResponseEntity(userList, HttpStatus.FOUND);

    }

    private HashSet<String> getTechNamesForTerms(Map<String, Double> paraMap) {

        HashSet<String> techNames = new HashSet();
        paraMap.forEach((k, v) -> {

            ArrayList<TechnologyEntity> temp = technologyRepository
                    .findByTechnologyUsages(k);
            if (temp != null && !temp.isEmpty()) {
                temp.forEach(technologyEntity -> {
                    techNames.add(technologyEntity.getTechnologyName());
                });

            }

        });

        return techNames;
    }

    private Map<String, Double> getFilteredListFromThreshold(Map<String, Double> doubleMap, Integer value) {
        Map<String, Double> map = new HashMap<>();
        List<Double> list = new ArrayList<>(doubleMap.values());
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
