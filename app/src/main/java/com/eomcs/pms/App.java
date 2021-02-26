package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    try (DataInputStream in = new DataInputStream(new FileInputStream("boards.data"))) {

      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Board b = new Board();
        b.setNo(in.readInt());
        b.setTitle(in.readUTF());
        b.setContent(in.readUTF());
        b.setWriter(in.readUTF());
        b.setRegisteredDate(Date.valueOf(in.readUTF())); // 문자열로 읽어서 Date 객체로 만들어야 한다.
        b.setViewCount(in.readInt()); // 4바이트 값으로 만들기

        boardList.add(b);
      }
      System.out.println("게시글 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("boards.data"))) {

      out.writeInt(boardList.size());

      for (Board b : boardList) {
        out.writeInt(b.getNo());
        out.writeUTF(b.getTitle());
        out.writeUTF(b.getContent());
        out.writeUTF(b.getWriter());
        out.writeUTF(b.getRegisteredDate().toString());
        out.writeInt(b.getViewCount());
      }
      System.out.println("게시글 데이터 저장!");

    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    } 
  }

  static void loadMembers() {
    try (DataInputStream in = new DataInputStream(new FileInputStream("members.data"))) {

      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Member m = new Member();
        m.setNo(in.readInt());
        m.setName(in.readUTF());
        m.setEmail(in.readUTF());
        m.setPassword(in.readUTF());
        m.setPhoto(in.readUTF());
        m.setTel(in.readUTF());
        m.setRegisteredDate(Date.valueOf(in.readUTF()));

        memberList.add(m);
      }
      System.out.println("회원 데이터 로딩!");

    } catch (Exception e){
      System.out.println("회원 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("members.data"))) {
      out.writeInt(memberList.size());

      for (Member m : memberList) {
        out.writeInt(m.getNo());
        out.writeUTF(m.getName());
        out.writeUTF(m.getEmail());
        out.writeUTF(m.getPassword());
        out.writeUTF(m.getPhoto());
        out.writeUTF(m.getTel());
        out.writeUTF(m.getRegisteredDate().toString());
      }
      System.out.println("회원 데이터 저장!");

    } catch (Exception e) {
      System.out.println("회원 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {
    try (DataInputStream in = new DataInputStream(new FileInputStream("projects.data"))) {
      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Project p = new Project();
        p.setNo(in.readInt());
        p.setTitle(in.readUTF());
        p.setContent(in.readUTF());
        p.setStartDate(Date.valueOf(in.readUTF()));
        p.setEndDate(Date.valueOf(in.readUTF()));
        p.setOwner(in.readUTF());
        p.setMembers(in.readUTF());

        projectList.add(p);
      }
      System.out.println("프로젝트 데이터 로딩!");

    } catch (Exception e){
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("projects.data"))) {

      out.writeInt(projectList.size());

      for (Project p : projectList) {
        out.writeInt(p.getNo());
        out.writeUTF(p.getTitle());
        out.writeUTF(p.getContent());
        out.writeUTF(p.getStartDate().toString());
        out.writeUTF(p.getEndDate().toString());
        out.writeUTF(p.getOwner());
        out.writeUTF(p.getMembers());
      }
      System.out.println("프로젝트 데이터 저장!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {
    try (DataInputStream in = new DataInputStream(new FileInputStream("tasks.data"))) {

      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Task t = new Task();
        t.setNo(in.readInt());
        t.setContent(in.readUTF());
        t.setDeadline(Date.valueOf(in.readUTF()));
        t.setStatus(in.readInt());
        t.setOwner(in.readUTF());

        taskList.add(t);
      }
      System.out.println("작업 데이터 로딩!");

    } catch (Exception e){
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("tasks.data"))) {

      out.writeInt(taskList.size());

      for (Task t : taskList) {

        out.writeInt(t.getNo());
        out.writeUTF(t.getContent());
        out.writeUTF(t.getDeadline().toString());
        out.writeInt(t.getStatus());
        out.writeUTF(t.getOwner());
      }
      System.out.println("작업 데이터 저장!");

    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
}
