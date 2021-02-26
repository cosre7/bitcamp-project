package com.eomcs.pms;

import java.io.FileOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardSearchHandler;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.HelloHandler;
import com.eomcs.pms.handler.MemberAddHandler;
import com.eomcs.pms.handler.MemberDeleteHandler;
import com.eomcs.pms.handler.MemberDetailHandler;
import com.eomcs.pms.handler.MemberListHandler;
import com.eomcs.pms.handler.MemberUpdateHandler;
import com.eomcs.pms.handler.MemberValidatorHandler;
import com.eomcs.pms.handler.ProjectAddHandler;
import com.eomcs.pms.handler.ProjectDeleteHandler;
import com.eomcs.pms.handler.ProjectDetailHandler;
import com.eomcs.pms.handler.ProjectListHandler;
import com.eomcs.pms.handler.ProjectUpdateHandler;
import com.eomcs.pms.handler.TaskAddHandler;
import com.eomcs.pms.handler.TaskDeleteHandler;
import com.eomcs.pms.handler.TaskDetailHandler;
import com.eomcs.pms.handler.TaskListHandler;
import com.eomcs.pms.handler.TaskUpdateHandler;
import com.eomcs.util.Prompt;

public class App {
  // 한 클래스당 길이가 작을 수록 유지보수가 더 쉽다
  // => 한 클래스에 여러 메서드를 넣는 것 보다는 여러 클래스로 나누는 것이 낫다!
  // - command pattern의 핵심 -> 한 기능 한 클래스

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  public static void main(String[] args) {

    ArrayList<Board> boardList = new ArrayList<>();
    ArrayList<Member> memberList = new ArrayList<>();
    LinkedList<Project> projectList = new LinkedList<>();
    LinkedList<Task> taskList = new LinkedList<>();


    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler(boardList));
    commandMap.put("/board/list", new BoardListHandler(boardList));
    commandMap.put("/board/detail", new BoardDetailHandler(boardList));
    commandMap.put("/board/update", new BoardUpdateHandler(boardList));
    commandMap.put("/board/delete", new BoardDeleteHandler(boardList));

    commandMap.put("/member/add", new MemberAddHandler(memberList));
    commandMap.put("/member/list", new MemberListHandler(memberList));
    commandMap.put("/member/detail", new MemberDetailHandler(memberList));
    commandMap.put("/member/update", new MemberUpdateHandler(memberList));
    commandMap.put("/member/delete", new MemberDeleteHandler(memberList));
    MemberValidatorHandler memberValidatorHandler = new MemberValidatorHandler(memberList);

    commandMap.put("/project/add", new ProjectAddHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/list", new ProjectListHandler(projectList));
    commandMap.put("/project/detail", new ProjectDetailHandler(projectList));
    commandMap.put("/project/update", new ProjectUpdateHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/delete", new ProjectDeleteHandler(projectList));

    commandMap.put("/task/add", new TaskAddHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/list", new TaskListHandler(taskList));
    commandMap.put("/task/detail", new TaskDetailHandler(taskList));
    commandMap.put("/task/update", new TaskUpdateHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/delete", new TaskDeleteHandler(taskList));

    // 새 기능 추가
    commandMap.put("/board/search", new BoardSearchHandler(boardList));
    commandMap.put("/hello", new HelloHandler());

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
              Command commandHandler = commandMap.get(command);

              if (commandHandler == null) {
                System.out.println("실행할 수 없는 명령입니다.");
              } else {
                commandHandler.service();
                // 이제 명령어와 그 명령어를 처리하는 핸들러를 추가할 때마다
                // case 문을 추가할 필요가 없다.
              }
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

    // 게시글 데이터를 파일로 출력한다.

    try (FileOutputStream out = new FileOutputStream("boards.data")){

      // boards.data 파일 포맷
      // - 2바이트: 저장된 게시글 개수
      // - 게시글 데이터 목록
      //   - 4바이트: 게시글 번호
      //   - 게시글 제목
      //     - 2바이트: 게시글 제목의 바이트 배열 개수
      //     - x바이트: 게시글 제목의 바이트 배열
      //   - 게시글 내용
      //     - 2바이트: 게시글 내용의 바이트 배열 개수
      //     - x바이트: 게시글 내용의 바이트 배열
      //    - 작성자
      //     - 2바이트: 작성자의 바이트 배열 개수
      //     - x바이트: 작성자의 바이트 배열
      //    - 등록일
      //     - 2바이트: 등록일의 바이트 배열 개수
      //     - x바이트: 등록일의 바이트 배열
      int size = boardList.size(); 
      out.write(size >> 8);
      out.write(size);

      for (Board b : boardList) {
        // 게시글 번호
        out.write(b.getNo() >> 24);
        out.write(b.getNo() >> 16);
        out.write(b.getNo() >> 8);
        out.write(b.getNo());

        // 게시글 제목
        byte[] buf = b.getTitle().getBytes("UTF-8");
        // - 게시글 제목의 바이트 개수
        out.write(buf.length >> 8); // 최소 2바이트는 출력하자
        out.write(buf.length);
        // - 게시글 제목의 바이트 배열
        out.write(buf);

        // 게시글 내용
        buf = b.getContent().getBytes("UTF-8");
        out.write(buf.length >> 8); 
        out.write(buf.length);
        out.write(buf);

        // 작성자
        buf = b.getWriter().getBytes("UTF-8");
        out.write(buf.length >> 8); 
        out.write(buf.length);
        out.write(buf);

        // 등록일
        buf = b.getRegisteredDate().toString().getBytes("UTF-8");
        out.write(buf.length >> 8); 
        out.write(buf.length);
        out.write(buf);

        // 조회수
        out.write(b.getViewCount() >> 24); // 4바이트 저장
        out.write(b.getViewCount() >> 16);
        out.write(b.getViewCount() >> 8);
        out.write(b.getViewCount());
      }
    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
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
