package com.github.hobos_taco.githobo.user;

import java.util.HashMap;

import com.github.hobos_taco.githobo.repository.Repository;
import com.github.hobos_taco.githobo.util.GitHoboWebHelper;

public class AuthenticatedUser extends User {

  private String username;
  private String password;

  public AuthenticatedUser(String username, String password) {
    userData = GitHoboWebHelper.get(username, password, "/user");
    this.password = password;
    this.username = username;
  }

  public int getTotalPrivateReposCount() { return (Integer)userData.get("total_private_repos"); }

  public int getOwnedPrivateReposCount() { return (Integer)userData.get("owned_private_repos"); }

  public int getPrivateGistsCount() { return (Integer)userData.get("private_gists"); }

  public int getDiskUsageCount() { return (Integer)userData.get("disk_usage"); }

  public int getCollaboratorsCount() { return (Integer)userData.get("collaborators"); }

  public User setName(String name) {
    HashMap map = new HashMap<String, Object>();
    map.put("name", name);
    return new User(GitHoboWebHelper.patch(getLogin(), password, "/user", map));
  }

  public User setEmail(String email) {
    HashMap map = new HashMap<String, Object>();
    map.put("email", email);
    return new User(GitHoboWebHelper.patch(getLogin(), password, "/user", map));
  }

  public User setBlog(String blog) {
    HashMap map = new HashMap<String, Object>();
    map.put("blog", blog);
    return new User(GitHoboWebHelper.patch(getLogin(), password, "/user", map));
  }

  public User setCompany(String company) {
    HashMap map = new HashMap<String, Object>();
    map.put("company", company);
    return new User(GitHoboWebHelper.patch(getLogin(), password, "/user", map));
  }

  public User setLocation(String location) {
    HashMap map = new HashMap<String, Object>();
    map.put("location", location);
    return new User(GitHoboWebHelper.patch(getLogin(), password, "/user", map));
  }

  public User setHireable(boolean hireable) {
    HashMap map = new HashMap<String, Object>();
    map.put("hireable", hireable);
    return new User(GitHoboWebHelper.patch(getLogin(), password, "/user", map));
  }

  public User setBiography(String biography) {
    HashMap map = new HashMap<String, Object>();
    map.put("bio", biography);
    return new User(GitHoboWebHelper.patch(getLogin(), password, "/user", map));
  }

  public Plan getPlan() {
    return new Plan().get((HashMap)userData.get("plan"));
  }

  public class Plan {
    protected HashMap<String, Object> planData = new HashMap<String, Object>();

    protected Plan get(HashMap plan) {
      planData = plan;
      return this;
    }

    public String getName() { return (String)planData.get("name"); }

    public int getSpace() { return (Integer)planData.get("space"); }

    public int getCollaboratorsCount() { return (Integer)planData.get("collaborators"); }

    public int getPrivateReposCount() { return (Integer)planData.get("private_repos"); }

    public Repository createRepository(String name, String description, String homepage, boolean isPrivate, boolean hasIssues, boolean hasWiki, boolean hasDownloads, int teamId, boolean autoInitialCommit, String gitIgnoreTemplate) {
      HashMap newRepoMap = new HashMap<String, Object>();
      newRepoMap.put("name", name);
      newRepoMap.put("description", description);
      newRepoMap.put("homepage", homepage);
      newRepoMap.put("private", isPrivate);
      newRepoMap.put("has_issues", hasIssues);
      newRepoMap.put("has_wiki", hasWiki);
      newRepoMap.put("has_downloads", hasDownloads);
      newRepoMap.put("team_id", teamId);
      newRepoMap.put("auto_init", autoInitialCommit);
      newRepoMap.put("gitignore_template", gitIgnoreTemplate);
      return new Repository(GitHoboWebHelper.post(username, password, "/user/repos", newRepoMap));
    }
  }
}
