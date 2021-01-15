package com.eomcs.pms;

import java.sql.Date;

public class App2 {
  public static void main(String[] args) {
    java.util.Scanner keyboard = new java.util.Scanner(System.in);
    java.sql.Date day= new java.sql.Date(0);

    System.out.println("[프로젝트]");

    System.out.print("번호? ");
    int no = keyboard.nextInt();

    System.out.print("프로젝트명? ");
    keyboard.nextLine();
    String title = keyboard.nextLine();

    System.out.print("내용? ");
    String content = keyboard.nextLine();

    System.out.print("시작일? ");
    Date startDate = Date.valueOf(keyboard.nextLine());

    System.out.print("종료일? ");
    Date endDate = Date.valueOf(keyboard.nextLine());

    System.out.print("만든이? ");
    String owner = keyboard.nextLine();

    System.out.print("팀원? ");
    String members = keyboard.nextLine();

    keyboard.close();

    System.out.println("---------------------------------");

    System.out.printf("번호: %d\n", no);
    System.out.printf("프로젝트명: %s\n", title);
    System.out.printf("내용: %s\n", content);
    System.out.printf("시작일: %s\n", startDate);
    System.out.printf("종료일: %s\n", endDate);
    System.out.printf("만든이: %s\n", owner);
    System.out.printf("팀원: %s\n", members);


  }
}
