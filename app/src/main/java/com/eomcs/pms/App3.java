package com.eomcs.pms;

import java.sql.Date;

public class App3 {
  public static void main(String[] args) {
    java.util.Scanner keyboard = new java.util.Scanner(System.in);
    java.sql.Date date = new java.sql.Date(0);

    System.out.println("[작업]");

    System.out.print("프로젝트? ");
    String title = keyboard.nextLine();

    System.out.print("번호? ");
    int no = keyboard.nextInt();
    keyboard.nextLine();

    System.out.print("내용? ");
    String content = keyboard.nextLine();

    System.out.print("완료일? ");
    Date endDate = Date.valueOf(keyboard.nextLine());

    System.out.println("상태?");
    System.out.printf("0: 신규\n1: 진행중\n2: 완료\n>");
    int constant = keyboard.nextInt();
    keyboard.nextLine();

    System.out.print("담당자? ");
    String owner = keyboard.nextLine();

    keyboard.close();

    System.out.println("--------------------------------");

    System.out.printf("프로젝트: %s\n", title);
    System.out.printf("번호: %d\n", no);
    System.out.printf("내용: %s\n", content);
    System.out.printf("완료일: %s\n", endDate);
    if(constant == 0) {
      System.out.println("상태: 신규");
    }else if(constant == 1) {
      System.out.println("상태: 진행중");
    }else if(constant == 2) {
      System.out.println("상태: 완료");
    }
    System.out.printf("담당자: %s\n", owner);
  }
}