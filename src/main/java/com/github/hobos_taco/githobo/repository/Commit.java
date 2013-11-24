package com.github.hobos_taco.githobo.repository;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.github.hobos_taco.githobo.util.GitHoboWebHelper;
import com.google.gson.internal.LinkedTreeMap;

@SuppressWarnings("unused")
public class Commit {

  private HashMap commitData = new HashMap<String, Object>();

  public Commit(Repository repository, String sha) {
    commitData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(repository.getCommitsUrl().replaceAll(Pattern.quote("{/sha}"), "/".concat(sha))));
  }

  public Commit(HashMap data) {
    commitData = data;
  }

  public String getSha() { return (String)commitData.get("sha"); }

  public String getMessage() { return (String)((LinkedTreeMap)commitData.get("commit")).get("message"); }

}
