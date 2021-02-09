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
    // 로컬 변수
    Queue queue = this.clone();
    // Iterator는 원래 Queue의 메소드이기 때문에 Queue.this가 아닌 그냥 this.

    class QueueIterator implements Iterator {
      // 만약 로컬 클래스에서 바깥 메서드의 로컬 변수를 사용한다면,
      // 컴파일러는 로컬 변수의 값을 저장할 필드를 자동 생성한다.

      @Override
      public boolean hasNext() {
        // 로컬 클래스에서는 바깥 메서드의 로컬 변수를 직접 사용할 수 있다. -> 이것만 기억하자
        // => 실제적으로는 로컬 클래스에 자동 생성된 필드를 가리킨다.
        return queue.size() > 0;
      }

      @Override
      public Object next() {
        // 로컬 클래스에서는 바깥 메서드의 로컬 변수를 직접 사용할 수 있다.
        // => 실제적으로는 로컬 클래스에 자동 생성된 필드를 가리킨다.
        return queue.poll();
      }
    }
    return new QueueIterator();
  }
}
