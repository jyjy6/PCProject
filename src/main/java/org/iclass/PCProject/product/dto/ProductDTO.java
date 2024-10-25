package org.iclass.PCProject.product.dto;

import lombok.*;
import org.iclass.PCProject.product.entity.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private int seq;
    private String code;
    private String vendor;
    private String name;
    private int price;
    private int discount;
    private String thumb;
    private LocalDateTime regDate;
    private int stock;
    private char status;

//    entity to dto
    public static ProductDTO toDto(Product entity) {
        return ProductDTO.builder()
                .seq(entity.getSeq())
                .code(entity.getCode())
                .vendor(entity.getVendor())
                .name(entity.getName())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .thumb(entity.getThumb())
                .regDate(entity.getRegDate())
                .stock(entity.getStock())
                .status(entity.getStatus())
                .build();
    }

//    dto to entity
    public Product toEntity() {
        return Product.builder()
                .seq(this.seq)
                .code(this.code)
                .vendor(this.vendor)
                .name(this.name)
                .price(this.price)
                .discount(this.discount)
                .thumb(this.thumb)
                .regDate(this.regDate)
                .stock(this.stock)
                .status(this.status)
                .build();
    }
}
