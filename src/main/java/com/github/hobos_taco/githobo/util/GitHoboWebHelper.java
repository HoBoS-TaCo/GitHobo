package com.github.hobos_taco.githobo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class GitHoboWebHelper {

  private static String userAuthentication = null;

  public static void setUserAuthentication(String auth) {
    userAuthentication = auth;
  }

  public static HttpEntity post(String url, HttpEntity entity) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    if (url.contains("uploads.github.com")) {
      uri.setHost("uploads.github.com");
      uri.setPath(url.substring("https://uploads.github.com".length()));
    } else {
      uri.setHost("api.github.com");
      uri.setPath(url.substring("https://api.github.com".length()));
    }
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpPost httpPost = new HttpPost(String.valueOf(uri));
    if (userAuthentication != null) {
      httpPost.setHeader("Authorization", userAuthentication);
    }
    if (entity instanceof StringEntity) {
      httpPost.setHeader("Accept", "application/vnd.github.v3+json");
    } else if (entity instanceof FileEntity) {
      httpPost.setHeader("Accept", "application/vnd.github.v3+zip");
      ;
    }
    httpPost.setHeader("User-Agent", "GitHobo");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    httpPost.setEntity(entity);
    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(httpPost);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    try {
      return response.getEntity();
    }
    catch (NullPointerException e) {
      System.out.println("No post response from " + url);
      e.printStackTrace();
    }
    return null;
  }

  public static HttpEntity get(String url) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    uri.setHost("api.github.com");
    uri.setPath(url.substring("https://api.github.com".length()));
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpGet httpGet = new HttpGet(String.valueOf(uri));
    if (userAuthentication != null) {
      httpGet.setHeader("Authorization", userAuthentication);
    }
    httpGet.setHeader("User-Agent", "GitHobo");
    httpGet.setHeader("Accept", "application/vnd.github.v3+json");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(httpGet);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    try {
      return response.getEntity();
    }
    catch (NullPointerException e) {
      System.out.println("No get response from " + url);
      e.printStackTrace();
    }
    return null;
  }

  public static HttpEntity patch(String url, String key, Object value) {
    try {
      return patch(url, new StringEntity(new Gson().toJson(new HashMap<String, Object>().put(key, value))));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HttpEntity patch(String url, HttpEntity entity) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    uri.setHost("api.github.com");
    uri.setPath(url.substring("https://api.github.com".length()));
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpPatch httpPatch = new HttpPatch(String.valueOf(uri));
    if (userAuthentication != null) {
      httpPatch.setHeader("Authorization", userAuthentication);
    }
    if (entity instanceof StringEntity) {
      httpPatch.setHeader("Accept", "application/vnd.github.v3+json");
    } else if (entity instanceof FileEntity) {
      httpPatch.setHeader("Accept", "application/vnd.github.v3+zip");
    }
    httpPatch.setHeader("User-Agent", "GitHobo");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    httpPatch.setEntity(entity);
    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(httpPatch);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    try {
      return response.getEntity();
    }
    catch (NullPointerException e) {
      System.out.println("No patch response from " + url);
      e.printStackTrace();
    }
    return null;
  }

  public static HttpEntity upload(String url, HttpEntity entity, String uploadName) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    uri.setHost("uploads.github.com");
    uri.setPath(url.substring("https://uploads.github.com".length()));
    uri.addParameter("name", uploadName);
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpPost httpPost = new HttpPost(String.valueOf(uri));
    if (userAuthentication != null) {
      httpPost.setHeader("Authorization", userAuthentication);
    }
    httpPost.setHeader("Content-Type", "application/vnd.github.v3+zip");
    httpPost.setHeader("User-Agent", "GitHobo");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    httpPost.setEntity(entity);
    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(httpPost);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    try {
      return response.getEntity();
    }
    catch (NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HashMap toHashMap(HttpEntity entity) {
    String json = null;
    try {
      json = EntityUtils.toString(entity);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return new Gson().fromJson(json, HashMap.class);
  }

  public static HashMap[] toHashMapArray(HttpEntity entity) {
    String json = null;
    try {
      json = EntityUtils.toString(entity);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return new Gson().fromJson(json, HashMap[].class);
  }

  public static StringEntity toStringEntity(HashMap hashMap) {
    try {
      return new StringEntity(new Gson().toJson(hashMap));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }
}