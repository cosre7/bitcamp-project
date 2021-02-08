package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.List;
import com.eomcs.util.ListIterator;
import com.eomcs.util.Prompt;

public class BoardHandler {

  private List boardList = new List();

  public void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.setNo(Prompt.inputInt("번호? "));
    b.setTitle(Prompt.inputString("제목? "));
    b.setContent(Prompt.inputString("내용? "));
    b.setWriter(Prompt.inputString("작성자? "));
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    boardList.add(b);

    //    1)
    //    Board obj = new Board();
    //    Member obj2 = new Member();
    //    Project obj3 = new Project();
    //    Task obj4 = new obj4

    //    2)
    //    Object obj = new Board();
    //    obj = new Member();
    //    obj = new Project();
    //    obj = new Task();

    // 1과 2는 같은 뜻

    System.out.println("게시글을 등록하였습니다.");
  }

  //  class A{}
  //  class B extends A{}
  //  class C extends B{}
  //  
  // A: 자동차 B: 승용차 C: SUV

  //  Object get() {
  //    return new A();
  //  }
  // 자동차 get() {
  // return new 승용차 (자동차) ();
  // }
  // get은 승용차를 리턴한다
  // => get은 자동차를 리턴한다.
  // -> 승용차도 자동차기 때문에 규칙에 어긋나지 않는다.

  // 승용차 get() {
  // return new 자동차 ();
  // } => 불가능

  public void list() {
    System.out.println("[게시글 목록]");

    //    Object obj = get();

    // B p = (B) get(); -> 컴파일러는 통과, 실행에서 문제
    // 리턴하는게 A인데 B로 강제로 변환해버리면 문제가 되는 것

    ListIterator iterator = new ListIterator(this.boardList);

    while (iterator.hasNext()) {
      Board b = (Board) iterator.next();
      // 번호, 제목, 등록일, 작성자, 조회수, 좋아요
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getRegisteredDate(), 
          b.getWriter(), 
          b.getViewCount(),
          b.getLike());
    }
  }

  public void detail() {
    System.out.println("[게시글 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    board.setViewCount(board.getViewCount() + 1);
    System.out.printf("제목: %s\n", board.getTitle());
    System.out.printf("내용: %s\n", board.getContent());
    System.out.printf("작성자: %s\n", board.getWriter());
    System.out.printf("등록일: %s\n", board.getRegisteredDate());
    System.out.printf("조회수: %d\n", board.getViewCount());

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

  public void delete() {
    System.out.println("[게시글 삭제]");

    int no = Prompt.inputInt("번호? ");

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      boardList.delete(board); // 오버로딩한 메서드를 사용하여 삭제한다.

      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

  private Board findByNo(int boardNo) {
    // 이건 Board에서만 사용할 것
    Object[] list =  boardList.toArray();
    for (Object obj : list) {
      Board b = (Board)obj;
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }
}






