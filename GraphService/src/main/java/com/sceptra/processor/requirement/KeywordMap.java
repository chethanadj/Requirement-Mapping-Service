package com.sceptra.processor.requirement;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sceptra.domain.requirement.DefineWord;
import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.processor.Tagger;
import com.sceptra.repository.DefineWordRepository;
import com.sceptra.repository.DefinedRelRepository;
import com.sceptra.repository.KeyWordRepository;
import com.sceptra.requestor.HTTPRequester;
import com.sceptra.requestor.NLPServiceRequester;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class KeywordMap {

    @Autowired
    DefineWordRepository defineWordRepository;

    @Autowired
    KeyWordRepository keyWordRepository;

    @Autowired
    HTTPRequester requester;

    @Autowired
    NLPServiceRequester nlpServiceRequester;

    @Autowired
    DefinedRelRepository definedRelRepository;

    Tagger tagger = new Tagger();


    public Map<String, Double> getParents(String para) {
        Map<String, Double> keywordMap = new HashMap<>();
        final boolean[] iskeyword = {false};
        ArrayList<String> words = nlpServiceRequester.getCustomFilteredWordList(para);
        final Double[] totalCount = {0.0};

        if (words != null && words.size() == 1)
            words.forEach(word -> {
                KeyWord keyword1 = keyWordRepository.findByDescription(word);
                if (keyword1 != null) {
                    totalCount[0] += 1.0;
                    iskeyword[0] = true;
                    if (keywordMap.get((keyword1.getDescription())) == null) {
                        keywordMap.put(keyword1.getDescription(), 1.0 / totalCount[0]);
                    } else {
                        Double integer = keywordMap.get((keyword1.getDescription())) * (totalCount[0] - 1);
                        keywordMap.put(keyword1.getDescription(), (integer + 1) / totalCount[0]);
                    }
                }
            });
        if (!iskeyword[0])
            words.forEach(word -> {
                DefineWord defineWord = defineWordRepository.findByDescription(word);

                if (defineWord != null) {

                    String url = "http://localhost:7474/db/data/node/" + defineWord.getId() + "/relationships/all";

                    CloseableHttpResponse response = requester.getRequest(url);
                    HttpEntity entity = response.getEntity();

                    String entityString = "";
                    try {
                        entityString = EntityUtils.toString(entity);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            response.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(entityString).getAsJsonArray();
                    ArrayList<KeyWord> keywords = new ArrayList<KeyWord>();
                    ArrayList<Double> ratios = new ArrayList<>();

                    jsonArray.forEach(jsonElement -> {
                        JsonObject object = jsonElement.getAsJsonObject();
                        String node = object.get("end").getAsString().split("/")[6];
                        Long id = Long.parseLong(node);
                        KeyWord keyword = keyWordRepository.findOne(id);
                        keywords.add(keyword);
                        ratios.add(object.get("data").getAsJsonObject().get("definedRatio").getAsDouble());
                    });
//                    totalCount[0] = 0.0;
                    final int[] index = {0};
                    if (keywords != null) {
                        keywords.forEach(defined -> {

                            if (defined.getDescription() != null) {
                                totalCount[0] += 1.0;
                                System.out.println(defined.toString());
                                if (keywordMap.get((defined.getDescription())) == null) {
                                    keywordMap.put(defined.getDescription(), ratios.get(index[0]++));
                                } else {
                                    Double integer = keywordMap.get((defined.getDescription()));
                                    keywordMap.put(defined.getDescription(), (integer + ratios.get(index[0]++)));
                                }
                            }

                        });
                    }
                }


            });

//        keywordMap.forEach((k, v) -> {
//
//            keywordMap.put(k, v / totalCount[0]);
//
//        });


        return keywordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

}
