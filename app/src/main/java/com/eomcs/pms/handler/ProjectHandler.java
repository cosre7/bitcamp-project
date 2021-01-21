package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class ProjectHandler {

  static class Project {
    int no;
    String title;
    String content;
    Date startDate;
    Date endDate;
    String owner;
    String members;  
  }

  static final int LENGTH = 100;
  static Project[] projects = new Project[LENGTH];
  static int size = 0;

  public static void add() {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.no = Prompt.inputInt("번호? ");
    p.title = Prompt.inputString("프로젝트명? ");
    p.content = Prompt.inputString("내용? ");
    p.startDate = Prompt.inputDate("시작일? ");
    p.endDate = Prompt.inputDate("종료일? ");
    while (true) {
      String name = Prompt.inputString("만든이?(취소: 빈 문자열) ");
      // 사용자로부터 "만든이"를 입력받음
      if (name.length() == 0) {
        // 만든이에서 그냥 엔터치면 프로젝트 등록 취소
        System.out.println("프로젝트 등록을 취소합니다.");
        // void 상태에서 return; 이라고 하면 메소드 자체를 break; 한다는 것 -> 호출한 곳으로 돌아간다
        return;
      } 
      if (MemberHandler.exist(name)) {
        p.owner = name;
        break;
        // break; = 멈춤 return; 아예 나감
      }
      System.out.println("등록된 회원이 아닙니다.");
    }
    p.members = "";
    while (true) {
      String name = Prompt.inputString("팀원?(완료: 빈 문자열) ");
      // 사용자로부터 "만든이"를 입력받음
      if (name.length() == 0) {
        // 빈문자열 입력하면 종료
        break;
      } else if (MemberHandler.exist(name)) {
        // 이름이 멤버에 존재하면 p.members 에 이름 추가
        if (p.members.length() > 0) {
          // p.members.length() = !p.members.isEmpty() 모두 가능
          // p.members가 비어있지 않다면 , 붙여주기
          p.members += ",";
        }
        p.members += name;
      } else {
        // 이름이 멤버에 없으면 "등록된 회원이 아닙니다."
        System.out.println("등록된 회원이 아닙니다.");
      }

    }    
    projects[size++] = p;
  }

  public static void list() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < size; i++) {
      Project p = projects[i];
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n",
          p.no, p.title, p.startDate, p.endDate, p.owner, p.members);
    }
  }

}
