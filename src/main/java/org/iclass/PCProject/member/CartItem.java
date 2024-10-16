package org.iclass.PCProject.member;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
//   더미데이터
    private String title;
    private int price;
    private int quantity;
    private String size;
    private String color;
    private String img;

}
