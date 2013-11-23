package com.github.hobos_taco.githobo.user;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.FileEntity;

import com.github.hobos_taco.githobo.Constants;
import com.github.hobos_taco.githobo.repository.Asset;
import com.github.hobos_taco.githobo.repository.Release;
import com.github.hobos_taco.githobo.repository.Repository;
import com.github.hobos_taco.githobo.util.GitHoboWebHelper;

public class AuthenticatedUser extends User {
  public AuthenticatedUser(String username, String password) {
    GitHoboWebHelper.setUserAuthentication("Basic " + new String(Base64.encodeBase64((username + ':' + password).getBytes())));
    userData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(Constants.URL_USER));
  }

  public int getTotalPrivateReposCount() { return (Integer)userData.get("total_private_repos"); }

  public int getOwnedPrivateReposCount() { return (Integer)userData.get("owned_private_repos"); }

  public int getPrivateGistsCount() { return (Integer)userData.get("private_gists"); }

  public int getDiskUsageCount() { return (Integer)userData.get("disk_usage"); }

  public int getCollaboratorsCount() { return (Integer)userData.get("collaborators"); }

  public User setName(String name) { return new User(GitHoboWebHelper.patch(Constants.URL_USER, "name", name)); }

  public User setEmail(String email) { return new User(GitHoboWebHelper.patch(Constants.URL_USER, "email", email)); }

  public User setBlog(String blog) { return new User(GitHoboWebHelper.patch(Constants.URL_USER, "blog", blog));}

  public User setCompany(String company) {
    return new User(GitHoboWebHelper.patch(Constants.URL_USER, "company", company));
  }

  public User setLocation(String location) {
    return new User(GitHoboWebHelper.patch(Constants.URL_USER, "location", location));
  }

  public User setHireable(boolean hireable) {
    return new User(GitHoboWebHelper.patch(Constants.URL_USER, "hireable", hireable));
  }

  public User setBiography(String biography) {
    return new User(GitHoboWebHelper.patch(Constants.URL_USER, "bio", biography));
  }

  public Plan getPlan() { return new Plan().get((HashMap)userData.get("plan")); }

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
    return new Repository(GitHoboWebHelper.post(Constants.URL_USERREPOS, GitHoboWebHelper.toStringEntity(newRepoMap)));
  }

  public Release createRelease(Repository repostitory, String tagName, String targetCommitish, String name, String body, boolean draft, boolean prerelease) {
    HashMap newReleaseMap = new HashMap<String, Object>();
    newReleaseMap.put("tag_name", tagName);
    newReleaseMap.put("target_commitish", targetCommitish);
    newReleaseMap.put("name", name);
    newReleaseMap.put("body", body);
    newReleaseMap.put("draft", draft);
    newReleaseMap.put("prerelease", prerelease);
    return new Release(GitHoboWebHelper.post(repostitory.getReleasesUrl().replaceAll(Pattern.quote("{/id}"), ""), GitHoboWebHelper.toStringEntity(newReleaseMap)));
  }

  public Asset createReleaseAsset(Release release, File upload, String uploadName) {
    return new Asset(GitHoboWebHelper.upload(release.getUploadUrl().replaceAll(Pattern.quote("{?name}"), ""), new FileEntity(upload), uploadName));
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
  }
}
