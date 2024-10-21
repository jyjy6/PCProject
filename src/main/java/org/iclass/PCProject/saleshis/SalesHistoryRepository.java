package org.iclass.PCProject.saleshis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// =================================================================================
// !!! Caution
// Never Used. bcoz JPA can not perform "group by and order by" by passed parameter
// =================================================================================
@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Integer> {

  @Query(value = "SELECT H.* FROM SALES_HISTORY H WHERE H.SEQ = :seq", nativeQuery = true)
  public List<Object[]> testSQL(@Param("seq") int seq);

  @Query(value = "SELECT P.VENDOR, P.CODE FROM PRODUCT P", nativeQuery = true)
  public List<Object[]> getProdList();

  /*
   * MariaDB
  @Query(value = "" +
    "SELECT (@IDX := @IDX + 1) AS IDX, H.VENDOR, H.CODE, SUM(H.PRICE) PRICE, SUM(H.COUNT) COUNT, SUM(H.PRICE*H.COUNT) TOTAL " +
    "FROM (SELECT @IDX := 0) R, SALES_HISTORY H " +
    "WHERE REGDATE BETWEEN :sdate AND :edate " +
    "GROUP BY H.VENDOR, H.CODE ORDER BY IDX ", nativeQuery = true)
  public List<Object[]> getSalesHistoryStat(String sdate, String edate, String vendor, String code, String column, String dir);
   */


  /*
   * Oracle
   */
  @Query(value = "SELECT ROWNUM AS IDX, STAT.VENDOR, STAT.CODE, STAT.PRICE, STAT.COUNT, STAT.TOTAL "
    + "FROM (SELECT H.VENDOR, H.CODE, SUM(H.PRICE) PRICE, SUM(H.COUNT) COUNT, SUM(H.PRICE * H.COUNT) TOTAL "
    + "FROM SALES_HISTORY H "
    + "WHERE H.REGDATE BETWEEN TO_DATE(:sdate) AND TO_DATE(:edate) "
    + "AND H.VENDOR LIKE %:vendor% "
    + "GROUP BY H.VENDOR, H.CODE) STAT "
    , nativeQuery = true)
  public List<Object[]> getSalesHistoryStatVendor(String sdate, String edate, String vendor);

  @Query(value = "SELECT ROWNUM AS IDX, STAT.VENDOR, STAT.CODE, STAT.REGDATE, STAT.PRICE, STAT.COUNT, STAT.TOTAL "
    + "FROM (SELECT H.VENDOR, H.CODE, TO_CHAR(H.REGDATE, 'YYYY-MM-DD') REGDATE, H.PRICE PRICE, H.COUNT COUNT, H.PRICE * H.COUNT TOTAL "
    + "FROM SALES_HISTORY H "
    + "WHERE H.REGDATE BETWEEN TO_DATE(:sdate) AND TO_DATE(:edate) "
    + "AND H.VENDOR LIKE %:vendor% "
    + "AND H.CODE LIKE %:code%) STAT "
    , nativeQuery = true)
  public List<Object[]> getSalesHistoryStatCode(String sdate, String edate, String vendor, String code);
}