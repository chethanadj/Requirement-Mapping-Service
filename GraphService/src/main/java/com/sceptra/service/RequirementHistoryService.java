package com.sceptra.service;

import com.sceptra.domain.requirement.Requirement;
import com.sceptra.model.RequirementHistory;
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
import java.util.ArrayList;


@RestController
public class RequirementHistoryService {

    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    KeywordMap keywordMap;
    @Autowired
    TechnologyPackageRepository technologyPackageRepository;
    @Autowired
    RequirementHistoryRepository requirementHistoryRepository;

    @RequestMapping(value = "oldrecords", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<RequirementHistory>> addKeyWord(
            @RequestBody(required = true) Requirement para,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<RequirementHistory> output = requirementHistoryRepository.find(para.getParagraph());

        return new ResponseEntity(output, HttpStatus.FOUND);

    }


}
