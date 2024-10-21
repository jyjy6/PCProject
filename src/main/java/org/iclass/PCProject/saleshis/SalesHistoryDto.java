package org.iclass.PCProject.saleshis;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryDto {
    private int             seq;
    private String          vendor;
    private String          code;
    private int             price;
    private int             count;
    private String          username;
    private LocalDateTime   regdate;
    private int             stslogis;

    public static SalesHistoryDto toDto (SalesHistory entity)
    {
        return SalesHistoryDto
          .builder()
          .seq(entity.getSeq())
          .vendor(entity.getVendor())
          .code(entity.getCode())
          .price(entity.getPrice())
          .count(entity.getCount())
          .username(entity.getUsername())
          .regdate(entity.getRegdate())
          .stslogis(entity.getStslogis())
          .build();
    }

    public SalesHistory toEntity (SalesHistoryDto dto)
    {
        return SalesHistory
          .builder()
          .seq(dto.getSeq())
          .vendor(dto.getVendor())
          .code(dto.getCode())
          .price(dto.getPrice())
          .count(dto.getCount())
          .username(dto.getUsername())
          .regdate(dto.getRegdate())
          .stslogis(dto.getStslogis())
          .build();
    }
}