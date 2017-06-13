package com.sceptra.processor.requirement;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sceptra.domain.requirement.DefineWord;
import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.repository.DefineWordRepository;
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


    public Map<String, Double> getWordMap(ArrayList<String> words) {
        Map<String, Double> wordMap = new HashMap<>();
        boolean iskeyword = false;
        Double totalCount = 0.0;

        if (words != null && words.size() == 1)
            for (String word : words) {
                KeyWord keyword1 = keyWordRepository.findByDescription(word);
                if (keyword1 != null) {
                    totalCount += 1.0;
                    iskeyword = true;
                    if (wordMap.get((keyword1.getDescription())) == null) {
                        wordMap.put(keyword1.getDescription(), 1.0 / totalCount);
                    } 
                }
            }
        if (!iskeyword)
            for (String word : words) {
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
                    ArrayList<KeyWord> keywords = new ArrayList<>();
                    ArrayList<Double> ratios = new ArrayList<>();

                    for(int a=0;a<jsonArray.size();a++) {

                        JsonObject object =  jsonArray.get(a).getAsJsonObject();
                        String node = object.get("end").getAsString().split("/")[6];
                        Long id = Long.parseLong(node);
                        KeyWord keyword = keyWordRepository.findOne(id);
                        keywords.add(keyword);
                        ratios.add(object.get("data").getAsJsonObject().get("definedRatio").getAsDouble());
                    }

                    int index = 0;
                    if (keywords != null) {
                        for (KeyWord defined : keywords) {

                            if (defined.getDescription() != null) {
                                totalCount += 1.0;
                                System.out.println(defined.toString());
                                if (wordMap.get((defined.getDescription())) == null) {
                                    wordMap.put(defined.getDescription(), ratios.get(index++));
                                } else {
                                    Double integer = wordMap.get((defined.getDescription()));
                                    wordMap.put(defined.getDescription(),
                                            (integer + ratios.get(index++)));
                                }
                            }

                        }
                    }
                }

            }

        return wordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public ArrayList<String> getStemList(String para) {
        ArrayList<String> words = new ArrayList<>();
        words = nlpServiceRequester.getCustomFilteredWordList(para);
        words.sort((p1, p2) -> p1.compareTo(p2));
        return words;
    }
}
