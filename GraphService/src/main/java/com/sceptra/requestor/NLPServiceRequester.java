package com.sceptra.requestor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;


public class NLPServiceRequester {

    public static String NLPSERVICEURI = "http://127.0.0.1:5000/getwordlist";

//    @Autowired
//    HTTPRequester requester;

    public ArrayList<String> getCustomFilteredWordList(String description) {

        HTTPRequester requester=new HTTPRequester();
        ArrayList<String> wordList = new ArrayList<>();
        JsonObject json = new JsonObject();
        json.addProperty("description", description);
        CloseableHttpResponse response = requester.postRequest(NLPSERVICEURI, json);

        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == 400) {
            requester.closeConn();
            return wordList;

        }
        String entityString = "";
        try

        {
            entityString = EntityUtils.toString(entity);
        } catch (ParseException e) {
            entityString = "[]";
//            e.printStackTrace();
        } catch (Exception e) {
            entityString = "[]";
//            e.printStackTrace();
        } finally {
            requester.closeConn();
        }
        {

            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(entityString).getAsJsonArray();
            jsonArray.forEach(jsonElement -> wordList.add(jsonElement.getAsString()));

            return wordList;
        }

    }

}
