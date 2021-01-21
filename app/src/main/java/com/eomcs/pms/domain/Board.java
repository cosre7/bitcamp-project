package com.eomcs.pms.domain;
// domain 또는 vo 또는 dto 라고 되어있을 것
// 설계도들을 보관한다고 생각.

import java.sql.Date;

public class Board {
  public int no;
  public String title;
  public String content;
  public String writer;
  public Date registereDate;
  public int viewCount;
}
