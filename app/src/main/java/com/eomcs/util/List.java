package com.eomcs.util;

public class List {

  private Node first;
  private Node last;
  protected int size = 0;  
  // 상속받은 클래스에서 접근 허용
  public void add(Object obj) {
    Node node = new Node(obj);

    if (last == null) { // 연결 리스트의 첫 항목이라면,
      last = node;
      first = node;
    } else { // 연결리스트에 이미 항목이 있다면, 
      last.next = node; // 현재 마지막 상자의 다음 상자가 새 상자를 가리키게 한다.
      node.prev = last; // 새 상자에서 이전 상자로서 현재 마지막 상자를 가리키게 한다. 
      last = node; // 새 상자가 마지막 상자가 되게 한다.
    }

    size++;
  }

  public Object[] toArray() {
    Object[] arr = new Object[size];

    Node cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  public Object get(int index) {
    // no를 받게되면 board는 괜춘 member는? 이름으로 찾으면?
    // -> index를 받는다
    // index 유효 검증
    if (index < 0 || index >= this.size) {
      return null;
    }

    int count = 0;
    Node cursor = first;
    while (cursor != null) {
      if (index == count++) { // (cursor.obj.no == boardNo) -> 불가능 
        // ((board)cursor.obj.no == boardNo) -> 이것도 불가능
        return cursor.obj;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public boolean delete(Object obj) {
    Node cursor = first;
    while (cursor != null) {
      if (cursor.obj.equals(obj)) {
        // 인스턴스주소가 다르더라도 내용이 같으면 지운다
        // cursor의 obj와 파라미터로 받은 obj 비교
        this.size--;
        if (first == last) {
          first = last = null;
          return true;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        // 삭제했으면
        return true;
      }
      cursor = cursor.next;
    }
    // 삭제 못했으면
    return false;
  }

  public Object delete(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }

    Object deleted = null;
    int count = 0;
    Node cursor = first;
    while (cursor != null) {
      if (index == count++) {
        deleted = cursor.obj; // 삭제될 항목을 보관해 둔다.
        this.size--;
        if (first == last) {
          first = last = null;
          break;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        break;
      }
      cursor = cursor.next;
    }
    return deleted;
  }

  public int indexOf(Object obj) {
    Object[] list =  this.toArray();
    for (int i = 0; i < list.length; i++) {
      if (list[i].equals(obj)) {
        return i;
      }
    }
    return -1;
  }

  public int size() {
    return this.size;
  }
  private static class Node {
    // 어차피 여기서만 쓸 것이기 때문에 private 해도 됨

    Object obj;
    Node next;
    Node prev;

    Node(Object obj) {
      this.obj = obj;
    }
  }

  //  interface I {}

  public Iterator iterator() throws CloneNotSupportedException {

    //    class A implements I {}
    //    class B {}
    //    class {} // 익명 클래스
    //    class {}
    //    
    //    obj = new (); // 이름이 없는 클래스의 레퍼런스는 만들 수 없다.
    //    obj2 = new ();
    //    
    //    Object obj = new class extends X {};
    //    Object obj = new X() {};
    //    여기서 X는 인터페이스 이거나 수퍼클래스 이름이다.
    //    // 위의 두 코드가 같은 것 

    // class ListIterator implements Iterator { //원래
    Iterator iterator = new Iterator() { // 익명
      // 위의 코드는 수퍼클래스
      // Object obj = new Iterator() {
      // 위의 코드는 인터페이스
      int cursor = 0;

      @Override
      public boolean hasNext() {
        return cursor < List.this.size();
      }

      @Override
      public Object next() {
        return List.this.get(cursor++);
      }
    };
    // 생성된 객체주소
    return iterator;
  }


}
