package org.iclass.PCProject.qna;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private Long qseq;
    @Column(nullable = false)
    private String vendor;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private LocalDateTime reg_Date;
}
