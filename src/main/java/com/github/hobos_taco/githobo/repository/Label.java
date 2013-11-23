package com.github.hobos_taco.githobo.repository;

import java.util.HashMap;

import com.github.hobos_taco.githobo.util.GitHoboWebHelper;

public class Label {

  private HashMap<String, Object> issueData = new HashMap<String, Object>();

  public Label(Repository repository, String name) {
    issueData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(repository.getApiUrl().concat("/labels/").concat(name)));
  }

  public Label(HashMap data) {
    issueData = data;
  }

  public String getIssueUrl() { return (String)issueData.get("url"); }

  public String getName() { return (String)issueData.get("name"); }

  public String getColour() { return (String)issueData.get("color"); }

}
