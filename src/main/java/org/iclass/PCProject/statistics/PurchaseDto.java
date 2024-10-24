package org.iclass.PCProject.statistics;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
  private String vendor;
  private String name;
  private String code;
  private int price;
  private int count;
}
