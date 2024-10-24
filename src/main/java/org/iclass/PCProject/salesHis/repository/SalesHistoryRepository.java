package org.iclass.PCProject.salesHis.repository;

import org.iclass.PCProject.salesHis.entity.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {

    /*List<SalesHistoryEntity> findAll();*/
    List<SalesHistory> findAll();
}
