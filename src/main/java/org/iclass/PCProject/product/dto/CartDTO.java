package org.iclass.PCProject.product.dto;

import lombok.*;
import org.iclass.PCProject.product.entity.Cart;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private int seq;
    private String username;
    private int pSeq;
    private String vendor;
    private String name;
    private String code;
    private int price;
    private int quantity;
    private LocalDateTime regDate;

    public static CartDTO toDTO(Cart cart) {
        return CartDTO.builder()
                .username(cart.getUsername())
                .pSeq(cart.getPSeq())
                .vendor(cart.getVendor())
                .name(cart.getName())
                .code(cart.getCode())
                .price(cart.getPrice())
                .quantity(cart.getQuantity())
                .build();
    }

    public Cart toEntity() {
        return Cart.builder()
                .seq(this.seq)
                .username(this.username)
                .pSeq(this.pSeq)
                .vendor(this.vendor)
                .name(this.name)
                .code(this.code)
                .price(this.price)
                .quantity(this.quantity)
                .regDate(this.regDate)
                .build();
    }
}
