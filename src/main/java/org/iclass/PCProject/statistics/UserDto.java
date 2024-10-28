package org.iclass.PCProject.statistics;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private String  gender;
  private int     generation;
  private int     count;
}
