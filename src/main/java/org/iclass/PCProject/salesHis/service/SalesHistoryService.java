package org.iclass.PCProject.salesHis.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.salesHis.dto.SalesHistoryDTO;
import org.iclass.PCProject.salesHis.entity.SalesHistory;
import org.iclass.PCProject.salesHis.repository.SalesHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesHistoryService {

    private final SalesHistoryRepository salesHistoryRepository;

    public List<SalesHistoryDTO> getAllSalesHistoryList() {
        List<SalesHistory> list = salesHistoryRepository.findAll();
        return list.stream().map(entity -> SalesHistoryDTO.toDTO(entity))
                .collect(Collectors.toList());
    }

    public SalesHistory findById(Long seq) {
        Optional<SalesHistory> salesHistoryOpt = salesHistoryRepository.findById(seq);
        return salesHistoryOpt.orElse(null); // 존재하지 않으면 null 반환
    }

    public void update(SalesHistory salesHistory) {
        salesHistoryRepository.save(salesHistory);
    }
}

