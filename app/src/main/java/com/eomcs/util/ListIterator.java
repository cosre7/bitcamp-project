package com.eomcs.util;

public class ListIterator extends AbstractIterator {

  // 리스트에서 데이터를 꺼내려면 리스트 객체를 알아야 한다.
  List list;
  // 어디까지 꺼냈는지 확인하기 위해 cursor가 필요하다.
  int cursor = 0;

  public ListIterator(List list) {
    // 리스트는 복제할 필요가 없다.
    // delete 과정이 아닌 get으로 꺼내기 때문에
    // 때문에 예외처리도 필요없다.
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return cursor < list.size();
  }

  @Override
  public Object next() {
    return list.get(cursor++);

    // 위의 문장은 컴파일하면 다음 문장으로 바뀐다.
    //    int temp = cursor;
    //    cursor = cursor + 1;
    //    return list.get(temp);
  }
}
