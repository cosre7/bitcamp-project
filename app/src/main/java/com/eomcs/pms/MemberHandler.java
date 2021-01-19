package com.eomcs.pms;

import java.sql.Date;

public class MemberHandler {
  static final int LENGTH = 100;
  static int[] no = new int[LENGTH];
  static String[] name = new String[LENGTH];
  static String[] email = new String[LENGTH];
  static String[] password = new String[LENGTH];
  static String[] photo = new String[LENGTH];
  static String[] tel = new String[LENGTH];
  static Date[] registeredDate = new Date[LENGTH];  
  static int size = 0;

  static void add() {
    // 굳이 addMember라고 할 필요 없다.
    // 클래스 명이 MemberHandler 이기 때문에 이미 member와 관련되었다는 것을
    // 인지할 수 있기 때문에 add 만 해도 알아볼 수 있다.

    System.out.println("[회원 등록]");

    no[size] = Prompt.inputInt("번호? ");
    // App.no = App 클래스에 있는 no 변수라는 뜻.
    // ctrl+f 후 App. 을 찾아서 replace all -> 한번에 지워짐
    name[size] = Prompt.inputString("이름? ");
    email[size] = Prompt.inputString("이메일? ");
    password[size] = Prompt.inputString("암호? ");
    photo[size] = Prompt.inputString("사진? ");
    tel[size] = Prompt.inputString("전화? ");
    registeredDate[size] = new java.sql.Date(System.currentTimeMillis());
    size++;
  }

  static void list() {
    System.out.println("[회원 목록]");

    for (int i = 0; i < size; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[i], name[i], email[i], tel[i], registeredDate[i]);
    }
  }

}
