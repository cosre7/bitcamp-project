package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class Prompt {
  static Scanner keyboardScan = new Scanner(System.in);

  static String inputString(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }

  static int inputInt(String title) {
    return Integer.parseInt(inputString(title));
  }

  static Date inputDate(String title) {
    // 한번에 이름 다 바꾸고 싶을 때
    // inputDate 드래그 -> 마우스 오른쪽 클릭 -> refactor -> rename
    // 이름 바꾸고 엔터

    return Date.valueOf(inputString(title));
  }

  static void close() {
    // 정리하고 마무리할 것 있으면 해라~
    // scanner를 사용한건 app 이 아니라 prompt이다. 
    keyboardScan.close();
  }
}
