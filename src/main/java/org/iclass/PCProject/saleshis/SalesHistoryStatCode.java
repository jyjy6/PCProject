package org.iclass.PCProject.saleshis;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryStatCode {
  private int     idx;
  private String  vendor;
  private String  code;
  private String  regdate;
  private int     price;
  private int     count;
  private int     total;
}
