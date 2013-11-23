package com.github.hobos_taco.githobo.repository;

import java.util.HashMap;

import org.apache.http.HttpEntity;

import com.github.hobos_taco.githobo.util.GitHoboWebHelper;

public class Asset {

  private HashMap assetData = new HashMap();

  public Asset(Release release, int assetId) {
    assetData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(release.getAssetsUrl().concat("/").concat(String.valueOf(assetId))));
  }

  public Asset(HashMap data) {
    assetData = data;
  }

  public Asset(HttpEntity entity) {
    assetData = GitHoboWebHelper.toHashMap(entity);
  }

  public String getApiUrl() { return (String)assetData.get("url"); }

  public Integer getId() { return (Integer)assetData.get("id"); }

  public String getName() { return (String)assetData.get("name"); }

  public String getLabel() { return (String)assetData.get("label"); }

  public String getContentType() { return (String)assetData.get("content_type"); }

  public String getState() { return (String)assetData.get("state"); }

  public Integer getSize() { return (Integer)assetData.get("size"); }

  public Integer getDownloadCount() { return (Integer)assetData.get("download_count"); }

  public String getCreatedAt() { return (String)assetData.get("created_at"); }

  public String getUpdatedAt() { return (String)assetData.get("updated_at"); }
}