package com.eomcs.pms;

public class App {
  public static void main(String[] args) {
    java.util.Scanner keyboard = new java.util.Scanner(System.in);
    java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

    final int SIZE = 100;
    String[] nos = new String[SIZE];
    String[] names = new String[SIZE];
    String[] emails = new String[SIZE];
    String[] pws = new String[SIZE];
    String[] photos = new String[SIZE];
    String[] phones = new String[SIZE];
    int count = 0;

    System.out.println("[회원]");

    for (int i = 0; i < SIZE; i++) {
      System.out.print("번호? ");
      nos[i] = keyboard.nextLine();

      System.out.print("이름? ");
      names[i] = keyboard.nextLine();

      System.out.print("이메일? ");
      emails[i] = keyboard.nextLine();

      System.out.print("암호? ");
      pws[i] = keyboard.nextLine();

      System.out.print("사진? ");
      photos[i] = keyboard.nextLine();

      System.out.print("전화? ");
      phones[i] = keyboard.nextLine();

      count++;
      System.out.println();
      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String choice = keyboard.nextLine();
      if (!choice.equalsIgnoreCase("y")) {
        break;
      }
      System.out.println();
    }
    keyboard.close();

    System.out.println("-----------------------------");

    for (int i = 0; i < count; i++) {
      System.out.printf("%s, %s, %s, %s, %s\n", nos[i], names[i], emails[i], phones[i], today);
    }
  }
}