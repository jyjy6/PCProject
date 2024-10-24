package org.iclass.PCProject.qna;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class QNA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String vendor;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String questioner;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    @Column(updatable = false, name="REG_DATE")
    private LocalDateTime regDate;
    private String answer;
    private String answerUser;




}
