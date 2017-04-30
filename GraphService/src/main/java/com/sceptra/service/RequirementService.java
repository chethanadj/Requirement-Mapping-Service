package com.sceptra.service;

import com.sceptra.domain.KeyWord;
import com.sceptra.finder.WikiDesc;
import com.sceptra.processor.Tagger;
import com.sceptra.processor.requirement.KeywordMap;
import com.sceptra.repository.DefineWordRepository;
import com.sceptra.repository.KeyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;


@RestController
public class RequirementService {

    @Autowired
    private WikiDesc wikiDesc;

    @Autowired
    KeyWordRepository keyWordRepository;

    @Autowired
    DefineWordRepository defineWordRepository;

    @Autowired
    KeywordMap keywordMap;

    Tagger tagger=new Tagger();

    @RequestMapping(value = "para" , produces = "application/json",method = RequestMethod.POST)
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

}
