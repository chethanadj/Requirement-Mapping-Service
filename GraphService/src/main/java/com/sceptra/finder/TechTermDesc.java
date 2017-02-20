package com.sceptra.finder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by chiranz on 2/20/17.
 */
public class TechTermDesc {

    public static String TECH_TERMS_BASE="https://techterms.com/definition/";

    public String getDescription(String keyWord) {

        String res="";

        try {
            Document doc= Jsoup.connect(TECH_TERMS_BASE+keyWord).get();

            res=doc.select("article").first().select("p").first().text();
        } catch (IOException e) {
            return "not in the description";

        }

        return res;
    }
}
