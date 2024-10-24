package org.iclass.PCProject.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "cart_seq_generator")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cart_seq_generator")
    private int seq;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true, name="P_SEQ")
    private int pSeq;

    @Column(nullable = false)
    private String vendor;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, name="REG_DATE")
    @CreatedDate
    @LastModifiedDate
    private LocalDate regDate;
}
