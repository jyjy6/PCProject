package org.iclass.PCProject.saleshis;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryStatDto {
  Integer idx;
  String vendor;
  String code;
  Integer price;
  Integer count;
  Integer total;
}
