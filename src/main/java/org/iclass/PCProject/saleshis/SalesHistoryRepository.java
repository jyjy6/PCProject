package org.iclass.PCProject.saleshis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Integer> {

  // 일반 SQL쿼리
  @Query(value = "SELECT H.* FROM SALES_HISTORY H WHERE H.SEQ = :seq", nativeQuery = true)
  public SalesHistory testSQL(@Param("seq") int seq);

  // JPA Method
  @Query(value = "SELECT H.* FROM SALES_HISTORY H", nativeQuery = true)
  public List<SalesHistory> getSalesHistory();

  // JPA Method
  @Query(value = "" +
    "SELECT ROWNUM IDX, STAT.VENDOR, STAT.CODE, STAT.PRICE, STAT.COUNT, STAT.TOTAL " +
    "FROM (SELECT H.VENDOR, H.CODE, SUM(H.PRICE) PRICE, SUM(H.COUNT) COUNT, SUM(H.PRICE*H.COUNT) TOTAL " +
    "FROM SALES_HISTORY H " +
    "GROUP BY H.VENDOR, H.CODE) STAT ", nativeQuery = true)
  public List<Object[]> getSalesHistoryStat();
}