package com.eomcs.pms.domain;

import java.sql.Date;
// 한 단위의 데이터
// 하나의 게시물
public class Board {
  public int no;
  public String title;
  public String content;
  public String writer;
  public Date registeredDate;
  public int viewCount;
  public int like;
}
