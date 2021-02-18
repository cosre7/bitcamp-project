package com.eomcs.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.eomcs.pms.domain.Board;

public class BoardListHandler {

  //특정 클래스를 지정하기 보다는
  // List 규칙에 따라 사용할 수 있는 객체를 요구하라!
  // 그러면 훨씬 코드가 유연해진다. (인터페이스 > 추상클래스 > 클래스)
  private List<Board> boardList;
  // List이든 ArrayList이든 LinkedList이든 다 쓰기 위해 List 인터페이스 사용

  public BoardListHandler(List<Board> boardList) {
    this.boardList = boardList;
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("[게시글 목록]");

    Iterator<Board> iterator = boardList.iterator();

    while (iterator.hasNext()) {
      Board b = iterator.next();
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getRegisteredDate(), 
          b.getWriter(), 
          b.getViewCount(),
          b.getLike());
    }
  }
}






