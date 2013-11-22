package com.github.hobos_taco.githobo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class GitHoboWebHelper {
  public static HashMap post(String username, String password, String path, HashMap toPost) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    uri.setHost("api.github.com");
    uri.setPath(path);
    if (username != null && password != null) {
      uri.setUserInfo(username, password);
    }
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpPost httppost = new HttpPost(String.valueOf(uri));
    httppost.setHeader("Accept", "application/vnd.github.v3+json");
    httppost.setHeader("User-Agent", "GitHobo");
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      httppost.setEntity(new StringEntity(new Gson().toJson(toPost)));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    CloseableHttpResponse response = null;
    try {
      response = httpclient.execute(httppost);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    assert response != null;
    try {
      String resp = EntityUtils.toString(response.getEntity());
      return new Gson().fromJson(resp, HashMap.class);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HashMap get(String path) {
    return get(null, null, path);
  }

  public static HashMap get(String username, String password, String path) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    uri.setHost("api.github.com");
    uri.setPath(path);
    if (username != null && password != null) {
      uri.setUserInfo(username, password);
    }
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpGet httpget = new HttpGet(String.valueOf(uri));
    httpget.setHeader("Accept", "application/vnd.github.v3+json");
    httpget.setHeader("User-Agent", "GitHobo");
    CloseableHttpClient httpclient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    try {
      response = httpclient.execute(httpget);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    assert response != null;
    try {
      String resp = EntityUtils.toString(response.getEntity());
      return new Gson().fromJson(resp, HashMap.class);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HashMap[] getUrl(String username, String password, String url) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    uri.setHost(url);
    if (username != null && password != null) {
      uri.setUserInfo(username, password);
    }
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpGet httpget = new HttpGet(String.valueOf(uri));
    httpget.setHeader("Accept", "application/vnd.github.v3+json");
    httpget.setHeader("User-Agent", "GitHobo");
    CloseableHttpClient httpclient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    try {
      response = httpclient.execute(httpget);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    assert response != null;
    try {
      String resp = EntityUtils.toString(response.getEntity());
      return new Gson().fromJson(resp, HashMap[].class);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HashMap patch(String username, String password, String path, HashMap toPost) {
    URIBuilder uri = new URIBuilder();
    uri.setScheme("https");
    uri.setHost("api.github.com");
    uri.setPath(path);
    if (username != null && password != null) {
      uri.setUserInfo(username, password);
    }
    try {
      uri.build();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    HttpPatch httppatch = new HttpPatch(String.valueOf(uri));
    httppatch.setHeader("Accept", "application/vnd.github.v3+json");
    httppatch.setHeader("User-Agent", "GitHobo");
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      httppatch.setEntity(new StringEntity(new Gson().toJson(toPost)));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    CloseableHttpResponse response = null;
    try {
      response = httpclient.execute(httppatch);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    assert response != null;
    try {
      String resp = EntityUtils.toString(response.getEntity());
      return new Gson().fromJson(resp, HashMap.class);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
