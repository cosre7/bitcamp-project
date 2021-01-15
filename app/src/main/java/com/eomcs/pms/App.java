package com.eomcs.pms;
import java.util.Scanner;
public class App {
  public static void main(String[] args) {
    System.out.println("[회원]");

    Scanner keyboardScan = new Scanner(System.in);

    final int SIZE = 100;
    String[] nos = new String[SIZE];
    String[] names = new String[SIZE];
    String[] emails = new String[SIZE];
    String[] pws = new String[SIZE];
    String[] photos = new String[SIZE];
    String[] tels = new String[SIZE];
    int count = 0;
    java.sql.Date[] now = new java.sql.Date[SIZE];

    for (int i = 0; i < SIZE; i++) {
      System.out.print("번호? ");
      nos[i] = keyboardScan.nextLine();

      System.out.print("이름? ");
      names[i] = keyboardScan.nextLine();

      System.out.print("이메일? ");
      emails[i] = keyboardScan.nextLine();

      System.out.print("암호? ");
      pws[i] = keyboardScan.nextLine();

      System.out.print("사진? ");
      photos[i] = keyboardScan.nextLine();

      System.out.print("전화? ");
      tels[i] = keyboardScan.nextLine();

      now[i] = new java.sql.Date(System.currentTimeMillis());
      count++;
      System.out.println();
      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String response = keyboardScan.nextLine();
      if (response.length() == 0 || response.equalsIgnoreCase("n")) {
        //  response.equalsIgnoreCase("n") 대문자 소문자 상관없이 n일 때
        break;
      }
      System.out.println();
    }
    keyboardScan.close();

    System.out.println("-----------------------------");

    for (int i = 0; i < count; i++) {
      System.out.printf("%s, %s, %s, %s, %s\n", nos[i], names[i], emails[i], tels[i], now[i]);
    }
  }
}