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
    private long score;
    private int pSeq;
    private String imgPath;
    private String imgPath2;
    private String imgPath3;
    private LocalDateTime regDate;

    public static ReviewDTO toDTO(Review entity) {
        return ReviewDTO.builder()
                .seq(entity.getSeq())
                .username(entity.getUsername())
                .code(entity.getCode())
                .content(entity.getContent())
                .score(entity.getScore())
                .pSeq(entity.getPSeq())
                .imgPath(entity.getImgPath())
                .imgPath2(entity.getImgPath2())
                .imgPath3(entity.getImgPath3())
                .regDate(entity.getRegDate())
                .build();
    }

    public Review toEntity() {
        return Review.builder()
                .seq(this.seq)
                .username(this.username)
                .code(this.code)
                .content(this.content)
                .score(this.score)
                .pSeq(this.pSeq)
                .imgPath(this.imgPath)
                .imgPath2(this.imgPath2)
                .imgPath3(this.imgPath3)
                .regDate(this.regDate)
                .build();
    }
}
