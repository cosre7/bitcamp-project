package com.eomcs.util;

public class Queue extends List implements Cloneable {

  public boolean offer(Object e) {
    // e = element (요소)
    this.add(e);
    return true; // 무조건 add
  }

  public Object poll() { // 꺼내서 버리는 것이기 때문에 get이 아닌 delete를 쓴다
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

  @Override
  public Iterator iterator() throws CloneNotSupportedException {
    Queue clone = this.clone();
    return clone.new QueueIterator();
  }

  private class QueueIterator implements Iterator {
    // 내장 필드 Queue.this 에는 iterator() 메서드에서 생성한 Queue 복제판이 들어 있다.
    // 바깥 클래스의 인스턴스를 사용할 필요가 있을 때 -> non-static 이 편하다
    @Override
    public boolean hasNext() {
      return Queue.this.size() > 0;
    }

    @Override
    public Object next() {
      return Queue.this.poll();
    }
  }
}
