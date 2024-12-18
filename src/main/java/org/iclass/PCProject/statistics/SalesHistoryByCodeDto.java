package org.iclass.PCProject.statistics;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryByCodeDto {
  private int     idx;
  private String  vendor;
  private String  code;
  private String  regdate;
  private int     price;
  private int     count;
  private int     total;
  private int     stock;
}
