package org.iclass.PCProject.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "review_seq_generator")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false, name="P_SEQ")
    private int pSeq;
    @Column(nullable = false)
    private int score;
    @Column(nullable = false, name="REG_DATE")
    @CreatedDate
    private LocalDateTime regDate;
}
