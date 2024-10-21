package org.iclass.PCProject.saleshis;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryStatVendor {
  int     idx;
  String  vendor;
  String  code;
  int     price;
  int     count;
  int     total;
}
