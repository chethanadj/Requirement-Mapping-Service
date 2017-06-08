package com.sceptra.service;

import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.domain.requirement.Requirement;
import com.sceptra.finder.WikiDesc;
import com.sceptra.model.TechnologyEntity;
import com.sceptra.model.TechnologyPackage;
import com.sceptra.processor.Tagger;
import com.sceptra.processor.requirement.KeywordMap;
import com.sceptra.repository.DefineWordRepository;
import com.sceptra.repository.KeyWordRepository;
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
    KeyWordRepository keyWordRepository;
    @Autowired
    DefineWordRepository defineWordRepository;
    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    KeywordMap keywordMap;
    @Autowired
    TechnologyPackageRepository technologyPackageRepository;
    Tagger tagger = new Tagger();
    @Autowired
    private WikiDesc wikiDesc;

    @RequestMapping(value = "requirement", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<KeyWord> addKeyWord(
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        Map<String, Double> paraMap = keywordMap.getParents(para.getParagraph());

        return new ResponseEntity(getFilteredListFromThreshold(paraMap), HttpStatus.FOUND);

    }

    @RequestMapping(value = "findpackagesforrequirement", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<HashSet<String>> findPackagesForRequirement(
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        Map<String, Double> paraMap = keywordMap.getParents(para.getParagraph());

        HashSet<String> techNames= getTechNamesForTerms(paraMap);
        HashSet<String>  packages= getPackagesforTechNames(techNames);
        return new ResponseEntity(packages, HttpStatus.FOUND);

    }

    private HashSet<String> getTechNamesForTerms(Map<String, Double> paraMap) {

        HashSet<String> techNames=new HashSet();
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

    private Map<String, Double> getFilteredListFromThreshold(Map<String, Double> doubleMap){
        Map<String, Double> map=new HashMap<>();
        List<Double> list = new ArrayList<>(doubleMap.values());
        final Double[] total = {0.0};
        int n = (int) Math.round(list.size() * 75 / 100);
        Double threshold=list.get(n);
        doubleMap.forEach((k,v)->{
            if(v>=threshold){
                map.put(k,v);
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
