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
}
