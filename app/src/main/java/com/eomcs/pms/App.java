package com.eomcs.pms;

public class App {
  public static void main(String[] args) {
    java.util.Scanner keyboard = new java.util.Scanner(System.in);
    java.util.Date today = new java.util.Date();

    System.out.println("[회원]");
    System.out.print("번호? ");
    String no = keyboard.nextLine();

    System.out.print("이름? ");
    String name = keyboard.nextLine();

    System.out.print("이메일? ");
    String email = keyboard.nextLine();

    System.out.print("암호? ");
    String pw = keyboard.nextLine();

    System.out.print("사진? ");
    String photo = keyboard.nextLine();

    System.out.print("전화? ");
    String phone = keyboard.nextLine();

    keyboard.close();

    System.out.println("-----------------------------");

    System.out.printf("번호: %s\n", no);
    System.out.printf("이름: %s\n", name);
    System.out.printf("이메일: %s\n", email);
    System.out.printf("암호: %s\n", pw);
    System.out.printf("사진: %s\n", photo);
    System.out.printf("전화: %s\n", phone);
    System.out.printf("가입일: %1$tY-%1$tm-%1$td\n", today);
  }
}