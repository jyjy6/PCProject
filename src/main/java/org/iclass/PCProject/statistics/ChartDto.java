package org.iclass.PCProject.statistics;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartDto {
  String type;
  String name;
  long   take;

  public static ChartDto cvtDto (UserDto user) {
    return ChartDto.builder()
                   .type(user.getGender())
                   .name(user.getGeneration()+"")
                   .take(user.getCount())
                   .build();
  }
}
