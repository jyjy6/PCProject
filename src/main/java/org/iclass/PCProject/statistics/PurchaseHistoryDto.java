package org.iclass.PCProject.statistics;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Slf4j
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryDto {
  private Integer seq;
  private String vendor;
  private String code;
  private Integer price;
  private Integer count;
  private String username;
  private LocalDateTime regdate;

  public static PurchaseHistoryDto toDto (PurchaseHistory entity)
  {
    return PurchaseHistoryDto
      .builder()
      .seq(entity.getSeq())
      .vendor(entity.getVendor())
      .code(entity.getCode())
      .price(entity.getPrice())
      .count(entity.getCount())
      .username(entity.getUsername())
      .regdate(entity.getRegdate())
      .build();
  }

  public PurchaseHistory toEntity (PurchaseHistoryDto dto)
  {
    return PurchaseHistory
      .builder()
      .seq(dto.getSeq())
      .vendor(dto.getVendor())
      .code(dto.getCode())
      .price(dto.getPrice())
      .count(dto.getCount())
      .username(dto.getUsername())
      .regdate(dto.getRegdate())
      .build();
  }
}
