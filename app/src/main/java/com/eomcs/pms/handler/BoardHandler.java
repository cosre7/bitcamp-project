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

    for (int i = 0; i < this.size; i++) {
      Board board = this.boards[i];
      if (board.no == no) {
        board.viewCount++;
        System.out.printf("제목: %s\n", board.title);
        System.out.printf("내용: %s\n", board.content);
        System.out.printf("작성자: %s\n", board.writer);
        System.out.printf("등록일: %s\n", board.registeredDate);
        System.out.printf("조회수: %d\n", board.viewCount);
        return;
      }
    }
    System.out.println("해당 번호의 게시글이 없습니다.");
  }

  public void update() {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");   

    for (int i = 0; i < this.size; i++) {
      Board board = this.boards[i];
      if (board.no == no) {

        //        String promptCaption;
        //     1_1) promptCaption = "제목(" + board.title + ")?";
        //     1_2) String promptCaption = String.format("제목(%s)? ", board.title);
        // 1_1과 1_2는 같은 것.
        //        String str = Prompt.inputString(promptCaption);
        //        위의 3줄 코드가 아래의 제목(%s)? 코드를 풀어쓴 것이다.

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

        return;
      }
    }
    System.out.println("해당 번호의 게시글이 없습니다.");
  }

  public void delete() {
    System.out.println("[게시글 삭제]");

    int no = Prompt.inputInt("번호? ");

    for (int i = 0; i < this.size; i++) {
      Board board = this.boards[i];
      if (board.no == no) {
        String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N)");

        if (input.equalsIgnoreCase("Y")) {
          this.boards[i] = null;
          System.out.println("게시글을 삭제하였습니다.");

        } else {
          System.out.println("게시글 삭제를 취소하였습니다.");
        }

        return;
      }
    }
    System.out.println("해당 번호의 게시글이 없습니다.");

  }
}






