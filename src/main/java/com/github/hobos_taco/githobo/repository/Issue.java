package com.github.hobos_taco.githobo.repository;

import java.util.HashMap;

import com.github.hobos_taco.githobo.util.GitHoboWebHelper;

public class Issue {

  private HashMap<String, Object> issueData = new HashMap<String, Object>();

  public Issue(String ownerName, String repositoryName) {
    issueData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(Repository.createUrl(ownerName, repositoryName).concat("/issues")));
  }

  public Issue(HashMap data) {
    issueData = data;
  }

  public int getId() { return (Integer)issueData.get("id"); }

}
