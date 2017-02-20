package com.sceptra.requestor;

/**
 * Created by chiranz on 2/12/17.
 */

import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HTTPRequester {

    private HttpClient httpclient;

    public HTTPRequester() {
        this.httpclient = HttpClients.createDefault();
    }

    public HttpResponse putRequest(String uri, String body) {
        RequestBuilder requestBuilder = null;
        try {
            requestBuilder = RequestBuilder.put().setHeader("Content-Type", "application/json").setUri(uri)
                    .setEntity(new StringEntity(body));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return execute(requestBuilder.build());
    }

    public HttpResponse postRequest(String uri, JsonObject body) {
        RequestBuilder requestBuilder = null;
        try {
            requestBuilder = RequestBuilder.post().setHeader("Content-Type", "application/json").setUri(uri)
                    .setEntity(new StringEntity(body.toString()));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return execute(requestBuilder.build());
    }

    public HttpResponse getRequest(String uri) {

        String plainCreds = "neo4j:admin";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpUriRequest request = RequestBuilder.get().setUri(uri).addHeader("Authorization", "Basic " + base64Creds).build();
        return execute(request);
    }

    private HttpResponse execute(HttpUriRequest request) {
        try {
            return httpclient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
