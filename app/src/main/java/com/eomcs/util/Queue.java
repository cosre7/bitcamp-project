package com.eomcs.util;

public class Queue extends List implements Cloneable {

  public boolean offer(Object e) {
    // e = element (요소)
    this.add(e);
    return true; // 무조건 add
  }

  public Object poll() {
    return this.delete(0); // 젤 처음 것을 꺼낸다.
  }

  @Override
  public Queue clone() throws CloneNotSupportedException {
    // deep copy
    Queue queue = new Queue();
    // get을 사용하는 방법보다 실행 속도가 빠르다.
    Object[] values = this.toArray();
    for (Object value : values) {
      queue.offer(value);
    }
    return queue;
  }
}
