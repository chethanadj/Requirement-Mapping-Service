package com.sceptra.finder;

import com.sceptra.model.TechnologyEntity;
import com.sceptra.processor.requirement.KeywordMap;
import com.sceptra.repository.TechnologyRepository;
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
import java.util.Map;

/**
 * Created by chiranz on 5/30/17.
 */
public class ApacheLibraryDesc {

    public static String APACHE_PROJECTS_BASE = "https://projects.apache.org/json/projects/";
    @Autowired
    KeywordMap keywordMap;
    @Autowired
    TechnologyRepository technologyRepository;
    //    public static String TECH_TERMS_DEFINITION = TECH_TERMS_BASE + "/definition/";
//    public static String TECH_TERMS_CATEGORY = TECH_TERMS_BASE + "/category/";
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

    public static void main(String[] args) {
        new ApacheLibraryDesc().getTechnologyList();
    }

    public ArrayList<TechnologyEntity> getData(String librayName) throws Exception {
        ArrayList<TechnologyEntity> technologyEntityArrayList = new ArrayList();
        try {
            JSONObject json = new JSONObject(readUrl(APACHE_PROJECTS_BASE + librayName));
            System.out.println(json.toString());
            if (json.has("category"))
                category = json.getString("category");
            if (json.has("description"))
                description = json.getString("description");
            if (json.has("programming-language"))
                programming_language = json.getString("programming-language");
            if (json.has("release"))
                name = json.getJSONArray("release").getJSONObject(0).getString("name");

            if (programming_language.toLowerCase().contains("java")&&category.equalsIgnoreCase("library")) {
                Map<String, Integer> keywordUsage = keywordMap.getParents(description);
                keywordUsage.forEach((k, v) -> {
                    if (v > 2) {
                        TechnologyEntity technologyEntity = new TechnologyEntity();
                        technologyEntity.setTechnology_usages(k);
                        technologyEntity.setTechnology_name(name);
                        technologyEntityArrayList.add(technologyRepository.save(technologyEntity));
                    }
                });

            } else {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return technologyEntityArrayList;
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
                        System.out.println("Technology"+tech);

                        output.addAll(getData(tech));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


        );
        return output;
    }
}
