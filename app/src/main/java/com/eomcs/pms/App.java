package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.BoardList;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    // 각 게시판 데이터를 저장할 메모리 준비
    BoardList boardList1 = new BoardList();
    // BoardList 설계도에 따라 만든 인스턴스 주소
    BoardList boardList2 = new BoardList();
    BoardList boardList3 = new BoardList();
    BoardList boardList4 = new BoardList();
    BoardList boardList5 = new BoardList();
    BoardList boardList6 = new BoardList();
    // 게시판 add, list를 담당하는 BoardHandler는 1개만 필요
    // BoardList 설계도에 따라 boardList 인스턴스를 만들어서 여러 개의 게시판을 사용할 수 있음
    // => 인스턴스 필드를 사용하는 이유

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            MemberHandler.add();
            break;
          case "/member/list":
            MemberHandler.list();
            break;
          case "/project/add":
            ProjectHandler.add();
            break;
          case "/project/list":
            ProjectHandler.list();
            break;
          case "/task/add":
            TaskHandler.add();
            break;
          case "/task/list":
            TaskHandler.list();
            break;
          case "/board/add":
            BoardHandler.add(boardList1);
            break;
          case "/board/list":
            BoardHandler.list(boardList1);
            break;
          case "/board2/add":
            BoardHandler.add(boardList2);
            break;
          case "/board2/list":
            BoardHandler.list(boardList2);
            break;
          case "/board3/add":
            BoardHandler.add(boardList3);
            break;
          case "/board3/list":
            BoardHandler.list(boardList3);
            break;
          case "/board4/add":
            BoardHandler.add(boardList4);
            break;
          case "/board4/list":
            BoardHandler.list(boardList4);
            break;
          case "/board5/add":
            BoardHandler.add(boardList5);
            break;
          case "/board5/list":
            BoardHandler.list(boardList5);
            break;
          case "/board6/add":
            BoardHandler.add(boardList6);
            break;
          case "/board6/list":
            BoardHandler.list(boardList6);
            break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }
}
