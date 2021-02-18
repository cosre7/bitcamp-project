package com.eomcs.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.eomcs.pms.domain.Member;

public class MemberListHandler extends AbstractMemberHandler {

  public MemberListHandler(List<Member> memberList) {
    super(memberList); 
  }

  @Override
  public void service() {
    System.out.println("[회원 목록]");

    Iterator<Member> iterator = memberList.iterator();
    while (iterator.hasNext()) {
      Member m = iterator.next();
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          m.getNo(), m.getName(), m.getEmail(), m.getTel(), m.getRegisteredDate());
    }
  }
}






