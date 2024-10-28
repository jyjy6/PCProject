package org.iclass.PCProject.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// =================================================================================
// !!! Caution
// Do not use. bcoz JPA can not perform "group by and order by" by passed parameter
// =================================================================================
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {

}