package com.sceptra.processor.requirement;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sceptra.domain.requirement.DefineWord;
import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.processor.Tagger;
import com.sceptra.repository.DefineWordRepository;
import com.sceptra.repository.KeyWordRepository;
import com.sceptra.requestor.HTTPRequester;
import com.sceptra.requestor.NLPServiceRequester;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeywordMap {

    @Autowired
    DefineWordRepository defineWordRepository;

    @Autowired
    KeyWordRepository keyWordRepository;

    @Autowired
    HTTPRequester requester;

    @Autowired
    NLPServiceRequester nlpServiceRequester;

    Tagger tagger = new Tagger();


    public Map<String, Integer> getParents(String para) {
        Map<String, Integer> keywordMap = new HashMap<>();

        ArrayList<String> words = nlpServiceRequester.getCustomFilteredWordList(para);

        words.forEach(word -> {
            DefineWord defineWord = defineWordRepository.findByDescription(word);

            if (defineWord != null) {

                String url="http://localhost:7474/db/data/node/"+defineWord.getId()+"/relationships/all";

                HttpResponse response= requester.getRequest(url);
                HttpEntity entity = response.getEntity();

                String entityString = "";
                try {
                    entityString = EntityUtils.toString(entity);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(entityString).getAsJsonArray();
                ArrayList<KeyWord> keywords = new ArrayList<KeyWord>();

                jsonArray.forEach(jsonElement -> {
                    JsonObject object=jsonElement.getAsJsonObject();
                    String node=object.get("end").getAsString().split("/")[6];
                    Long id=Long.parseLong(node);
                    KeyWord keyword = keyWordRepository.findOne(id);
                    keywords.add(keyword);
                });

                if (keywords != null) {
                    keywords.forEach(defined -> {
                        if(defined.getDescription()!=null) {
                            System.out.println(defined.toString());
                            if (keywordMap.get((defined.getDescription())) == null) {
                                keywordMap.put(defined.getDescription(), 1);
                            } else {
                                Integer integer = keywordMap.get((defined.getDescription()));
                                keywordMap.put(defined.getDescription(), integer + 1);
                            }
                        }

                    });
                }
            }


        });

        return keywordMap;
    }

}
