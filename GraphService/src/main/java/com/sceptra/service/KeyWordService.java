package com.sceptra.service;

import com.sceptra.domain.requirement.*;
import com.sceptra.repository.DefineWordRepository;
import com.sceptra.repository.DefinedRelRepository;
import com.sceptra.repository.KeyWordRepository;
import com.sceptra.requestor.NLPServiceRequester;
import com.sceptra.webfinder.TechTermDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@RestController
public class KeyWordService {

    private final String base = "keyword";
    @Autowired
    KeyWordRepository keyWordRepository;
    @Autowired
    DefinedRelRepository definedRelRepository;
    @Autowired
    DefineWordRepository defineWordRepository;
    @Autowired
    NLPServiceRequester nlpServiceRequester;
    @Autowired
    private TechTermDesc techTermDesc;

    @RequestMapping(value = base + "/all",
            produces = "application/json",
            method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<ArrayList<KeyWord>> getAllKeyWord(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        return new ResponseEntity(keyWordRepository.findAll(), HttpStatus.FOUND);

    }

    @RequestMapping(value = base + "/add/one",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<Defined>> addKeyWord(
            @RequestBody(required = true) KeyWord word,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        String keyWordDesc = techTermDesc.getDescription(word.getDescription());
        System.out.println(keyWordDesc);
        ArrayList<String> neighbours = nlpServiceRequester.getCustomFilteredWordList(keyWordDesc);

        if (keyWordRepository.findByDescription(word.getDescription()) != null) {
            word = keyWordRepository.findByDescription(word.getDescription());
        }
        KeyWord keyWord = keyWordRepository.save(word);
        ArrayList<Defined> definedArrayList = saveNeighbours(neighbours, keyWord);
        return new ResponseEntity(definedArrayList, HttpStatus.CREATED);

    }

    @RequestMapping(value = base + "/add/list",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<Defined>> addBatchKeyWord(
            @RequestBody(required = true) ArrayList<KeyWord> words,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<Defined> definedArrayList = new ArrayList<>();
        ArrayList<KeyWord> keyWords = new ArrayList<>();

        for (KeyWord word : words) {

            String keyWordDesc = techTermDesc.getDescription(word.getDescription());
            System.out.println(keyWordDesc);
            ArrayList<String> neighbours = nlpServiceRequester
                    .getCustomFilteredWordList(keyWordDesc);
            if (keyWordRepository.findByDescription(word.getDescription()) != null) {
                word = keyWordRepository.findByDescription(word.getDescription());
            }
            KeyWord keyWord = keyWordRepository.save(word);
            keyWords.add(keyWord);
            ArrayList<Defined> savedDefinedArrayList1 = saveNeighbours(neighbours, keyWord);
            definedArrayList.addAll(savedDefinedArrayList1);
        }

        return new ResponseEntity(definedArrayList, HttpStatus.CREATED);

    }

    @RequestMapping(value = base + "/add/withDesc",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<Defined>> addAKeyWord(
            @RequestBody(required = true) Description wordWithDesc,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        KeyWord word;
        ArrayList<String> neighbours = nlpServiceRequester
                .getCustomFilteredWordList(wordWithDesc.getDescription());
        if (keyWordRepository.findByDescription(wordWithDesc.getWord()) != null) {
            word = keyWordRepository.findByDescription(wordWithDesc.getWord());
        } else {
            word = new KeyWord(wordWithDesc.getWord().trim().toLowerCase());
        }
        KeyWord keyWord = keyWordRepository.save(word);
        ArrayList<Defined> definers = new ArrayList<>();
        for (int a = 0; a < neighbours.size(); a++) {

            DefineWord tempWord = null;
            if (defineWordRepository.findByDescription(neighbours.get(a)) == null) {
                tempWord = new DefineWord(neighbours.get(a));
                tempWord = defineWordRepository.save(tempWord);
            } else {
                tempWord = defineWordRepository.findByDescription(neighbours.get(a));
            }
            Float size = Float.valueOf(neighbours.size());
            Float one = 1.0f;
            definers.add(definedRelRepository
                    .save(new Defined(keyWord, tempWord, (one / size))));

        }

        return new ResponseEntity(definers, HttpStatus.CREATED);

    }

    @RequestMapping(value = base + "/add/bycategory",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<KeyWord>> addByCategoryKeyWord(
            @RequestBody(required = true) Category category,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        ArrayList<KeyWord> keyWords = new ArrayList<>();
        ArrayList<KeyWord> words = techTermDesc
                .getAllKeywords(category.getDescription());
        words.forEach(word -> {
            System.out.println(word);
            String keyWordDesc = techTermDesc.getDescription(word.getDescription());
            System.out.println("key word is : " + keyWordDesc);
            ArrayList<String> neighbours = nlpServiceRequester
                    .getCustomFilteredWordList(keyWordDesc);
            if (!neighbours.isEmpty()) {
                if (keyWordRepository.findByDescription(word.getDescription()) != null) {
                    word = keyWordRepository.findByDescription(word.getDescription());
                }
                KeyWord keyWord = keyWordRepository.save(word);
                keyWords.add(keyWord);
                for (int a = 0; a < neighbours.size(); a++) {

                    DefineWord tempWord = null;
                    if (defineWordRepository.findByDescription(neighbours.get(a)) == null) {
                        tempWord = defineWordRepository
                                .save(new DefineWord(neighbours.get(a)));
                    } else {
                        tempWord = defineWordRepository
                                .findByDescription(neighbours.get(a));
                    }
                    Float size = Float.valueOf(neighbours.size());
                    Float one = 1.0f;
                    definedRelRepository
                            .save(new Defined(keyWord, tempWord, (one / size)));
                }
            }
        });

        return new ResponseEntity(keyWords, HttpStatus.CREATED);

    }

    private KeyWord getKeyWord(String desc) {

        if (keyWordRepository.findByDescription(desc) != null)
            return keyWordRepository.findByDescription(desc);
        else
            return keyWordRepository.save(new KeyWord(desc));

    }

    private ArrayList<Defined> saveNeighbours(ArrayList<String> neighbours,
                                              KeyWord keyWord) {
        ArrayList<Defined> definedArrayList = new ArrayList<>();
        for (int a = 0; a < neighbours.size(); a++) {

            DefineWord tempWord = null;
            if (defineWordRepository.findByDescription(neighbours.get(a)) == null) {
                tempWord = new DefineWord(neighbours.get(a));
                tempWord = defineWordRepository.save(tempWord);
            } else {
                tempWord = defineWordRepository.findByDescription(neighbours.get(a));
            }
            Float size = Float.valueOf(neighbours.size());
            Float one = 1.0f;
            Defined data = definedRelRepository
                    .save(new Defined(keyWord, tempWord, (one / size)));
            definedArrayList.add(data);
        }
        return definedArrayList;
    }
}
