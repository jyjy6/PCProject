package org.iclass.PCProject.statistics;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryStatVendor {
  private int     idx;
  private String  vendor;
  private String  code;
  private int     price;
  private int     count;
  private long    total;
  private int     stock;
}
