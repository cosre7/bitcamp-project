package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    Scanner keyboardScan = new Scanner(System.in);

    // 회원 등록, 목록 데이터
    final int MEMBER_LENGTH = 100;
    int[] no = new int[MEMBER_LENGTH];
    String[] name = new String[MEMBER_LENGTH];
    String[] email = new String[MEMBER_LENGTH];
    String[] password = new String[MEMBER_LENGTH];
    String[] photo = new String[MEMBER_LENGTH];
    String[] tel = new String[MEMBER_LENGTH];
    Date[] registeredDate = new Date[MEMBER_LENGTH];
    int memberSize = 0;

    // 프로젝트 등록, 목록 데이터
    final int PROJECT_LENGTH = 100;
    int[] no2 = new int[PROJECT_LENGTH];
    String[] title = new String[PROJECT_LENGTH];
    String[] content = new String[PROJECT_LENGTH];
    Date[] startDate = new Date[PROJECT_LENGTH];
    Date[] endDate = new Date[PROJECT_LENGTH];
    String[] owner = new String[PROJECT_LENGTH];
    String[] members = new String[PROJECT_LENGTH];
    int projectSize = 0;

    // 작업 등록, 목록 데이터
    final int TASK_LENGTH = 100;
    int[] no3 = new int[TASK_LENGTH];
    String[] content3 = new String[TASK_LENGTH];
    Date[] deadline = new Date[TASK_LENGTH];
    String[] owner3 = new String[TASK_LENGTH];
    int[] status = new int[TASK_LENGTH];
    int taskSize = 0;

    while (true) {
      System.out.print("명령> ");
      String command = keyboardScan.nextLine();

      if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
        break;
      }else if (command.equalsIgnoreCase("/member/list")) {
        System.out.println("[회원 목록]");
        for (int i = 0; i < memberSize; i++) {
          System.out.printf("%d, %s, %s, %s, %s\n", 
              no[i], name[i], email[i], tel[i], registeredDate[i]);
        }

      }else if (command.equalsIgnoreCase("/member/add")) {
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

      }else if (command.equalsIgnoreCase("/project/add")) {
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

      }else if (command.equalsIgnoreCase("/project/list")) {
        for (int i = 0; i < projectSize; i++) {
          // 번호, 프로젝트명, 시작일, 종료일, 만든이
          System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
              no[i], title[i], startDate[i], endDate[i], owner[i]);
        }
      }else if (command.equalsIgnoreCase("/task/add")) {
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

      }else if (command.equalsIgnoreCase("/task/list")) {
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
      }else {
        System.out.println("실행할 수 없는 명령입니다.");
      }
      System.out.println();
    }
    keyboardScan.close();
    System.out.println("안녕!");
  }
}

