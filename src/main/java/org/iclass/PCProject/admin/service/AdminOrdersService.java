package org.iclass.PCProject.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.admin.repository.AdminSaleshistoryRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminOrdersService {
    private final SalesHistoryRepository dao;
    private final AdminSaleshistoryRepository adminSaleshistoryRepository;


    public Page<SalesHistory> getOrders(Pageable pageable, Sort sort) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return dao.findAll(pageable);
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
        dao.save(salesHistoryDto.toEntity(salesHistoryDto));
    }

    @Transactional
    public void updateOrders(SalesHistory salesHistory) {

        if (salesHistory.getStslogis() == 3) {
            System.out.println("배송이 완료되었습니다: " + salesHistory);
        }
        dao.save(salesHistory);
    }

    private Integer convertStatus(String status) {
        switch (status) {
            case "결제완료":
                return 0;
            case "배송준비":
                return 1;
            case "배송중":
                return 2;
            case "배송완료":
                return 3;
            default:
                return null;
        }
    }

    public Page<SalesHistory> searchOrders(String searchField, String searchValue, Pageable pageable) {
        return adminSaleshistoryRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if ("username".equals(searchField) && searchValue != null && !searchValue.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + searchValue + "%"));
            } else if ("code".equals(searchField) && searchValue != null && !searchValue.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("code"), "%" + searchValue + "%"));
            } else if ("stslogis".equals(searchField) && searchValue != null && !searchValue.isEmpty()) {
                Integer status = convertStatus(searchValue); // 한글을 숫자로 변환
                if (status != null) {
                    predicates.add(criteriaBuilder.equal(root.get("stslogis"), status)); // 정수로 비교
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));}, pageable);
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
        dao.deleteById(id);
    }

}
