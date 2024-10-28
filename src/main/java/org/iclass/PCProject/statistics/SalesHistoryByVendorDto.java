package org.iclass.PCProject.statistics;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryByVendorDto {
  private int     idx;
  private String  vendor;
  private String  code;
  private int     stock;
  private int     price;
  private int     income;
  private int     count;
  private int     total;
}
