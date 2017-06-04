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
        ArrayList<String> customWords = getCustomList();
        String prefix = "/definition/";
        ArrayList<KeyWord> response = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(TECH_TERMS_CATEGORY + category).get();

            Element table = doc.getElementsByTag("table").get(0);
            Elements aTags = table.getElementsByAttributeValueContaining("href", prefix);

            aTags.forEach(aTag -> {
                String text = aTag.text().toLowerCase();
                if (customWords.contains(text)) ;
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


    private ArrayList<String> getCustomList() {

        ArrayList<String> worldList = new ArrayList<>();

        String[] customkeywordArray = new String[]{"Alert Box", "Algorithm",
                "Application", "Backup", "Control Panel", "Contextual Menu", "Dashboard",
                "Database", "DBMS", "Dialog Box", "Document", "Drag and Drop",
                "Drop Down Menu", "Driver", "Menu Bar", "Pop-Up",
                "Repository",
                "Scroll Bar",
                "Scrolling",
                "Status Bar",
                "Text Box",
                "Undo",
                "Utility",
                "Toolbar",
                "JDBC",
                "Artificial Intelligence",
                "Augmented Reality",
                "Biometrics",
                "Client-Server Model",
                "Configuration",
                "Cryptography",
                "Crossplatform",
                "Data Mining",
                "Domain",
                "E-learning",
                "Encryption",
                "Enterprise",
                "End User",
                "File System",
                "Flowchart",
                "Graphics",
                "Log On",
                "Login",
                "Load Balancing",
                "Multimedia",
                "Network",
                "Password",
                "Platform",
                "Refresh",
                "Speech Recognition",
                "User Interface",
                "Username",
                "Website",
                "Web Server",
                "Web Application",
                "Upload",
                "Streaming",
                "Social Networking",
                "Social Media",
                "Servlet",
                "Search Engine",
                "Navigation Bar",
                "Home Page",
                "E-commerce",
                "Cloud Computing",
                "Big Data",
                "Android",
                "MySQL",
                "openGL",
                "SaaS",
                "SQL",
                "Ajax",
                "HTML",
                "JavaScript",
                "JQuery",
                "NoSQL",
                "SOAP",
                "Socket",
                "XHTML",
                "Hibernate",
                "XML"};

        for (int start = 0; start < customkeywordArray.length; start++) {

            worldList.add(customkeywordArray[start].toLowerCase().trim());
        }

    }
}
