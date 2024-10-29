package org.iclass.PCProject.product.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.iclass.PCProject.product.entity.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private int seq;
    private String username;
    private String code;
    private String content;
    private int score;
    private int pSeq;
    private LocalDateTime regDate;

    public static ReviewDTO toDTO(Review entity) {
        return ReviewDTO.builder()
                .seq(entity.getSeq())
                .username(entity.getUsername())
                .code(entity.getCode())
                .content(entity.getContent())
                .score(entity.getScore())
                .pSeq(entity.getPSeq())
                .regDate(entity.getRegDate())
                .build();
    }

    public Review toEntity() {
        return Review.builder()
                .username(this.username)
                .code(this.code)
                .content(this.content)
                .score(this.score)
                .pSeq(this.pSeq)
                .regDate(this.regDate)
                .build();
    }
}
