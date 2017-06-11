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

    public ArrayList<String> getCustomFilteredWordList(String description) {

        HTTPRequester requester = new HTTPRequester();
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
        try {
            entityString = EntityUtils.toString(entity);
        } catch (ParseException e) {
            entityString = "[]";
        } catch (Exception e) {
            entityString = "[]";
        } finally {
            requester.closeConn();
        }
        {

            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(entityString).getAsJsonArray();
            for (int a = 0; a < jsonArray.size(); a++) {
                JsonObject jsonObject = jsonArray.get(a).getAsJsonObject();
                String word = jsonObject.getAsString();
                wordList.add(word);
            }

            return wordList;
        }

    }

}
