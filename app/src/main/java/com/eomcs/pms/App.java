package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  static Scanner keyboardScan = new Scanner(System.in);

  // 회원 등록, 목록 데이터
  static final int MEMBER_LENGTH = 100;
  static int[] no = new int[MEMBER_LENGTH];
  static String[] name = new String[MEMBER_LENGTH];
  static String[] email = new String[MEMBER_LENGTH];
  static String[] password = new String[MEMBER_LENGTH];
  static String[] photo = new String[MEMBER_LENGTH];
  static String[] tel = new String[MEMBER_LENGTH];
  static Date[] registeredDate = new Date[MEMBER_LENGTH];
  static int memberSize = 0;

  static void addMember() {
    System.out.println("[회원 등록]");

    System.out.print("번호? ");
    no[memberSize] = Integer.parseInt(keyboardScan.nextLine());

    System.out.print("이름? ");
    name[memberSize] = keyboardScan.nextLine();

    System.out.print("이메일? ");
    email[memberSize] = keyboardScan.nextLine();

    System.out.print("암호? ");
    password[memberSize] = keyboardScan.nextLine();

    System.out.print("사진? ");
    photo[memberSize] = keyboardScan.nextLine();

    System.out.print("전화? ");
    tel[memberSize] = keyboardScan.nextLine();

    registeredDate[memberSize] = new java.sql.Date(System.currentTimeMillis());

    memberSize++;
  }

  static void listMember() {
    System.out.println("[회원 목록]");
    for (int i = 0; i < memberSize; i++) {
      System.out.printf("%d, %s, %s, %s, %s\n", 
          no[i], name[i], email[i], tel[i], registeredDate[i]);
    }
  }

  //프로젝트 등록, 목록 데이터
  static final int PROJECT_LENGTH = 100;
  static int[] no2 = new int[PROJECT_LENGTH];
  static String[] title = new String[PROJECT_LENGTH];
  static String[] content = new String[PROJECT_LENGTH];
  static Date[] startDate = new Date[PROJECT_LENGTH];
  static Date[] endDate = new Date[PROJECT_LENGTH];
  static String[] owner = new String[PROJECT_LENGTH];
  static String[] members = new String[PROJECT_LENGTH];
  static int projectSize = 0;

  static void addProject() {
    System.out.println("[프로젝트 등록]");

    System.out.print("번호? ");
    no[projectSize] = Integer.valueOf(keyboardScan.nextLine());

    System.out.print("프로젝트명? ");
    title[projectSize] = keyboardScan.nextLine();

    System.out.print("내용? ");
    content[projectSize] = keyboardScan.nextLine();

    System.out.print("시작일? ");
    startDate[projectSize] = Date.valueOf(keyboardScan.nextLine());

    System.out.print("종료일? ");
    endDate[projectSize] = Date.valueOf(keyboardScan.nextLine());

    System.out.print("만든이? ");
    owner[projectSize] = keyboardScan.nextLine();

    System.out.print("팀원? ");
    members[projectSize] = keyboardScan.nextLine();

    projectSize++;
  }

  static void listProject() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < projectSize; i++) {
      // 번호, 프로젝트명, 시작일, 종료일, 만든이
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[i], title[i], startDate[i], endDate[i], owner[i]);
    }
  }

  // 작업 등록, 목록 데이터
  static final int TASK_LENGTH = 100;
  static int[] no3 = new int[TASK_LENGTH];
  static String[] content3 = new String[TASK_LENGTH];
  static Date[] deadline = new Date[TASK_LENGTH];
  static String[] owner3 = new String[TASK_LENGTH];
  static int[] status = new int[TASK_LENGTH];
  static int taskSize = 0;

  static void addTask() {
    System.out.println("[작업 등록]");

    System.out.print("번호? ");
    no[taskSize] = Integer.parseInt(keyboardScan.nextLine());

    System.out.print("내용? ");
    content[taskSize] = keyboardScan.nextLine();

    System.out.print("마감일? ");
    deadline[taskSize] = Date.valueOf(keyboardScan.nextLine());

    System.out.println("상태?");
    System.out.println("0: 신규");
    System.out.println("1: 진행중");
    System.out.println("2: 완료");
    System.out.print("> ");
    status[taskSize] = Integer.valueOf(keyboardScan.nextLine());

    System.out.print("담당자? ");
    owner[taskSize] = keyboardScan.nextLine();

    taskSize++;

  }

  static void listTask() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < taskSize; i++) {
      String stateLabel = null;
      switch (status[i]) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      System.out.printf("%d, %s, %s, %s, %s\n", 
          no[i], content[i], deadline[i], stateLabel, owner[i]);
    }
  }
  public static void main(String[] args) {

    while (true) {
      System.out.print("명령> ");
      String command = keyboardScan.nextLine();

      if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
        break;
      }else if (command.equalsIgnoreCase("/member/add")) {
        addMember();

      }else if (command.equalsIgnoreCase("/member/list")) {
        listMember();

      }else if (command.equalsIgnoreCase("/project/add")) {
        addProject();

      }else if (command.equalsIgnoreCase("/project/list")) {
        listProject();

      }else if (command.equalsIgnoreCase("/task/add")) {
        addTask();

      }else if (command.equalsIgnoreCase("/task/list")) {
        listTask();

      }else {
        System.out.println("실행할 수 없는 명령입니다.");
      }
      System.out.println();
    }
    keyboardScan.close();
    System.out.println("안녕!");
  }
}

