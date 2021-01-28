package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardHandler {

  //공통으로 사용하는 값은 스태틱 필드로 선언한다. (다 같은 값을 쓸 것 -> 공통적으로 관리)
  // 인스턴스가 없어도 작업할 수 있는 메소드 -> static으로 만들기
  static final int LENGTH = 100;

  // 개별적으로 관리해야 하는 값은 인스턴스 필드로 선언한다. 
  // 인스턴스가 있어야 작업할 수 있는 메소드 -> static을 빼고 만들기
  Board[] boards = new Board[LENGTH];   
  int size = 0;

  public void service () {
    loop:
      while (true) {
        System.out.println("메인 / 게시판 --------------------------------");
        System.out.println("1. 등록");
        System.out.println("2. 목록");
        System.out.println("3. 상세 보기");
        System.out.println("4. 변경");
        System.out.println("5. 삭제");
        System.out.println("0. 이전 메뉴");

        String command = com.eomcs.util.Prompt.inputString("게시판> ");
        System.out.println();

        switch (command) {
          case "1":
            this.add();
            break;
          case "2":
            this.list();
            break;
          case "3":
            this.detail();
            break;  
          case "4":
            this.update();
            break; 
          case "5":
            this.delete();
            break; 
          case "0":
            break loop;
          default:
            System.out.println("메뉴 번호가 맞지 않습니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력

      }
  }
  public void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.no = Prompt.inputInt("번호? ");
    b.title = Prompt.inputString("제목? ");
    b.content = Prompt.inputString("내용? ");
    b.writer = Prompt.inputString("작성자? ");
    b.registeredDate = new Date(System.currentTimeMillis());

    this.boards[this.size++] = b;

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[게시글 목록]");

    for (int i = 0; i < this.size; i++) {
      Board b = this.boards[i];

      if (b == null)
        continue;
      // continue: 아래쪽으로 가지 않고 i++로 가기
      // 원래 순서: i++ -> i < this.size -> Board b = this.board[i] ...
      // 번호, 제목, 등록일, 작성자, 조회수, 좋아요
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.no, 
          b.title, 
          b.registeredDate, 
          b.writer, 
          b.viewCount,
          b.like);
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

    board.viewCount++;
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("작성자: %s\n", board.writer);
    System.out.printf("등록일: %s\n", board.registeredDate);
    System.out.printf("조회수: %d\n", board.viewCount);
  }

  public void update() {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");   

    Board board = findByNo(no);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    } 

    String title = Prompt.inputString(String.format("제목(%s)? ", board.title));
    String content = Prompt.inputString(String.format("내용(%s)? ", board.content));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N)");

    if (input.equalsIgnoreCase("Y")) {
      board.title = title;
      board.content = content;
      System.out.println("게시글을 변경하였습니다.");

    } else {
      System.out.println("게시글 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[게시글 삭제]");

    int no = Prompt.inputInt("번호? ");

    int i = indexOf(no);

    if (i == -1) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    } 
    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N)");

    if (input.equalsIgnoreCase("Y")) {
      for (int x = i + 1; x < this.size; x++) {
        this.boards[x-1] = this.boards[x];
      }
      boards[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다.
      // 가비지 관리를 효율적으로 하기 위해 해주는 부분

      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

  // 게시글 번호에 해당하는 인스턴스를 배열에서 찾아 그 인덱스를 리턴한다.
  int indexOf(int boardNo) {
    for (int i = 0; i < this.size; i++) {
      Board board = this.boards[i];
      if (board.no == boardNo) {
        // 삭제 후에 앞으로 당겨서 null이 없게 만들기 때문에
        // board != null 을 할 필요가 없다.
        return i;
      }
    }
    return -1;
    // 원래 배열 인스턴스에 있을 수 없는 수
    // 배열은 0부터
  }

  // 게시글 번호에 해당하는 인스턴스를 찾아 리턴한다.
  Board findByNo(int boardNo) {
    int i = indexOf(boardNo);
    if (i == -1)
      return null;
    else
      return this.boards[i];
  }
}






