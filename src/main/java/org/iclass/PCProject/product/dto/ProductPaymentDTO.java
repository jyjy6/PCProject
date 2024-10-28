package org.iclass.PCProject.product.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.ProductPayment;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ProductPaymentDTO {

    private int seq;
    private int pSeq;
    private String username;
    private String vendor;
    private String productname;
    private String code;
    private int quantity;
    private int price;
    private String thumb;
    private int status;
    private LocalDateTime regDate;

    public static ProductPaymentDTO toDto(ProductPayment productPayment) {
        return ProductPaymentDTO.builder()
                .seq(productPayment.getSeq())
                .pSeq(productPayment.getPSeq())
                .username(productPayment.getUsername())
                .vendor(productPayment.getVendor())
                .productname(productPayment.getProductname())
                .code(productPayment.getCode())
                .quantity(productPayment.getQuantity())
                .price(productPayment.getPrice())
                .thumb(productPayment.getThumb())
                .status(productPayment.getStatus())
                .regDate(productPayment.getRegDate())
                .build();
    }

    public ProductPayment toEntity() {
        return ProductPayment.builder()
                .seq(this.seq)
                .pSeq(this.pSeq)
                .username(this.username)
                .vendor(this.vendor)
                .productname(this.productname)
                .code(this.code)
                .quantity(this.quantity)
                .price(this.price)
                .thumb(this.thumb)
                .status(this.status)
                .regDate(this.regDate)
                .build();
    }
}
