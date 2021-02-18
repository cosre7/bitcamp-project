package com.eomcs.pms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  public static void main(String[] args) throws CloneNotSupportedException {

    ArrayList<Board> boardList = new ArrayList<>();
    BoardAddHandler boardAddHandler = new BoardAddHandler(boardList);
    BoardListHandler boardListHandler = new BoardListHandler(boardList);
    BoardDetailHandler boardDetailHandler = new BoardDetailHandler(boardList);
    BoardUpdateHandler boardUpdateHandler = new BoardUpdateHandler(boardList);
    BoardDeleteHandler boardDeleteHandler = new BoardDeleteHandler(boardList);

    MemberHandler memberHandler = new MemberHandler();
    ProjectHandler projectHandler = new ProjectHandler(memberHandler);
    TaskHandler taskHandler = new TaskHandler(memberHandler);

    loop:
      while (true) {
        String command = com.eomcs.util.Prompt.inputString("명령> ");

        if (command.length() == 0) // 사용자가 빈 문자열을 입력하면 다시 입력하도록 요구한다.
          continue;

        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try {
          switch (command) {
            case "/member/add":
              memberHandler.add();
              break;
            case "/member/list":
              memberHandler.list();
              break;
            case "/member/detail":
              memberHandler.detail();
              break;  
            case "/member/update":
              memberHandler.update();
              break; 
            case "/member/delete":
              memberHandler.delete();
              break;
            case "/project/add":
              projectHandler.add();
              break;
            case "/project/list":
              projectHandler.list();
              break;
            case "/project/detail": 
              projectHandler.detail();
              break;  
            case "/project/update":
              projectHandler.update();
              break; 
            case "/project/delete":
              projectHandler.delete();
              break;
            case "/task/add":
              taskHandler.add();
              break;
            case "/task/list":
              taskHandler.list();
              break;
            case "/task/detail": 
              taskHandler.detail();
              break;  
            case "/task/update":
              taskHandler.update();
              break; 
            case "/task/delete":
              taskHandler.delete();
              break;
            case "/board/add":
              boardAddHandler.add();
              // boardAddHandler에 데이터가 들어있다 (add 메서드가 사용할 데이터)
              // add로 데이터를 더한다.
              // => 객체지향 문법
              break;
            case "/board/list":
              boardListHandler.list();
              // boardListHandler에 데이터가 들어있다 (list 메서드가 사용할 데이터)
              // list로 데이터의 목록을 조회한다.
              // => 객체지향 문법
              break;
            case "/board/detail":
              boardDetailHandler.detail();
              break;  
            case "/board/update":
              boardUpdateHandler.update();
              break; 
            case "/board/delete":
              boardDeleteHandler.delete();
              break;
            case "history": // <== history 명령 추가
              printCommandHistory(commandStack.iterator());
              break;
            case "history2":
              printCommandHistory(commandQueue.iterator());
              break;
            case "quit":
            case "exit":
              System.out.println("안녕!");
              break loop;
            default:
              System.out.println("실행할 수 없는 명령입니다.");
          }
        } catch (Exception e) {
          System.out.println("----------------------------------------");                                                       
          System.out.printf("명령어 실행 중 오류 발생: %s - %s\n", 
              e.getClass().getName(), e.getMessage());
          // 예외가 발생한 클래스의 이름, 예외 메시지
          // 예외 처리 -> 예외가 발생했을 때 프로그램을 멈추지 않고
          // 오류 메시지만 출력하고 프로그램은 계속해서 작동되도록 하기 위함
          System.out.println("----------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }

  static void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.inputString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }
}
