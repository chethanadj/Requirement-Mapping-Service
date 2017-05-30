package com.sceptra.service;

import com.sceptra.domain.requirement.DefineWord;
import com.sceptra.domain.requirement.Defined;
import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.finder.TechTermDesc;
import com.sceptra.finder.WikiDesc;
import com.sceptra.repository.DefineWordRepository;
import com.sceptra.repository.DefinedRelRepository;
import com.sceptra.repository.KeyWordRepository;
import com.sceptra.requestor.NLPServiceRequester;
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

	@Autowired
	private WikiDesc wikiDesc;

	@Autowired
	private TechTermDesc  techTermDesc;

	@Autowired
	KeyWordRepository keyWordRepository;

	@Autowired
	DefinedRelRepository definedRelRepository;

	@Autowired
	DefineWordRepository defineWordRepository;
	@Autowired
	NLPServiceRequester nlpServiceRequester;



	@RequestMapping(value = "all" , produces = "application/json",method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<Iterable<KeyWord>> getAllKeyWord(
            @RequestHeader HttpHeaders headers, 
            HttpServletRequest request) throws Exception {

		KeyWord person=new KeyWord("abc");

		keyWordRepository.save(person);
		
		return new ResponseEntity(keyWordRepository.findAll(),HttpStatus.FOUND);
	
	}

	@RequestMapping(value = "keyword" , produces = "application/json",method = RequestMethod.POST)
	public
	@ResponseBody
	ResponseEntity<KeyWord> addKeyWord(
			@RequestBody(required = true) KeyWord word,
			BindingResult result,
			@RequestHeader HttpHeaders headers,
			HttpServletRequest request) throws Exception {

		String keyWordDesc=techTermDesc.getDescription(word.getDescription());
		System.out.println(keyWordDesc);
		ArrayList<String> neighbours=nlpServiceRequester.getCustomFilteredWordList(keyWordDesc);
		if(keyWordRepository.findByDescription(word.getDescription())!=null){
			word=keyWordRepository.findByDescription(word.getDescription());
		}
		KeyWord	keyWord=keyWordRepository.save(word);

		for (int a=0;a<neighbours.size();a++) {

			DefineWord tempWord=null;
			if(defineWordRepository.findByDescription(neighbours.get(a))==null) {
				tempWord=new DefineWord(neighbours.get(a));
				tempWord = defineWordRepository.save(new DefineWord(neighbours.get(a)));
			}else {
				tempWord=defineWordRepository.findByDescription(neighbours.get(a));
				tempWord = defineWordRepository.findByDescription(neighbours.get(a));
			}
			definedRelRepository.save(new Defined(keyWord,tempWord));

		}


 		return new ResponseEntity(keyWord,HttpStatus.CREATED);

	}


	@RequestMapping(value = "keywordlist" , produces = "application/json",method = RequestMethod.POST)
	public
	@ResponseBody
	ResponseEntity<ArrayList<KeyWord>> addBatchKeyWord(
			@RequestBody(required = true) ArrayList<KeyWord> words,
			BindingResult result,
			@RequestHeader HttpHeaders headers,
			HttpServletRequest request) throws Exception {

		ArrayList<KeyWord> keyWords=new ArrayList<>();
		words.forEach(word->{
		String keyWordDesc=techTermDesc.getDescription(word.getDescription());
		System.out.println(keyWordDesc);
		ArrayList<String> neighbours=nlpServiceRequester.getCustomFilteredWordList(keyWordDesc);
		if(keyWordRepository.findByDescription(word.getDescription())!=null){
			word=keyWordRepository.findByDescription(word.getDescription());
		}
		KeyWord	keyWord=keyWordRepository.save(word);
			keyWords.add(keyWord);
		for (int a=0;a<neighbours.size();a++) {

			DefineWord tempWord=null;
			if(defineWordRepository.findByDescription(neighbours.get(a))==null) {
				tempWord=new DefineWord(neighbours.get(a));
				tempWord = defineWordRepository.save(new DefineWord(neighbours.get(a)));
			}else {
				tempWord=defineWordRepository.findByDescription(neighbours.get(a));
				tempWord = defineWordRepository.findByDescription(neighbours.get(a));
			}
			definedRelRepository.save(new Defined(keyWord,tempWord));

		}
		});

		return new ResponseEntity(keyWords,HttpStatus.CREATED);

	}

	@RequestMapping(value = "keywordcategory" , produces = "application/json",method = RequestMethod.POST)
	public
	@ResponseBody
	ResponseEntity<ArrayList<KeyWord>> addByCategoryKeyWord(
			@RequestBody(required = true)String category,
			BindingResult result,
			@RequestHeader HttpHeaders headers,
			HttpServletRequest request) throws Exception {

		ArrayList<KeyWord> keyWords=new ArrayList<>();
		ArrayList<KeyWord> words=techTermDesc.getAllKeywords(category);
		words.forEach(word->{
			String keyWordDesc=techTermDesc.getDescription(word.getDescription());
			System.out.println("key word is :"+keyWordDesc);
			ArrayList<String> neighbours=nlpServiceRequester.getCustomFilteredWordList(keyWordDesc);
			if(keyWordRepository.findByDescription(word.getDescription())!=null){
				word=keyWordRepository.findByDescription(word.getDescription());
			}
			KeyWord	keyWord=keyWordRepository.save(word);
			keyWords.add(keyWord);
			for (int a=0;a<neighbours.size();a++) {

				DefineWord tempWord=null;
				if(defineWordRepository.findByDescription(neighbours.get(a))==null) {
					tempWord=new DefineWord(neighbours.get(a));
					tempWord = defineWordRepository.save(new DefineWord(neighbours.get(a)));
				}else {
					tempWord=defineWordRepository.findByDescription(neighbours.get(a));
					tempWord = defineWordRepository.findByDescription(neighbours.get(a));
				}
				definedRelRepository.save(new Defined(keyWord,tempWord));

			}
		});

		return new ResponseEntity(keyWords,HttpStatus.CREATED);

	}

	private KeyWord getKeyWord(String desc){

		if(keyWordRepository.findByDescription(desc)!=null)
			 return keyWordRepository.findByDescription(desc);
		else
			return keyWordRepository.save(new KeyWord(desc));

	}

}
