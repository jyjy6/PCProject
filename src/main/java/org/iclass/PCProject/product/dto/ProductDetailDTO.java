package org.iclass.PCProject.product.dto;

import lombok.*;
import org.iclass.PCProject.product.entity.ProductDetail;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {

    private int seq;
    private int pSeq;
    private String fileName;
    private LocalDateTime regDate;

    public static ProductDetailDTO toDto(ProductDetail entity) {
        return ProductDetailDTO.builder()
                .seq(entity.getSeq())
                .pSeq(entity.getPSeq())
                .fileName(entity.getFileName())
                .regDate(entity.getRegDate())
                .build();
    }

    public ProductDetail toEntity() {
        return ProductDetail.builder()
                .seq(this.seq)
                .pSeq(this.pSeq)
                .fileName(this.fileName)
                .regDate(this.regDate)
                .build();
    }
}
