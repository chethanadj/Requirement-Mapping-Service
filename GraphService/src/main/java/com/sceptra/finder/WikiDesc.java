package com.sceptra.finder;

import org.springframework.web.client.RestTemplate;

public class WikiDesc {



    public final String wikiUrl="https://en.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles=";

    public String getDescription(String keyWord) throws Exception {


        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(wikiUrl+keyWord, String.class);
        //need to be change
         
       result=result.substring(result.indexOf("<extract xml:space=\"preserve\">")+"<extract xml:space=\"preserve\">".length(),result.indexOf("</extract>"));
        
        return  result;
    }

}
