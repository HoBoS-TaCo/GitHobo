package com.github.hobos_taco.githobo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;

import com.github.hobos_taco.githobo.util.GitHoboWebHelper;

@SuppressWarnings("unused")
public class Release {

  private HashMap releaseData = new HashMap<String, Object>();

  public Release(Repository repository, int releaseId) {
    releaseData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(repository.getReleasesUrl().replaceAll(Pattern.quote("{/id}"), "/".concat(String.valueOf(releaseId)))));
  }

  public Release(HashMap data) {
    releaseData = data;
  }

  public Release(HttpEntity entity) {
    releaseData = GitHoboWebHelper.toHashMap(entity);
  }

  public String getApiUrl() { return (String)releaseData.get("url"); }

  public String getAssetsUrl() { return (String)releaseData.get("assets_url"); }

  public String getUploadUrl() { return (String)releaseData.get("upload_url"); }

  public String getWebUrl() { return (String)releaseData.get("html_url"); }

  public String getTarballUrl() { return (String)releaseData.get("tarball_url"); }

  public String getZipballUrl() { return (String)releaseData.get("zipball_url"); }

  public Integer getId() { return (Integer)releaseData.get("id"); }

  public String getTagName() { return (String)releaseData.get("tag_name"); }

  public String getBranch() { return (String)releaseData.get("target_commitish"); }

  public String getName() { return (String)releaseData.get("name"); }

  public String getDescription() { return (String)releaseData.get("body"); }

  public boolean isDraft() { return (Boolean)releaseData.get("draft"); }

  public boolean isPrerelease() { return (Boolean)releaseData.get("prerelease"); }

  public String getCreatedAt() { return (String)releaseData.get("created_at"); }

  public String getPublishedAt() { return (String)releaseData.get("published_at"); }

  public List<Asset> getAssets() {
    HashMap[] mappedRepos = GitHoboWebHelper.toHashMapArray(GitHoboWebHelper.get(getAssetsUrl()));
    List<Asset> repos = new ArrayList<Asset>();
    for (HashMap map : mappedRepos) {
      repos.add(new Asset(map));
    }
    return repos;
  }
}
