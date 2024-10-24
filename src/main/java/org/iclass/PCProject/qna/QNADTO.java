package org.iclass.PCProject.qna;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QNADTO {
    private Long seq; // 질문의 고유 ID
    private String answerUser; // 답변 사용자
    private String answer; // 답변 내용
}
