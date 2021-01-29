package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardHandler {

  Node first;
  Node last;
  int size = 0;

  public void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.no = Prompt.inputInt("번호? ");
    b.title = Prompt.inputString("제목? ");
    b.content = Prompt.inputString("내용? ");
    b.writer = Prompt.inputString("작성자? ");
    b.registeredDate = new Date(System.currentTimeMillis());

    //    Box box = new Box();
    //    box.board = b;
    // 아래의 코드와 같은 코드
    Node node = new Node(b);

    if (last == null) { // 연결 리스트의 첫 항목이라면, (첫 박스 = 마지막 박스)
      last = node;
      first = node;
      // firstBox = lastBox;

    } else { // 연결 리스트에 이미 항목이 있다면,
      last.next = node; // 현재 마지막 상자의 다음 상자가 새 상자를 가리키게 한다.
      node.prev = last; // 새 상자에서 이전 상자로서 현재 마지막 상자를 가리키게 한다.
      //라스트박스의 next에 box 주소를 넣는다.
      last = node; // 새 상자가 마지막 상자가 되게 한다.

    }
    this.size++;

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[게시글 목록]");

    Node cursor = first;

    while (cursor != null) {
      Board b = cursor.board;

      // 번호, 제목, 등록일, 작성자, 조회수, 좋아요
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.no, 
          b.title, 
          b.registeredDate, 
          b.writer, 
          b.viewCount,
          b.like);

      cursor = cursor.next;
      // 주소를 담는 변수 = 주소.next 변수에 들어있는 주소
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

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

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

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      Node cursor = first;
      while (cursor != null) {
        if (cursor.board == board) {
          if (first == last) {
            // 박스가 첫번째 박스이자 마지막 박스일 때
            // 즉, 박스가 하나밖에 없는데 삭제하려고 할 때
            first = last = null;
            break;
            // lastBox 에 null을 넣는다
            // lastBox를 firstBox에 넣는다.
            // 즉 null을 firstBox에 넣는다.
            // 결과적으로 둘 다 null이 된다.
          }
          if (cursor == first) {
            first = cursor.next;
            // 첫번째 상자를 지우면 첫번째 상자의 다음 상자를 현재 상자로 가리키게 만든다.
            cursor.prev = null;
          } else {
            cursor.prev.next = cursor.next;
            // 현재 상자(200)에서 가리키는 이전상자(100)로 가리킨다
            // 이전상자(100)가 가리키는 다음 상자를 현재 상자의 다음 상자(300)로 만든다.
            // 즉, 현재 상자가 300이 된다.
            // 200 상자는 가비지가 된다.
            if (cursor.next != null) {
              cursor.next.prev = cursor.prev;
            }
          }
          if (cursor == last) {
            last = cursor.prev;
          }
          this.size--;
          break;
        }
        cursor = cursor.next;
      }

      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

  // 게시글 번호에 해당하는 인스턴스를 찾아 리턴한다.
  Board findByNo(int boardNo) {
    Node cursor = first;
    while (cursor != null) {
      Board b = cursor.board;
      if (b.no == boardNo) {
        return b;
      }
      cursor = cursor.next;
    }
    return null;
  }

  static class Node {
    Board board;
    Node next;
    Node prev;

    Node(Board b) {
      this.board = b;
    }
  }
}






