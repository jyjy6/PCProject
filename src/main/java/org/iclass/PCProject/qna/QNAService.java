package org.iclass.PCProject.qna;

import jakarta.persistence.EntityNotFoundException;
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
    public void modify(QNA qna) {
        QNA existingQNA = qnaRepository.findById(qna.getSeq())
                .orElseThrow(() -> new EntityNotFoundException("QNA not found"));

        // 필요한 필드만 업데이트
        existingQNA.setAnswer(qna.getAnswer()); // 답변 필드만 업데이트
        existingQNA.setAnswerUser(qna.getAnswerUser()); // 필요 시 다른 필드도 업데이트
        qnaRepository.save(existingQNA);
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


    // Answer가 null인 항목만 가져오는 메서드
    public Page<QNA> adminGetPageFindByAnswerIsNull(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        return qnaRepository.findByAnswerIsNull(pageable);
    }
    // Answer가 null이 아닌 항목만 가져오는 메서드
    public Page<QNA> adminGetPageFindByAnswerIsNotNull(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        return qnaRepository.findByAnswerIsNotNull(pageable);
    }

    public Page<QNA> adminGetPageFindAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        return qnaRepository.findAll(pageable);
    }


}
