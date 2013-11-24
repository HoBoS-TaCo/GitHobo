package com.github.hobos_taco.githobo.user;

import java.util.HashMap;

import com.github.hobos_taco.githobo.Constants;
import com.github.hobos_taco.githobo.util.GitHoboWebHelper;
import com.google.gson.internal.LinkedTreeMap;

@SuppressWarnings("unused")
public class Organization extends User {
  protected HashMap orgData = new HashMap<String, Object>();

  public Organization(String name) {
    orgData = GitHoboWebHelper.toHashMap(GitHoboWebHelper.get(Constants.URL_ORGANIZATIONS.concat(name)));
  }

  public Organization(HashMap map) {
    orgData = map;
  }

  public Organization(LinkedTreeMap treeMap) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    for (Object key : treeMap.keySet()) {
      map.put((String)key, treeMap.get(map));
    }
    orgData = map;
  }
}
