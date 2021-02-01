package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Project;

public class ProjectList {
  static final int DEFAULT_CAPACITY = 100;
  // 의존 객체(dependency)를 담을 인스턴스 필드
  // - 메서드가 작업할 때 사용할 객체를 담는다.
  MemberHandler memberList;
  Project[] projects = new Project[DEFAULT_CAPACITY];
  int size = 0;

  void add(Project p) {
    this.projects[this.size++] = p;
  }

  Project[] toArray() {
    Project[] arr = new Project[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = projects[i];
    }
    return arr;
  }

  Project get(int projectNo) {
    int i = indexOf(projectNo);
    if (i == -1)
      return null;
    return projects[i];
  }

  void delete(int projectNo) {
    int index = indexOf(projectNo);

    if (index == -1)
      return;

    for (int x = index + 1; x < this.size; x++) {
      this.projects[x-1] = this.projects[x];
    }
    projects[--this.size] = null;
  }

  //프로젝트 번호에 해당하는 인스턴스를 배열에서 찾아 그 인덱스를 리턴한다. 
  int indexOf(int projectNo) {
    for (int i = 0; i < this.size; i++) {
      Project project = this.projects[i];
      if (project.no == projectNo) {
        return i;
      }
    }
    return -1;
  }

}
