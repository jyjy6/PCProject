package org.iclass.PCProject.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnaRepository;

    public List<QnADTO> list() {
        List<QnA> list = qnaRepository.findAll();

        return list.stream().map(entity -> QnADTO.of(entity))
                .collect(Collectors.toList());
        }
}

