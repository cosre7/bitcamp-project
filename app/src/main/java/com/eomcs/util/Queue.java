package com.eomcs.util;

public class Queue extends List {

  public boolean offer(Object e) {
    // e = element (요소)
    this.add(e);
    return true; // 무조건 add
  }

  public Object poll() {
    return this.get(0); // 젤 처음 것을 꺼낸다.
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Queue queue = new Queue();
    for (int i = 0; i < this.size; i++) {
      queue.offer(this.get(i)); //i 번째 값을 가져와서 queue의 offer에 넣는다.
    }
    return queue;
  }
}
