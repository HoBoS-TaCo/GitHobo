package com.github.hobos_taco.githobo.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.github.hobos_taco.githobo.repository.Repository;
import com.github.hobos_taco.githobo.util.GitHoboWebHelper;
import com.google.gson.Gson;

public class User {
  protected HashMap userData = new HashMap<String, Object>();

  public User(String username) {
    userData = GitHoboWebHelper.get("/users/" + username);
  }

  public User(HashMap map) {
    userData = map;
  }

  protected User() {}

  /**
   * Get the {@link User}'s data as a JSON string
   *
   * @return The
   */
  public String asJson() {
    return new Gson().toJson(userData);
  }

  /**
   * @return The {@link User}'s login, aka username as a string.
   */
  public String getLogin() { return (String)userData.get("login"); }

  /**
   * @return The {@link User}'s id as an integer.
   */
  public int getId() { return (Integer)userData.get("id"); }

  /**
   * @return The {@link User}'s github avatar, aka icon url, as an string.
   */
  public String getAvatarUrl() { return (String)userData.get("avatar_url"); }

  /**
   * @return The {@link User}'s Gravatar id as an hexcode string.
   */
  public String getGravatarId() { return (String)userData.get("gravatar_id"); }

  /**
   * @return The {@link User}'s github api url as a string.
   */
  public String getApiUrl() { return (String)userData.get("url"); }

  /**
   * @return The {@link User}'s github user page url as a string.
   */
  public String getWebUrl() { return (String)userData.get("html_url"); }

  /**
   * @return A Lis {@link User}'s github api url as a string.
   */
  public String getFollowersUrl() { return (String)userData.get("followers_url"); }

  public List<User> getFollowers() {
    String followersUrl = ((String)userData.get("followers_url"));
    HashMap[] mappedUsers = GitHoboWebHelper.getUrl(null, null, followersUrl);
    List<User> users = new ArrayList<User>();
    for (HashMap map : mappedUsers) {
      users.add(new User(String.valueOf(map.get("login"))));
    }
    return users;
  }

  public String getFollowingUrl() {
    return ((String)userData.get("following_url")).replaceAll(Pattern.quote("{/other_user}"), "");
  }

  public String getFollowingUrl(String followedUser) {
    return getFollowingUrl().concat("/").concat(followedUser);
  }

  public String getFollowingUrl(User followedUser) {
    return getFollowingUrl().concat("/").concat(followedUser.getLogin());
  }

  public List<User> getFollowing() {
    HashMap[] mappedUsers = GitHoboWebHelper.getUrl(null, null, getFollowingUrl());
    List<User> users = new ArrayList<User>();
    for (HashMap map : mappedUsers) {
      users.add(new User(String.valueOf(map.get("login"))));
    }
    return users;
  }

  public String getGistsUrl() {
    return ((String)userData.get("gists_url")).replaceAll(Pattern.quote("{/gist_id}"), "");
  }

  public String getGistsUrl(int gistId) {
    return getGistsUrl().concat("/").concat(String.valueOf(gistId));
  }

  /*public List<Gist> getGists() {
    HashMap[] mappedUsers = GitHoboWebHelper.getUrl(null, null, getGistsUrl());
    List<Gist> gists = new ArrayList<Gist>();
    for (HashMap map : mappedUsers) {
      gists.add(new Gist().get(map));
    }
    return gists;
  }*/

  public String getStarredUrl() {
    return ((String)userData.get("starred_url")).replaceAll(Pattern.quote("{/owner}{/repo}"), "");
  }

  public List<Repository> getStarred() {
    HashMap[] mappedUsers = GitHoboWebHelper.getUrl(null, null, getStarredUrl());
    List<Repository> repos = new ArrayList<Repository>();
    for (HashMap map : mappedUsers) {
      repos.add(new Repository(map));
    }
    return repos;
  }

  public String getSubscriptionsUrl() { return (String)userData.get("subscriptions_url"); }

  public String getOrganizationsUrl() { return (String)userData.get("organizations_url"); }

  public String getEventsUrl() {
    return ((String)userData.get("events_url")).replaceAll(Pattern.quote("{/privacy}"), "");
  }

  //TODO Events objects
  public String getEventsUrl(String privacy) {
    return getEventsUrl().concat("/").concat(privacy);
  }

  public String getRecievedEventsUrl() { return (String)userData.get("received_events_url"); }

  public String getType() { return (String)userData.get("type"); }

  public Boolean isSiteAdmin() { return (Boolean)userData.get("site_admin"); }

  public String getName() { return (String)userData.get("name"); }

  public String getCompany() { return (String)userData.get("company"); }

  public String getBlog() { return (String)userData.get("blog"); }

  public String getLocation() { return (String)userData.get("location"); }

  public String getEmail() { return (String)userData.get("email"); }

  public Boolean isHireable() { return (Boolean)userData.get("hireable"); }

  public String getBiography() { return (String)userData.get("bio"); }

  public int getPublicReposCount() { return (Integer)userData.get("public_repos"); }

  public int getPublicGistsCount() { return (Integer)userData.get("public_gists"); }

  public int getFollowersCount() { return (Integer)userData.get("followers"); }

  public int getFollowingCount() { return (Integer)userData.get("following"); }

  public String getCreatedAt() { return (String)userData.get("created_at"); }

  public String getUpdatedAt() { return (String)userData.get("updated_at"); }

  public List<Repository> getRepositories() {
    HashMap[] mappedRepos = GitHoboWebHelper.getUrl(null, null, getRepositoriesUrl());
    List<Repository> repos = new ArrayList<Repository>();
    for (HashMap map : mappedRepos) {
      repos.add(new Repository(map));
    }
    return repos;
  }

  public String getRepositoriesUrl() {
    return "https://api.github.com/users/" + getLogin() + "/repos";
  }
}
