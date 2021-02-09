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

    class QueueIterator implements Iterator {
      Queue queue;
      // 바깥 클래스의 복제판 만들기

      public QueueIterator() throws CloneNotSupportedException {
        this.queue = Queue.this.clone();
        // 생성자에서 만들기
      }

      @Override
      public boolean hasNext() {
        return this.queue.size() > 0;
      }

      @Override
      public Object next() {
        return this.queue.poll();
      }
    }
    return new QueueIterator();
  }


}
