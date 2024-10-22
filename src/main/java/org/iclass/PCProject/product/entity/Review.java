package org.iclass.PCProject.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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
@SequenceGenerator(name = "review_seq_generator")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "review_seq_generator")
    private int seq;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private int pSeq;
    @Column(nullable = false)
    private long score;
    @Column(nullable = false)
    @CreatedDate
    private LocalDate regDate;
}
