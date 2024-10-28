package org.iclass.PCProject.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@Table(name="PRODUCT_PAYMENT")
public class ProductPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false, name = "P_SEQ")
    private int pSeq;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String vendor;

    @Column(nullable = false)
    private String productname;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String thumb;

    @Column(nullable = false)
    private int status;

    @CreatedDate
    @Column(nullable = false, name="REG_DATE")
    private LocalDateTime regDate;
}
