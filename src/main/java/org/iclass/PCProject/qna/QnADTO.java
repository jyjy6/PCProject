package org.iclass.PCProject.qna;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnADTO {

    private Long seq;
    private Long qseq;
    private String vendor;
    private String code;
    private String id;
    private String contents;
    private LocalDateTime reg_Date;

    public static QnADTO of(QnA entity) {
        return QnADTO.builder()
                .seq(entity.getSeq())
                .qseq(entity.getQseq())
                .vendor(entity.getVendor())
                .code(entity.getCode())
                .id(entity.getId())
                .reg_Date(entity.getReg_Date())
                .build();
    }

    public QnA qnaEntity() {
        return QnA.builder()
                .seq(this.seq)
                .qseq(this.qseq)
                .vendor(this.vendor)
                .code(this.code)
                .id(this.id)
                .reg_Date(this.reg_Date)
                .build();

    }
}
