package org.iclass.PCProject.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class QNAService {
    private final QNARepository qnaRepository;

    public void save(QNA qna) {
        qnaRepository.save(qna);
    }
}
