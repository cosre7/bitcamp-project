package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Board;

public class BoardList {

  // 공통으로 사용하는 값은 스태틱 필드로 선언한다. (다 같은 값을 쓸 것 -> 공통적으로 관리)
  static final int LENGTH = 100;

  // 개별적으로 관리해야 하는 값은 인스턴스 필드로 선언한다. 
  Board[] boards = new Board[LENGTH];   
  int size = 0;
}
