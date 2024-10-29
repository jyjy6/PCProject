package org.iclass.PCProject.statistics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class StatisticsRepository {
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


  // =====================================
  // Data for Chart
  // =====================================
  public List<Object[]> getChartPurchase() {
    String sql = "SELECT * \n"
               + "FROM \n"
               + "  (SELECT 'V' TYPE, VENDOR NAME, SUM(PRICE*COUNT) WON \n"
               + "   FROM PURCHASE_HISTORY \n"
               + "   GROUP BY VENDOR \n"
               + "   ORDER BY WON DESC) \n"
               + "WHERE ROWNUM < 6 \n"
               + "UNION ALL \n"
               + "SELECT S.TYPE, P.NAME||' '||S.CODE NAME, S.WON \n"
               + "FROM PRODUCT P, \n"
               + "  (SELECT 'P' TYPE, CODE, SUM(PRICE*COUNT) WON \n"
               + "   FROM PURCHASE_HISTORY \n"
               + "   GROUP BY CODE \n"
               + "   ORDER BY WON DESC) S \n"
               + "WHERE ROWNUM < 11 \n"
               + "AND P.CODE = S.CODE \n";
    log.info ("\n[getChartList] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public List<Object[]> getChartSales() {
    String sql = "SELECT * \n"
               + "FROM \n"
               + "  (SELECT 'V' TYPE, VENDOR NAME, SUM(PRICE*COUNT) WON \n"
               + "   FROM SALES_HISTORY \n"
               + "   GROUP BY VENDOR \n"
               + "   ORDER BY WON DESC) \n"
               + "WHERE ROWNUM < 6 \n"
               + "UNION ALL \n"
               + "SELECT S.TYPE, P.NAME||' '||S.CODE NAME, S.WON \n"
               + "FROM PRODUCT P, \n"
               + "  (SELECT 'P' TYPE, CODE, SUM(PRICE*COUNT) WON \n"
               + "   FROM SALES_HISTORY \n"
               + "   GROUP BY CODE \n"
               + "   ORDER BY WON DESC) S \n"
               + "WHERE ROWNUM < 11 \n"
               + "AND P.CODE = S.CODE \n";
    log.info ("\n[getChartList] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public List<Object[]> getChartUser() {
    return null;
  }


  // =====================================
  // Purchase Statistics
  // =====================================
  public int getPurchaseHistoryCountByVendor(String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) AS CNT \n"
               + "FROM \n"
               + "  (SELECT VENDOR, CODE \n"
               + "   FROM   PURCHASE_HISTORY \n"
               + "   WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "   AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "   GROUP BY VENDOR, CODE) S \n";
    log.info ("\n[getPurchaseHistoryStatVendorCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getPurchaseHistoryByVendor(String sdate, String edate, String vendor, String code,
                                                   String column, String dir, int sno, int eno) {
    String sql = "SELECT I.*, P.STOCK \n"
               + "FROM PRODUCT P, \n"
               + "  (SELECT ROWNUM AS IDX, S.VENDOR, S.CODE, S.PRICE, S.COUNT, S.TOTAL \n"
               + "   FROM \n"
               + "     (SELECT VENDOR, CODE, TRUNC(SUM(PRICE)/COUNT(CODE)) PRICE, SUM(COUNT) COUNT, SUM(PRICE * COUNT) TOTAL \n"
               + "      FROM   PURCHASE_HISTORY \n"
               + "      WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "      AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "      GROUP BY VENDOR, CODE \n"
               + "      ORDER BY " + column + " " + dir + ") S) I \n"
               + "WHERE IDX BETWEEN " + sno + " AND " + eno + " \n"
               + "AND   P.VENDOR = I.VENDOR \n"
               + "AND   P.CODE   = I.CODE \n";
    log.info ("\n[getPurchaseHistoryStatVendor] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public int getPurchaseHistoryCountByCode(String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) CNT \n"
               + "FROM   PURCHASE_HISTORY \n"
               + "WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "AND    CODE LIKE '%" + code + "%' \n";
    log.info ("\n[getPurchaseHistoryStatCodeCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getPurchaseHistoryByCode(String sdate, String edate, String vendor, String code,
                                                 String column, String dir, int sno, int eno) {
    String sql = "SELECT * \n"
               + "FROM \n"
               + "  (SELECT ROWNUM AS IDX, S.VENDOR, S.CODE, S.REGDATE, S.PRICE, S.COUNT, S.TOTAL \n"
               + "   FROM \n"
               + "     (SELECT VENDOR, CODE, TO_CHAR(REGDATE,'YYYY-MM-DD') REGDATE, PRICE, COUNT, PRICE*COUNT TOTAL \n"
               + "      FROM   PURCHASE_HISTORY \n"
               + "      WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "      AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "      AND    CODE LIKE '%" + code + "%' \n"
               + "      ORDER BY " + column + " " + dir + ") S) \n"
               + "WHERE \n"
               + " IDX BETWEEN " + sno + " AND " + eno + " \n";
    log.info ("\n[getPurchaseHistoryStatCode] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }



  // =====================================
  // Sales Statistics
  // =====================================
  public int getSalesHistoryCountByVendor(String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) AS CNT \n"
               + "FROM \n"
               + "  (SELECT VENDOR, CODE \n"
               + "   FROM   SALES_HISTORY \n"
               + "   WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "   AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "   GROUP BY VENDOR, CODE) S \n";
    log.info ("\n[getSalesHistoryStatVendorCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getSalesHistoryByVendor(String sdate, String edate, String vendor, String code,
                                                String column, String dir, int sno, int eno) {
    String sql = "SELECT * \n"
               + "FROM \n"
               + "  (SELECT ROWNUM AS IDX, T.* \n"
               + "   FROM \n"
               + "     (SELECT O.VENDOR, O.CODE, P.STOCK, O.PRICE, (O.PRICE-I.PRICE) INCOME, O.COUNT, O.TOTAL \n"
               + "      FROM PRODUCT P, \n"
               + "        (SELECT VENDOR, CODE, TRUNC(SUM(PRICE)/COUNT(CODE)) PRICE, SUM(COUNT) COUNT, SUM(PRICE * COUNT) TOTAL \n"
               + "         FROM   SALES_HISTORY \n"
               + "         WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "         AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "         GROUP BY VENDOR, CODE) O, \n"
               + "        (SELECT VENDOR, CODE, TRUNC(SUM(PRICE)/COUNT(CODE)) PRICE \n"
               + "         FROM   PURCHASE_HISTORY \n"
               + "         WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "         AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "         GROUP BY VENDOR, CODE) I \n"
               + "      WHERE O.VENDOR = I.VENDOR \n"
               + "      AND   O.CODE   = I.CODE \n"
               + "      AND   O.VENDOR = P.VENDOR \n"
               + "      AND   O.CODE = P.CODE \n"
               + "      ORDER BY " + column + " " + dir + ") T) \n"
               + " WHERE IDX BETWEEN " + sno + " AND " + eno + " \n";

    log.info ("\n[getSalesHistoryStatVendor] SQL: \n{}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public int getSalesHistoryCountByCode(String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) CNT \n"
               + "FROM   SALES_HISTORY \n"
               + "WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "AND    CODE LIKE '%" + code + "%' \n";
    log.info ("\n[getSalesHistoryStatCodeCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getSalesHistoryByCode(String sdate, String edate, String vendor, String code,
                                              String column, String dir, int sno, int eno) {
    String sql = "SELECT * \n"
               + "FROM \n"
               + "  (SELECT ROWNUM AS IDX, S.VENDOR, S.CODE, S.REGDATE, S.PRICE, S.COUNT, S.TOTAL \n"
               + "   FROM \n"
               + "     (SELECT VENDOR, CODE, TO_CHAR(REGDATE,'YYYY-MM-DD') REGDATE, PRICE, COUNT, PRICE*COUNT TOTAL \n"
               + "      FROM   SALES_HISTORY \n"
               + "      WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "      AND    VENDOR LIKE '%" + vendor + "%' \n"
               + "      AND    CODE LIKE '%" + code + "%' \n"
               + "      ORDER BY " + column + " " + dir + ") S) \n"
               + "WHERE \n"
               + " IDX BETWEEN " + sno + " AND " + eno + " \n";
    log.info ("\n[getSalesHistoryStatCode] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }



  // =====================================
  // User Statistics
  // =====================================
  public List<Object[]> getUsers() {
    String sql = "SELECT GENDER, TRUNC(AGE/10)*10 GENERATION, COUNT(*) COUNT \n"
               + "FROM MEMBER \n"
               + "WHERE 1 = 1 \n"
               + "GROUP BY GENDER, TRUNC(AGE/10)*10 \n"
               + "ORDER BY 1, 2 \n";
    log.info ("\n[getUser] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public List<Object[]> getSalesByUser(String sdate, String edate) {
    String sql = "SELECT M.GENDER, TRUNC(M.AGE/10)*10 GENERATION, S.VENDOR, S.CODE, \n"
               + "       TRUNC(AVG(S.PRICE)) PRICE, SUM(COUNT) AMOUNT, SUM(S.PRICE*S.COUNT) TOTAL \n"
               + "FROM   MEMBER M, \n"
               + "       SALES_HISTORY S \n"
               + "WHERE  M.USERNAME = S.USERNAME \n"
               + "AND    REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') \n"
               + "GROUP BY M.GENDER, TRUNC(M.AGE/10)*10, S.VENDOR, S.CODE \n"
               + "ORDER BY GENDER, GENERATION, TOTAL DESC \n";
    log.info ("\n[getSalesByUser] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }
}
