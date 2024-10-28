package org.iclass.PCProject.statistics;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@Slf4j
@RequiredArgsConstructor
@Service
public class SalesHistoryService {
    private final SalesHistoryRepository dao;


    public String getStatUser() {
        return "OK";
    }


    public List<SalesHistoryDto> getAllSalesHistoryList() {
        List<SalesHistory> list = dao.findAll();
        return list.stream().map(entity -> SalesHistoryDto.toDto(entity))
                .collect(Collectors.toList());
    }

    public SalesHistory findById(int seq) {
        Optional<SalesHistory> salesHistoryOpt = dao.findById(seq);
        return salesHistoryOpt.orElse(null); // 존재하지 않으면 null 반환
    }

    public void update(SalesHistory salesHistory) {
        System.out.println("Updated SalesHistory: " + salesHistory);
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

}
