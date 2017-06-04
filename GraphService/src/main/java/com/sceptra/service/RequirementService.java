package com.sceptra.service;

import com.sceptra.domain.requirement.KeyWord;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;


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
            @RequestBody(required = true) String para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        Map<String, Integer> paraMap = keywordMap.getParents(para);

        return new ResponseEntity(paraMap, HttpStatus.FOUND);

    }

    @RequestMapping(value = "findpackagesforrequirement", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<HashSet<String>> findPackagesForRequirement(
            @RequestBody(required = true) String para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        Map<String, Integer> paraMap = keywordMap.getParents(para);

        HashSet<String> techNames= getTechNamesForTerms(paraMap);
        HashSet<String>  packages= getPackagesforTechNames(techNames);
        return new ResponseEntity(packages, HttpStatus.FOUND);

    }

    private HashSet<String> getTechNamesForTerms(Map<String, Integer> paraMap) {

        HashSet<String> techNames=new HashSet();
        paraMap.forEach((k, v) -> {

            if (v > 3) {
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
