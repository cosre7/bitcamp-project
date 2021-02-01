package com.eomcs.pms.handler;

import java.util.Arrays;
import com.eomcs.pms.domain.Task;

public class TaskList {
  static final int DEFAULT_CAPACITY = 100;
  MemberHandler memberList;
  Task[] tasks = new Task[DEFAULT_CAPACITY];
  int size = 0;

  void add(Task t) {
    if (this.size == this.tasks.length) {
      tasks = Arrays.copyOf(this.tasks, this.size + (this.size >> 1));
    }
    this.tasks[this.size++] = t;
  }

  Task[] toArray() {
    Task[] arr = new Task[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = tasks[i];
    }
    return arr;
  }

  Task get(int taskNo) {
    int i = indexOf(taskNo);
    if (i == -1) {
      return null;
    }
    return tasks[i];
  }

  void delete(int taskNo) {
    int index = indexOf(taskNo);

    if (index == -1)
      return;

    for (int x = index + 1; x < this.size; x++) {
      this.tasks[x-1] = this.tasks[x];
    }
    tasks[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다.
  }

  // 작업 번호에 해당하는 인스턴스를 배열에서 찾아 그 인덱스를 리턴한다. 
  int indexOf(int taskNo) {
    for (int i = 0; i < this.size; i++) {
      Task task = this.tasks[i];
      if (task.no == taskNo) {
        return i;
      }
    }
    return -1;
  }

}
