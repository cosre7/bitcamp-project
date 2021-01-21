package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class BoardHandler {
  static class Board {
    int no;
    String title;
    String content;
    String writer;
    Date registeredDate; 
    int viewCount;
  }

  static final int LENGTH = 100;
  static Board[] boards = new Board[LENGTH];
  static int size = 0;

  public static void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();
    b.no = Prompt.inputInt("번호? ");
    b.title = Prompt.inputString("제목? ");
    b.content = Prompt.inputString("내용? ");
    b.writer = Prompt.inputString("작성자? ");
    b.registeredDate = new java.sql.Date(System.currentTimeMillis());
    b.viewCount = 0;
    boards[size++] = b;

    System.out.println("게시글을 등록하였습니다.");
  }

  public static void list() {
    System.out.println("[게시글 목록]");
    for (int i = 0; i < size; i++) {
      Board b = boards[i];
      System.out.printf("%d, %s, %s, %s, %d\n", 
          b.no, b.title,  b.writer, b.registeredDate, b.viewCount);
    }
  }
}
