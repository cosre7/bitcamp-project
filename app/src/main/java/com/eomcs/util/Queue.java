package com.eomcs.util;

public class Queue<E> extends List<E> implements Cloneable {

  public boolean offer(E e) {
    // e = element (요소)
    this.add(e);
    return true; // 무조건 add
  }

  public E poll() { // 꺼내서 버리는 것이기 때문에 get이 아닌 delete를 쓴다
    return this.delete(0); // 젤 처음 것을 꺼낸다.
  }

  @SuppressWarnings("unchecked")
  @Override
  public Queue<E> clone() throws CloneNotSupportedException {
    // deep copy
    Queue<E> queue = new Queue<>();
    // get을 사용하는 방법보다 실행 속도가 빠르다.
    Object[] values = this.toArray();
    for (Object value : values) {
      queue.offer((E) value);
    }
    return queue;
  }

  @Override
  public Iterator<E> iterator() throws CloneNotSupportedException {
    // 익명 클래스 -> 인스턴스를 하나만 생성할 때 사용
    Queue<E> queue = this.clone();

    return new Iterator<E>() {

      @Override
      public boolean hasNext() {
        return queue.size() > 0;
      }

      @Override
      public E next() {
        return queue.poll();
      }
    };
  }
}
