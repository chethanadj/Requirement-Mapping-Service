package com.sceptra.requestor;


import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HTTPRequester {

    private CloseableHttpClient httpclient;
    public HTTPRequester() {
        this.httpclient = HttpClients.createDefault();
    }

    public CloseableHttpResponse putRequest(String uri, String body) {
        RequestBuilder requestBuilder = null;
        try {
            requestBuilder = RequestBuilder.put()
                    .setHeader("Content-Type", "application/json").setUri(uri)
                    .setEntity(new StringEntity(body));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return execute(requestBuilder.build());
    }

    public CloseableHttpResponse postRequest(String uri, JsonObject body) {
        RequestBuilder requestBuilder = null;
        try {
            requestBuilder = RequestBuilder.post()
                    .setHeader("Content-Type", "application/json").setUri(uri)
                    .setEntity(new StringEntity(body.toString()));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return execute(requestBuilder.build());
    }

    public CloseableHttpResponse getRequest(String uri) {

        String plainCreds = "neo4j:admin";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpUriRequest request = RequestBuilder.get()
                .setUri(uri)
                .addHeader("Authorization", "Basic " + base64Creds).build();
        return execute(request);
    }

    public CloseableHttpResponse getPlainRequest(String uri){
        HttpUriRequest request = RequestBuilder.get()
                .setUri(uri)
                .setHeader("Content-Type", "application/json").setUri(uri)
                .build();
        return execute(request);

    }

    private CloseableHttpResponse execute(HttpUriRequest request) {
        try {
            return httpclient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConn() {

        try {
            this.httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
