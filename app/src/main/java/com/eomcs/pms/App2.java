package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App2 {
  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    Date day= new Date(0);
    final int SIZE = 100;

    int[] nos = new int[SIZE];
    String[] titles = new String[SIZE];
    String[] contents = new String[SIZE];
    Date[] startDates = new Date[SIZE];
    Date[] endDates = new Date[SIZE];
    String[] owners = new String[SIZE];
    String[] members = new String[SIZE];
    int count = 0;

    System.out.println("[프로젝트]");

    for (int i = 0; i < SIZE; i++) {
      System.out.print("번호? ");
      nos[i] = keyboard.nextInt();

      System.out.print("프로젝트명? ");
      keyboard.nextLine();
      titles[i] = keyboard.nextLine();

      System.out.print("내용? ");
      contents[i] = keyboard.nextLine();

      System.out.print("시작일? ");
      startDates[i] = Date.valueOf(keyboard.nextLine());

      System.out.print("종료일? ");
      endDates[i] = Date.valueOf(keyboard.nextLine());

      System.out.print("만든이? ");
      owners[i] = keyboard.nextLine();

      System.out.print("팀원? ");
      members[i] = keyboard.nextLine();
      count++;
      System.out.println();
      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String response = keyboard.nextLine();
      System.out.println();
      if (response.length() == 0 || response.equalsIgnoreCase("n") ) {
        break;
      }
    }
    keyboard.close();

    System.out.println("---------------------------------");

    for (int i = 0; i < count; i++) {
      System.out.printf("%d, %s, %s, %s, %s\n", nos[i], titles[i], startDates[i], endDates[i], owners[i]);
    }
  }
}
