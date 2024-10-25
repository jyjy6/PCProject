package org.iclass.PCProject.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "product_seq_generator")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_seq_generator")
    private int seq;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String vendor;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int discount;

    @Column(nullable = false)
    private String thumb;

    @CreatedDate
    @Column(nullable = false, name="REG_DATE")
    private LocalDateTime regDate;

    @Column(nullable = false)
    private int stock;

    private char status;

}
