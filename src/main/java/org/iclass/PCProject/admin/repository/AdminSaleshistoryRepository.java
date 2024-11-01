package org.iclass.PCProject.admin.repository;

import org.iclass.PCProject.statistics.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminSaleshistoryRepository extends JpaRepository<SalesHistory, Long>, JpaSpecificationExecutor<SalesHistory> {

}

