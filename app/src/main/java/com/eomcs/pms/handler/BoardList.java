package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Board;

public class BoardList {
  //공동으로 사용하는 값은 스태틱 필드로 선언한다.
  static final int DEFAULT_CAPACITY = 100;

  // 개별적으로 관리해야 하는 값은 인스턴스 필드로 선언한다.
  Board[] boards = new Board[DEFAULT_CAPACITY];   
  int size = 0;

  void add(Board b) {
    this.boards[this.size++] = b;
  }

  Board[] toArray() {
    // 현재까지 저장된 게시글 목록을 리턴하기 위해 새 배열을 준비한다.
    Board[] arr = new Board[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = boards[i];
    }
    return arr;
  }

  Board get(int boardNo) {
    // -> findByNo 대체 가능
    // 해당 번호의 게시글을 찾는다.
    int index = indexOf(boardNo);
    if (index != -1) {
      return boards[index];
    }
    return null;
  }

  void delete(int boardNo) {
    // 해당 번호의 게시글을 찾는다.
    int index = indexOf(boardNo);

    // 못찾았으면 delete 메소드 나가기
    if (index == -1)
      return;

    // 찾은 인덱스를 이용해서 당기기
    // 배열에서 뒷 번호의 게시글을 한 칸씩 앞으로 당긴다. -> 삭제 
    for (int x = index + 1; x < this.size; x++) {
      this.boards[x-1] = this.boards[x];
    }
    boards[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다.
  }

  // 추후에 쓸지도 몰라서 일단 두는 메소드

  // 게시글 번호에 해당하는 인스턴스를 배열에서 찾아 그 인덱스를 리턴한다. 
  int indexOf(int boardNo) {
    for (int i = 0; i < this.size; i++) {
      Board board = this.boards[i];
      if (board.no == boardNo) {
        return i;
      }
    }
    return -1;
  }
}
