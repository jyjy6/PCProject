package org.iclass.PCProject.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "productdetail_seq_generator")
@Table(name="PRODUCT_DETAIL")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "productdetail_seq_generator")
    private int seq;

    @Column(nullable = false, name="P_SEQ")
    private int pSeq;

    @Column(nullable = false, name="FILE_NAME")
    private String fileName;

    @CreatedDate
    @LastModifiedDate
    @Column(nullable = false, name="REG_DATE")
    private LocalDate regDate;

}
