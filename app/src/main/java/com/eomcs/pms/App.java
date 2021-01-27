package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    // 각 게시판 데이터를 저장할 메모리 준비
    BoardHandler boardList1 = new BoardHandler();
    // BoardList 설계도에 따라 만든 인스턴스 주소
    BoardHandler boardList2 = new BoardHandler();
    BoardHandler boardList3 = new BoardHandler();
    BoardHandler boardList4 = new BoardHandler();
    BoardHandler boardList5 = new BoardHandler();
    BoardHandler boardList6 = new BoardHandler();
    // 게시판 add, list를 담당하는 BoardHandler는 1개만 필요
    // BoardList 설계도에 따라 boardList 인스턴스를 만들어서 여러 개의 게시판을 사용할 수 있음
    // => 인스턴스 필드를 사용하는 이유

    // 각 회원 목록 데이터를 저장할 메모리 준비
    MemberHandler memberList = new MemberHandler();
    // 유효한 인스턴스 주소 준비 => new MemberHandler();

    // 각 프로젝트 목록 데이터를 저장할 메모리 준비
    // - 생성자에서 MemberHandler 객체를 주입하라고 강요한다.
    // - ProjectHandler 객체를 만들려면 반드시 주입해야 한다.
    ProjectHandler projectList = new ProjectHandler(memberList);
    // new 명령어에서 괄호 안에 무엇이 있다 -> 생성자 문법이다

    // 각 작업 목록 데이터를 저장할 메모리 준비
    // - 생성자에서 MemberHandler 객체를 주입하라고 강요한다. -> 생성자를 쓰는 이유
    // - TaskHandler 객체를 만들려면 반드시 주입해야 한다.
    TaskHandler taskList = new TaskHandler(memberList);

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            memberList.add();
            break;
          case "/member/list":
            memberList.list();
            break;
          case "/project/add":
            projectList.add();
            break;
          case "/project/list":
            projectList.list();
            break;
          case "/task/add":
            taskList.add();
            break;
          case "/task/list":
            taskList.list();
            break;
          case "/board/add":
            boardList1.add();
            break;
          case "/board/list":
            boardList1.list();
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
