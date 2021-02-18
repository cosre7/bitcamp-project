package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Board;

public abstract class AbstractBoardHandler {
  //abstract : App 클래스에서 new BoardHandler(boardList)를 사용하지 못하게 하기 위함
  //그 후 직관적으로 abstract 클래스임을 확인시키기 위해 BoardHandler 이름 앞에 Abstract를 붙여준다

  //특정 클래스를 지정하기 보다는
  // List 규칙에 따라 사용할 수 있는 객체를 요구하라!
  // 그러면 훨씬 코드가 유연해진다. (인터페이스 > 추상클래스 > 클래스)
  protected List<Board> boardList;
  // List이든 ArrayList이든 LinkedList이든 다 쓰기 위해 List 인터페이스 사용

  public AbstractBoardHandler(List<Board> boardList) {
    this.boardList = boardList;
  }

  protected Board findByNo(int boardNo) {
    // protected: 서브 클래스까지만 공개
    Board[] arr =  boardList.toArray(new Board[boardList.size()]);
    for (Board b : arr) {
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }
}






