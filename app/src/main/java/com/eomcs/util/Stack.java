package com.eomcs.util;
// 상속을 이용해서 코드를 재사용(확장)하자!

public class Stack extends List {

  public Object push(Object item) {
    // 수퍼 클래스 List 의 메서드를 사용하여 항목을 추가한다.
    // 넣고 뺄때 어떤 타입인지 모르기 때문에 Object로 한다.
    this.add(item);
    return item;
  }

  public Object pop() {
    // 수퍼 클래스 List 의 메서드를 사용하여 항목을 꺼낸다.
    return this.delete(this.size - 1);
  }
}
