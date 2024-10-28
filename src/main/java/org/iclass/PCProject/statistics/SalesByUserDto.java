package org.iclass.PCProject.statistics;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SalesByUserDto {
  private String  gender;
  private int     generation;
  private String  vendor;
  private String  code;
  private int     price;
  private int     amount;
  private int     total;
}
