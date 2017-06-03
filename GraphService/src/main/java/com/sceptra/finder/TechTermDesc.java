package com.sceptra.finder;

import com.sceptra.domain.requirement.KeyWord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class TechTermDesc {

    public static String TECH_TERMS_BASE = "https://techterms.com";
    public static String TECH_TERMS_DEFINITION = TECH_TERMS_BASE + "/definition/";
    public static String TECH_TERMS_CATEGORY = TECH_TERMS_BASE + "/category/";

    public String getDescription(String keyWord) {

        String res = "";

        try {
            Document doc = Jsoup.connect(TECH_TERMS_DEFINITION + keyWord).get();

            res = doc.select("article").first().select("p").first().text();
        } catch (IOException e) {
            return "not in the description";

        }


        return res;
    }

    public ArrayList<KeyWord> getAllKeywords(String category) {
        String prefix = "/definition/";
        ArrayList<KeyWord> response = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(TECH_TERMS_CATEGORY + category).get();

            Element table = doc.getElementsByTag("table").get(0);
            Elements aTags = table.getElementsByAttributeValueContaining("href", prefix);

            aTags.forEach(aTag -> {

                String[] hrefDataArr = aTag.attr("href").toString().split(prefix);
                KeyWord keyword = new KeyWord(hrefDataArr[hrefDataArr.length - 1]);
                response.add(keyword);
            });
//            res=doc.select("article").first().select("p").first().text();
        } catch (IOException e) {
            response.add(new KeyWord(e.getLocalizedMessage()));
            return response;

        }

        return response;

    }
}
