package org.iclass.PCProject.saleshis;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class SalesHistoryPersisRepo {
  @Autowired
  @PersistenceContext
  private EntityManager em;

  public List<Object[]> testSQL(int seq) {
    String sql = "SELECT * FROM SALES_HISTORY WHERE SEQ = " + seq;
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public List<Object[]> getProdList() {
    String sql = "SELECT VENDOR, CODE FROM PRODUCT";
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public List<Object[]> getSalesHistoryStatVendor(String sdate, String edate, String vendor) {
    String sql = "SELECT "
               + "  ROWNUM AS IDX, STAT.VENDOR, STAT.CODE, STAT.PRICE, STAT.COUNT, STAT.TOTAL "
               + "FROM "
               + "  (SELECT VENDOR, CODE, SUM(PRICE) PRICE, SUM(COUNT) COUNT, SUM(PRICE * COUNT) TOTAL "
               + "   FROM   SALES_HISTORY "
               + "   WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
               + "   AND    VENDOR LIKE '%" + vendor + "%' "
               + "   GROUP BY VENDOR, CODE) STAT ";
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public List<Object[]> getSalesHistoryStatCode(String sdate, String edate, String vendor, String code) {
    String sql = "SELECT "
               + "  ROWNUM AS IDX, STAT.VENDOR, STAT.CODE, STAT.REGDATE, STAT.PRICE, STAT.COUNT, STAT.TOTAL "
               + "FROM "
               + "  (SELECT VENDOR, CODE, TO_CHAR(REGDATE,'YYYY-MM-DD') REGDATE, PRICE, COUNT, PRICE*COUNT TOTAL "
               + "   FROM   SALES_HISTORY "
               + "   WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
               + "   AND    VENDOR LIKE '%" + vendor + "%' "
               + "   AND    CODE LIKE '%" + code + "%') STAT ";
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

}
