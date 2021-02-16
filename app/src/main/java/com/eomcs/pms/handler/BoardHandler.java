package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.List;
import com.eomcs.util.Prompt;

public class BoardHandler {

  private List<Board> boardList = new List<>();
  // List 클래스의 <E>를 <Board>로 하자!

  public void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.setNo(Prompt.inputInt("번호? "));
    b.setTitle(Prompt.inputString("제목? "));
    b.setContent(Prompt.inputString("내용? "));
    b.setWriter(Prompt.inputString("작성자? "));
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    boardList.add(b);
    // add의 파라미터가 Board obj로 한정
    // -> 제네릭을 쓰는 이유

    System.out.println("게시글을 등록하였습니다.");
  }


  public void list() throws CloneNotSupportedException {
    System.out.println("[게시글 목록]");

    // 방법1)
    //    Board[] arr = new Board[boardList.size()];
    //    boardList.toArray(arr);
    // 배열에 아무것도 없는 상황이 생김

    // 방법2)
    // 위의 코드보다 밑의 코드를 더 추천
    // 혹시 배열의 갯수가 boardList가 가지고 있는 값보다 작을 경우가 있는데
    // 새로 배열을 만들어서 리턴해줄 것. => 가장 안전한 방법
    Board[] arr = boardList.toArray(new Board[boardList.size()]);
    // 파라미터 타입 Board, 리턴타입 Board인 배열 생성
    // 형변환(타입 캐스팅) 필요 없다!

    for (Board b : arr) {
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getRegisteredDate(), 
          b.getWriter(), 
          b.getViewCount(),
          b.getLike());
    }

    /*
    Iterator iterator = boardList.iterator();

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
     */
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
    Board[] arr =  boardList.toArray(new Board[boardList.size()]);
    // 자기가 받은 배열이 가지고 있는 값보다 작으면
    // 새 배열 만들어서 리턴
    // new Board[0]을 받으면 무조건 새 배열을 만들게 된다 -> 가비지 발생
    // new Board[boardList.size()]을 받으면 boardList의 크기만큼 배열을 만들기 때문에
    // 새 배열을 만들지 않을 수 있다.
    for (Board b : arr) {
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }
}






