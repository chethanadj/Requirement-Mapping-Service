package com.sceptra.requestor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;


public class NLPServiceRequester {

    @Autowired
    HTTPRequester requester;

    public static String NLPSERVICEURI="http://127.0.0.1:5000/getwordlist";

    public ArrayList<String> getCustomFilteredWordList(String description) {
  

    ArrayList<String> wordList = new ArrayList<>();
    JsonObject json = new JsonObject();
	json.addProperty("description",description);
    HttpResponse response = requester.postRequest(NLPSERVICEURI, json);

    HttpEntity entity = response.getEntity();
    String entityString = "";
		try

    {
        entityString = EntityUtils.toString(entity);
    } catch(
    ParseException e)

    {
        e.printStackTrace();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    }finally

    {

    }

    JsonParser parser = new JsonParser();
    JsonArray jsonArray = parser.parse(entityString).getAsJsonArray();
		jsonArray.forEach(jsonElement ->wordList.add(jsonElement.getAsString()));
        
        return wordList;
}

}
