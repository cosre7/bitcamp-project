package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Member;

public class MemberList {

  static final int DEFAULT_CAPACITY = 100;

  Member[] members = new Member[DEFAULT_CAPACITY];  // 레퍼런스 배열 준비  
  int size = 0;

  void add(Member m) {
    this.members[this.size++] = m;
  }

  Member[] toArray() {
    Member[] arr = new Member[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = members[i];
    }
    return arr;
  }

  Member get(int memberNo) {
    int i = indexOf(memberNo);
    if (i == -1)
      return null;
    return members[i];
  }

  void delete(int memberNo) {
    int index = indexOf(memberNo);

    if (index == -1) 
      return;

    for (int x = index + 1; x < this.size; x++) {
      this.members[x-1] = this.members[x];
    }
    members[--this.size] = null;
  }

  int indexOf(int memberNo) {
    for (int i = 0; i < this.size; i++) {
      Member member = this.members[i];
      if (member.no == memberNo) {
        return i;
      }
    }
    return -1;
  }
}
