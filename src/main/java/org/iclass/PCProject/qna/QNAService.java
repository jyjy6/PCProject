package org.iclass.PCProject.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class QNAService {
    private final QNARepository qnaRepository;

    public void save(QNA qna) {
        qnaRepository.save(qna);
    }

    public Page<QNA> getPageByQuestioner(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        return qnaRepository.findAllByQuestioner(username, pageable);
    }

    // Answer가 null인 항목만 가져오는 메서드
    public Page<QNA> getPageByQuestionerAndAnswerIsNull(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        return qnaRepository.findAllByQuestionerAndAnswerIsNull(username, pageable);
    }

    // Answer가 null이 아닌 항목만 가져오는 메서드
    public Page<QNA> getPageByQuestionerAndAnswerIsNotNull(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        return qnaRepository.findAllByQuestionerAndAnswerIsNotNull(username, pageable);
    }


}
