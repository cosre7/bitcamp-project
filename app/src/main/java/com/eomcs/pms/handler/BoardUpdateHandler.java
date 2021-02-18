package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardUpdateHandler {

  //특정 클래스를 지정하기 보다는
  // List 규칙에 따라 사용할 수 있는 객체를 요구하라!
  // 그러면 훨씬 코드가 유연해진다. (인터페이스 > 추상클래스 > 클래스)
  private List<Board> boardList;
  // List이든 ArrayList이든 LinkedList이든 다 쓰기 위해 List 인터페이스 사용

  public BoardUpdateHandler(List<Board> boardList) {
    this.boardList = boardList;
  }

  public void update() {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String title = Prompt.inputString(String.format("제목(%s)? ", board.getTitle()));
    String content = Prompt.inputString(String.format("내용(%s)? ", board.getContent()));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      board.setTitle(title);
      board.setContent(content);
      System.out.println("게시글을 변경하였습니다.");

    } else {
      System.out.println("게시글 변경을 취소하였습니다.");
    }
  }

  private Board findByNo(int boardNo) {
    Board[] arr =  boardList.toArray(new Board[boardList.size()]);
    for (Board b : arr) {
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }
}






