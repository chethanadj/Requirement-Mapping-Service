package com.sceptra.webfinder;

import com.sceptra.domain.technology.TechnologyEntity;
import com.sceptra.processor.requirement.KeywordMap;
import com.sceptra.repository.TechnologyEntityRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.neo4j.ogm.json.JSONException;
import org.neo4j.ogm.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ApacheLibraryDesc {

    public static String APACHE_PROJECTS_BASE = "https://projects.apache.org/json/projects/";
    @Autowired
    KeywordMap keywordMap;
    @Autowired
    TechnologyEntityRepository technologyRepository;
    private String name = "";
    private String category = "";
    private String description = "";
    private String programming_language = "";

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }


    public ArrayList<TechnologyEntity> getData(String librayName) throws Exception {
        ArrayList<TechnologyEntity> technologyEntityArrayList = new ArrayList();
        try {
            JSONObject json = new JSONObject(readUrl(APACHE_PROJECTS_BASE + librayName));
            if (json.has("category"))
                category = json.getString("category");
            if (json.has("description"))
                description = json.getString("description");
            if (json.has("programming-language"))
                programming_language = json.getString("programming-language");
//            if (json.has("release"))
//                name = json.getJSONArray("release").getJSONObject(0).getString("name");
            if (json.has("name"))
                name = json.getString("name");

            if (programming_language.toLowerCase().contains("java") && category.toLowerCase().contains("library")) {

                ArrayList<String> stemList = keywordMap.getStemList(description);
                Map<String, Double> keywordUsage = keywordMap.getWordMap(stemList);
                Double threshold = getThreshold(keywordUsage);
                keywordUsage.forEach((k, v) -> {
                    if (v >= threshold) {
                        TechnologyEntity technologyEntity = new TechnologyEntity();
                        technologyEntity.setTechnologyUsages(k);
                        technologyEntity.setTechnologyName(name);
//                        if(technologyRepository.findByTechnologyName(name)==null){
                        technologyEntityArrayList.add(technologyRepository.save(technologyEntity));
                        System.out.println(technologyEntity.toString());
//                        }
                    }
                });

            } else {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return technologyEntityArrayList;
    }

    private Double getThreshold(Map<String, Double> doubleMap) {

        List<Double> list = new ArrayList<>(doubleMap.values());
        final Double[] total = {0.0};
        doubleMap.forEach((k, v) -> {
            total[0] += v;
        });
        int n = (int) Math.round(list.size() * 75 / 100);
        return list.get(n);
    }

    private ArrayList<String> getTechnologyList() {
        ArrayList<String> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(APACHE_PROJECTS_BASE).get();
            Elements jsons = doc.getElementsByAttributeValueEnding("href", ".json");
            jsons.forEach(json -> {
                list.add(json.attr("href"));

            });
            System.out.println(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<TechnologyEntity> getListData() {
        ArrayList<TechnologyEntity> output = new ArrayList<>();
        ArrayList<String> techList = getTechnologyList();
        techList.forEach(tech -> {
                    try {
                        System.out.println("Technology" + tech);
                        output.addAll(getData(tech));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        return output;
    }
}