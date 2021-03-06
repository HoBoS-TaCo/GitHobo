package com.github.hobos_taco.githobo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;

import com.github.hobos_taco.githobo.user.User;
import com.github.hobos_taco.githobo.util.GitHoboWebHelper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

@SuppressWarnings("unused")
public class Repository {

  private HashMap repoData = new HashMap<String, Object>();

  public Repository(String ownerName, String repositoryName) {
    repoData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(createUrl(ownerName, repositoryName)));
  }

  public Repository(HashMap data) {
    repoData = data;
  }

  public Repository(HttpEntity entity) {
    repoData = GitHoboWebHelper.toHashMap(entity);
  }

  public int getId() { return (Integer)repoData.get("id"); }

  public User getOwner() { return new User((LinkedTreeMap)repoData.get("owner")); }

  public String getName() { return (String)repoData.get("name"); }

  public String getFullName() { return (String)repoData.get("full_name"); }

  public String getDescription() { return (String)repoData.get("description"); }

  public boolean isPrivate() { return (Boolean)repoData.get("private"); }

  public boolean isFork() { return (Boolean)repoData.get("fork"); }

  public String getApiUrl() { return (String)repoData.get("url"); }

  public String getWebUrl() { return (String)repoData.get("html_url"); }

  public String getCloneUrl() { return (String)repoData.get("clone_url"); }

  public String getGitUrl() { return (String)repoData.get("git_url"); }

  public String getSshUrl() { return (String)repoData.get("ssh_url"); }

  public String getSvnUrl() { return (String)repoData.get("svn_url"); }

  public String getMirrorUrl() { return (String)repoData.get("mirror_url"); }

  public String getForksUrl() { return (String)repoData.get("forks_url"); }

  public String getLabelsUrl() { return (String)repoData.get("labels_url"); }

  public String getReleasesUrl() { return (String)repoData.get("releases_url"); }

  public String getCommitsUrl() { return (String)repoData.get("commits_url"); }

  public String getNotificationsUrl() { return (String)repoData.get("notifications_url"); }

  public String getHomePage() { return (String)repoData.get("homepage"); }

  public String getLanguage() { return (String)repoData.get("language"); }

  public int getForksCount() { return (Integer)repoData.get("forks_count"); }

  public int getStargazersCount() { return (Integer)repoData.get("stargazers_count"); }

  public int getWatchersCount() { return (Integer)repoData.get("watchers_count"); }

  public int getSize() { return (Integer)repoData.get("size"); }

  public String getMasterBranch() { return (String)repoData.get("master_branch"); }

  public String getDefaultBranch() { return (String)repoData.get("default_branch"); }

  public int getOpenIssuesCount() { return (Integer)repoData.get("open_issues_count"); }

  public String getPushedAt() { return (String)repoData.get("pushed_at"); }

  public String getCreatedAt() { return (String)repoData.get("created_at"); }

  public String getUpdatedAt() { return (String)repoData.get("updated_at"); }

  public Release getRelease(int id) { return new Release(this, id); }

  public int getSubscribersCount() { return (Integer)repoData.get("subscribers_count"); }

  public User getOrganization() { return new User((HashMap)repoData.get("organization")); }

  /**
   * The repo where this was forked from
   */
  public Repository getParent() { return new Repository((HashMap)repoData.get("parent")); }

  /**
   * The original repo where this was forked from, all the way up the fork chain
   */
  public Repository getSource() { return new Repository((HashMap)repoData.get("source")); }

  public boolean hasIssues() { return (Boolean)repoData.get("has_issues"); }

  public boolean hasWiki() { return (Boolean)repoData.get("has_wiki"); }

  public boolean hasDownloads() { return (Boolean)repoData.get("has_downloads"); }

  public List<Repository> getForks() {
    HashMap[] forkedRepos = GitHoboWebHelper.toHashMapArray(GitHoboWebHelper.get(getForksUrl()));
    List<Repository> repos = new ArrayList<Repository>();
    for (HashMap map : forkedRepos) {
      repos.add(new Repository(map));
    }
    return repos;
  }

  public List<Label> getLabels() {
    HashMap[] forkedRepos = GitHoboWebHelper.toHashMapArray(GitHoboWebHelper.get(getLabelsUrl().replaceAll(Pattern.quote("{/name}"), "")));
    List<Label> repos = new ArrayList<Label>();
    for (HashMap map : forkedRepos) {
      repos.add(new Label(map));
    }
    return repos;
  }

  public static String createUrl(String owner, String repo) {
    return "https://api.github.com/repos/".concat(owner).concat("/").concat(repo);
  }

  public String asJson() {
    return new Gson().toJson(repoData);
  }
}
