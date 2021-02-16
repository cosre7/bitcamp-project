package com.eomcs.util;

import java.lang.reflect.Array;

public class List<E> {
  // 리스트 객체를 만들 때 넘어오는 값을 E라고 한다.

  private Node first;
  private Node last;
  protected int size = 0;  
  // 상속받은 클래스에서 접근 허용
  public void add(E obj) {
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

  // 제네릭에서 지정한 타입의 배열을 만들어 리턴한다.
  // @SuppressWarnings  - 경고를 억제하다
  // - 컴파일러가 타입이 맞는지 확인할 수 없는 경우 경고를 띄우는데
  //   '그 경고를 띄우지 말라고 지정하고 싶다면'
  //   다음 애노테이션을 붙인다.
  @SuppressWarnings("unchecked") 
  public E[] toArray(E[] arr) {

    if (arr.length < size) {
      // 파라미터로 받은 배열이 현재 저장된 항목의 크기보다 작을 경우
      // 새 배열을 만든다.
      arr = (E[]) Array.newInstance(arr.getClass().getComponentType(), size);
      // E[] arr 와 똑같은 배열을 만들고 싶을 때 
      // arr.getClass().getComponentType() 를 사용해서
      // 배열의 한 항목의 타입을 알아낸 후 size만큼 배열을 만든다.
    }

    Node cursor = this.first;
    for (int i = 0; i < size; i++) {
      // while문으로 해도 상관은 없다
      // for문도 써보자~
      arr[i] = (E) cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  @SuppressWarnings("unchecked")
  public E get(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }

    int count = 0;
    Node cursor = first;
    while (cursor != null) {
      if (index == count++) { 
        return (E) cursor.obj;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public boolean delete(E obj) {
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

  @SuppressWarnings("unchecked")
  public E delete(int index) {
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
    return (E) deleted;
  }

  public int indexOf(E obj) {
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

  public Iterator iterator() throws CloneNotSupportedException {

    return new Iterator() {
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
  }
}


