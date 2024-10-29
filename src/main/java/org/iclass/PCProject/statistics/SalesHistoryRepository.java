package org.iclass.PCProject.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// =================================================================================
// !!! Caution
// Do not use. bcoz JPA can not perform "group by and order by" by passed parameter
// =================================================================================
@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Integer> {
    List<SalesHistory> findByUsername(String username);
    List<SalesHistory> findByUsernameAndRegdateBetween(String username, LocalDateTime startDate, LocalDateTime endDate);

}