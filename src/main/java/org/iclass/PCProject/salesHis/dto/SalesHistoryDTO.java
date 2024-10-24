package org.iclass.PCProject.salesHis.dto;

import lombok.*;
import org.iclass.PCProject.salesHis.entity.SalesHistory;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryDTO {

    private Long seq;
    private String vendor;
    private String code;
    private String username;
    private LocalDateTime regDate;
    private int price;
    private int count;
    private String stslogis;

    public static SalesHistoryDTO toDTO(SalesHistory entity) {
        return SalesHistoryDTO.builder()
                .seq(entity.getSeq())
                .vendor(entity.getVendor())
                .code(entity.getCode())
                .username(entity.getUsername())
                .regDate(entity.getRegDate())
                .price(entity.getPrice())
                .count(entity.getCount())
                .stslogis(entity.getStslogis())
                .build();
    }


    public SalesHistory salesHisEntity() {
        return SalesHistory.builder()
                .seq(this.seq)
                .vendor(this.vendor)
                .code(this.code)
                .username(this.username)
                .regDate(this.regDate)
                .price(this.price)
                .count(this.count)
                .stslogis(this.stslogis)
                .build();

    }
}
