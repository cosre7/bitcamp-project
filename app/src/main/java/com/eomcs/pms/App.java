package com.eomcs.pms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
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

  // VO 를 저장할 컬렉션 객체
  static ArrayList<Board> boardList = new ArrayList<>();
  static ArrayList<Member> memberList = new ArrayList<>();
  static LinkedList<Project> projectList = new LinkedList<>();
  static LinkedList<Task> taskList = new LinkedList<>();

  public static void main(String[] args) {

    loadBoards(); 
    loadMembers();
    loadProjects();
    loadTasks();

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

    saveBoards();
    saveMembers();
    saveProjects();
    saveTasks();

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

  static void loadBoards() {
    // 파일에서 데이터를 읽어온다. (데이터 로딩)
    try (FileInputStream in = new FileInputStream("boards.data")) {
      // boards.data 파일 포맷에 따라 데이터를 읽는다.
      // 1) 게시글 개수
      int size = in.read() << 8 | in.read();

      // 2) 게시글 개수 만큼 게시글을 읽는다.
      for (int i = 0; i < size; i++) {
        // 게시글 데이터를 저장할 객체 준비
        Board b = new Board();

        // 게시글 데이터를 읽어서 객체에 저장
        // - 게시글 번호를 읽어서 객체에 저장
        b.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // - 게시글 제목을 읽어서 객체에 저장
        int len = in.read() << 8 | in.read(); // 2바이트 정수값으로 만든다.
        byte[] buf = new byte[len];
        in.read(buf);
        b.setTitle(new String(buf, "UTF-8"));

        // - 게시글 내용을 읽어서 객체에 저장
        len = in.read() << 8 | in.read(); 
        buf = new byte[len];
        in.read(buf);
        b.setContent(new String(buf, "UTF-8"));

        // - 게시글 작성자를 읽어서 객체에 저장
        len = in.read() << 8 | in.read(); 
        buf = new byte[len];
        in.read(buf);
        b.setWriter(new String(buf, "UTF-8"));

        // - 게시글 등록일을 읽어서 객체에 저장
        len = in.read() << 8 | in.read(); 
        buf = new byte[len];
        in.read(buf);
        b.setRegisteredDate(Date.valueOf(new String(buf, "UTF-8"))); // 문자열로 읽어서 Date 객체로 만들어야 한다.

        // - 게시글 조회수를 읽어서 객체에 저장
        b.setViewCount(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read()); // 4바이트 값으로 만들기

        // 게시글 객체를 컬렉션에 저장
        boardList.add(b);
      }
      System.out.println("게시글 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
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
      //    - 4바이트: 조회수
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
      System.out.println("게시글 데이터 저장!");

    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    } 
  }

  static void loadMembers() {
    try (FileInputStream in = new FileInputStream("members.data")) {
      // 1) 멤버 수
      int size = in.read() << 8 | in.read();

      // 2) 멤버 수만큼 읽는다.
      for (int i = 0; i < size; i++) {
        // 멤버 데이터를 저장할 객체 준비
        Member m = new Member();

        // 멤버 데이터를 읽어서 객체에 저장
        // - 멤버 번호를 읽어서 객체에 저장
        m.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // - 멤버 이름을 읽어서 객체에 저장
        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        m.setName(new String(buf, "utf-8"));

        // - 멤버 이메일을 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        m.setEmail(new String(buf, "utf-8"));

        // - 멤버 password를 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        m.setPassword(new String(buf, "utf-8"));

        // - 멤버 photo를 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        m.setPhoto(new String(buf, "utf-8"));

        // - 멤버 tel을 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        m.setTel(new String(buf, "utf-8"));

        // - 멤버 registeredDate를 읽어서 객체에 저장
        m.setRegisteredDate(Date.valueOf(new String(buf, "utf-8")));

        memberList.add(m);
      }
      System.out.println("멤버 데이터 로딩!");

    } catch (Exception e){
      System.out.println("멤버 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {
    try (FileOutputStream out = new FileOutputStream("members.data")) {
      int size = memberList.size();
      out.write(size >> 8);
      out.write(size);

      for (Member m : memberList) {

        // 멤버 번호
        out.write(m.getNo() >> 24);
        out.write(m.getNo() >> 16);
        out.write(m.getNo() >> 8);
        out.write(m.getNo());

        // 멤버 이름
        byte[] buf = m.getName().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 멤버 이메일
        buf = m.getEmail().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 멤버 password
        buf = m.getPassword().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 멤버 photo
        buf = m.getPhoto().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 멤버 tel
        buf = m.getTel().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 멤버 registeredDate
        buf = m.getRegisteredDate().toString().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);
      }
      System.out.println("멤버 데이터 저장!");

    } catch (Exception e) {
      System.out.println("멤버 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {
    try (FileInputStream in = new FileInputStream("projects.data")) {
      // 1) 프로젝트 수
      int size = in.read() << 8 | in.read();

      // 2) 프로젝트 수만큼 읽는다.
      for (int i = 0; i < size; i++) {
        // 프로젝트 데이터를 저장할 객체 준비
        Project p = new Project();

        // 프로젝트 데이터를 읽어서 객체에 저장
        // - 프로젝트 번호를 읽어서 객체에 저장
        p.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // - 프로젝트 제목을 읽어서 객체에 저장
        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        p.setTitle(new String(buf, "utf-8"));

        // - 프로젝트 내용을 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setContent(new String(buf, "utf-8"));

        // - 프로젝트 시작일 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setStartDate(Date.valueOf(new String(buf, "utf-8")));

        // - 프로젝트 종료일 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setEndDate(Date.valueOf(new String(buf, "utf-8")));

        // - 프로젝트 담당자를 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setOwner(new String(buf, "utf-8"));

        // - 프로젝트 멤버를 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setMembers(new String(buf, "utf-8"));

        projectList.add(p);
      }
      System.out.println("프로젝트 데이터 로딩!");

    } catch (Exception e){
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {
    try (FileOutputStream out = new FileOutputStream("projects.data")) {
      int size = projectList.size();
      out.write(size >> 8);
      out.write(size);

      for (Project p : projectList) {

        // 프로젝트 번호
        out.write(p.getNo() >> 24);
        out.write(p.getNo() >> 16);
        out.write(p.getNo() >> 8);
        out.write(p.getNo());

        // 프로젝트 제목
        byte[] buf = p.getTitle().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 내용
        buf = p.getContent().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 시작일
        buf = p.getStartDate().toString().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 종료일
        buf = p.getEndDate().toString().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 담당자
        buf = p.getOwner().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 멤버
        buf = p.getMembers().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);
      }
      System.out.println("프로젝트 데이터 저장!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {
    try (FileInputStream in = new FileInputStream("tasks.data")) {
      // 1) 작업 수
      int size = in.read() << 8 | in.read();

      // 2) 작업 수만큼 읽는다.
      for (int i = 0; i < size; i++) {
        // 작업 데이터를 저장할 객체 준비
        Task t = new Task();

        // 작업 데이터를 읽어서 객체에 저장
        // - 작업 번호를 읽어서 객체에 저장
        t.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // - 작업 내용을 읽어서 객체에 저장
        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        t.setContent(new String(buf, "utf-8"));

        // - 작업 마감일 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        t.setDeadline(Date.valueOf(new String(buf, "utf-8")));

        // - 작업 상태를 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        t.setStatus(Integer.valueOf(new String(buf, "utf-8")));

        // - 작업 담당자를 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        t.setOwner(new String(buf, "utf-8"));

        taskList.add(t);
      }
      System.out.println("작업 데이터 로딩!");

    } catch (Exception e){
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {
    try (FileOutputStream out = new FileOutputStream("tasks.data")) {
      int size = taskList.size();
      out.write(size >> 8);
      out.write(size);

      for (Task t : taskList) {

        // 작업 번호
        out.write(t.getNo() >> 24);
        out.write(t.getNo() >> 16);
        out.write(t.getNo() >> 8);
        out.write(t.getNo());

        // 작업 내용
        byte[] buf = t.getContent().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 작업 마감일
        buf = t.getDeadline().toString().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 작업 상태
        out.write(t.getStatus() >> 24);
        out.write(t.getStatus() >> 16);
        out.write(t.getStatus() >> 8);
        out.write(t.getStatus());

        // 작업 담당자
        buf = t.getOwner().getBytes("utf-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

      }
      System.out.println("작업 데이터 저장!");

    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
}
