package com.eomcs.pms;

// 1) 회원 정보를 다루는 메서드를 따로 분류한다.
//    - MemberHandler 클래스를 생성한다.
//    - addMember(), listMember() 메서드를 옮긴다.
// 2) 프로젝트 정보를 다루는 메서드를 따로 분류한다.
//    - ProjectHandler 클래스를 생성한다.
//    - addProject(), listProject() 메서드를 옮긴다.
// 3) 작업 정보를 다루는 메서드를 따로 분류한다.
//    - TaskHandler 클래스를 생성한다.
//    - addTask(), listTask() 메서드를 옮긴다.
// 4) 프롬프트를 다루는 메서드를 따로 분류한다.
//    - Prompt 클래스를 생성한다.
//    - promptString(), promptInt(), promptDate() 메서드를 옮긴다.
// 5) 배열 변수는 그 변수를 사용하는 클래스로 옮긴다. 
//    - keyboardScan 변수를 Prompt 클래스로 옮긴다.
//    - 멤버 데이터 관련 변수를 MemberHandler 클래스로 옮긴다.
//    - 프로젝트 데이터 관련 변수를 ProjectHandler 클래스로 옮긴다.
//    - 작업 데이터 관련 변수를 TaskHandler 클래스로 옮긴다.
public class App {

  public static void main(String[] args) {

    loop:
      while (true) {
        String command = Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            MemberHandler.add();
            //MemberHandler 클래스에 있는 addMember()
            break;
          case "/member/list":
            MemberHandler.list();
            break;
          case "/project/add":
            ProjectHandler.add();
            break;
          case "/project/list":
            ProjectHandler.list();
            break;
          case "/task/add":
            TaskHandler.add();
            break;
          case "/task/list":
            TaskHandler.list();
            break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }
    Prompt.close();
    // app 이 아니라 prompt에 close 할 것이 있음
    // 근데 그걸 app 이 직접 닫을 수는 없다 -> 남의 짐 내가 정리해주는 꼴
    // 그래서 prompt에게 close를 하라고 이야기 해줘야 한다.
    // 이것이 바로 Prompt.close();
    // Handler들과 App 모두 Prompt의 메소드를 사용했지만 가장 마지막 장소는 바로 App.
    // 그래서 App 에서만 close를 해주는 것이고 이를 Prompt.close(); 형태로 실행.

  }
}
