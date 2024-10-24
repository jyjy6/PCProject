package org.iclass.PCProject.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QNAService {

    private final QNARepository qnaRepository;

    public List<QNADTO> list() {
        List<QNA> list = qnaRepository.findAll();

        return list.stream().map(entity -> QNADTO.of(entity))
                .collect(Collectors.toList());
        }
}

