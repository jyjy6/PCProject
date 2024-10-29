package org.iclass.PCProject.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.statistics.SalesHistory;
import org.iclass.PCProject.statistics.SalesHistoryDto;
import org.iclass.PCProject.statistics.SalesHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminOrdersService {
    private final SalesHistoryRepository dao;
    private final SalesHistoryRepository salesHistoryRepository;


    public Page<SalesHistory> getOrders(Pageable pageable, Sort sort) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return salesHistoryRepository.findAll(pageable);
    }

    public List<SalesHistoryDto> getAllSalesHistoryList() {
        List<SalesHistory> list = dao.findAll();
        return list.stream().map(entity -> SalesHistoryDto.toDto(entity))
                .collect(Collectors.toList());
    }

    public SalesHistory findById(int seq) {
        Optional<SalesHistory> salesHistoryOpt = dao.findById(seq);
        return salesHistoryOpt.orElse(null);
    }

    public void createOrders(SalesHistoryDto salesHistoryDto) {
        salesHistoryRepository.save(salesHistoryDto.toEntity(salesHistoryDto));
    }

    @Transactional
    public void updateOrders(SalesHistory salesHistory) {

        if (salesHistory.getStslogis() == 3) {
            System.out.println("배송이 완료되었습니다: " + salesHistory);
        }
        dao.save(salesHistory);
    }

    // 날짜별 조회 메서드
    public List<SalesHistory> findPurchaseHistory(String username, LocalDateTime startDate, LocalDateTime endDate) {
        return dao.findByUsernameAndRegdateBetween(username, startDate, endDate);
    }

    public void savePayment(String username, String code, Integer price, Integer quantity, String vendor){
        SalesHistory dto = new SalesHistory();
        dto.setUsername(username);
        dto.setCode(code);
        dto.setRegdate(LocalDateTime.now());
        dto.setPrice(price);
        dto.setStslogis(0);
        dto.setCount(quantity);
        dto.setVendor(vendor);
        dao.save(dto);
    }

    public void deleteOrders(int id) {
        salesHistoryRepository.deleteById(id);
    }

}
