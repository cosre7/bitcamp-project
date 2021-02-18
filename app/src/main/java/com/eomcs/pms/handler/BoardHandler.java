package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardHandler {

  private ArrayList<Board> boardList = new ArrayList<>();
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
    //    Board[] arr = boardList.toArray(new Board[boardList.size()]);
    //    // 파라미터 타입 Board, 리턴타입 Board인 배열 생성
    //    // 형변환(타입 캐스팅) 필요 없다!
    //
    //    for (Board b : arr) {
    //      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
    //          b.getNo(), 
    //          b.getTitle(), 
    //          b.getRegisteredDate(), 
    //          b.getWriter(), 
    //          b.getViewCount(),
    //          b.getLike());
    //    }

    // Iterator 사용하여 데이터 조회하기
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
      boardList.remove(board); 

      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

  public void search() {
    String keyword = Prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      System.out.println("검색어를 입력하세요.");
      return;
    }

    // 검색 결과를 담을 컬렉션을 준비한다.
    ArrayList<Board> list = new ArrayList<>();

    // 전체 게시글을 가져와서 검색어를 포함하는 게시글을 찾는다.
    Board[] boards = boardList.toArray(new Board[boardList.size()]);
    for (Board b : boards) {
      if (b.getTitle().contains(keyword) ||
          b.getContent().contains(keyword) ||
          b.getWriter().contains(keyword)) {
        // 키워드가 포함된 b 찾기
        // 키워드가 포함되어 있으면 list에 포함
        list.add(b);
      }
    }

    if (list.size() == 0) {
      // list.isEmpty() 도 가능
      System.out.println("검색어에 해당하는 게시글이 없습니다.");
      return;
    }

    // 검색 결과를 출력한다.
    for (Board b : list) {
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getRegisteredDate(), 
          b.getWriter(), 
          b.getViewCount(),
          b.getLike());
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






