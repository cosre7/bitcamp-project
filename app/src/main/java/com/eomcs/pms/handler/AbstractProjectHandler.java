package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Project;

public abstract class AbstractProjectHandler {

  protected List<Project> projectList;

  public AbstractProjectHandler(List<Project> projectList) {
    this.projectList = projectList;
  }

  public Project findByNo(int projectNo) {
    Project[] arr =  projectList.toArray(new Project[projectList.size()]);
    for (Project p : arr) {
      if (p.getNo() == projectNo) {
        return p;
      }
    }
    return null;
  }
}








