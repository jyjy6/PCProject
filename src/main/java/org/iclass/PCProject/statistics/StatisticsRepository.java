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


  public List<Object[]> getChartList() {
    String sql = "SELECT * "
      + "FROM "
      + "  (SELECT 'V' TYPE, VENDOR NAME, SUM(PRICE*COUNT) WON "
      + "   FROM SALES_HISTORY "
      + "   GROUP BY VENDOR "
      + "   ORDER BY WON DESC) "
      + "WHERE ROWNUM < 6 "
      + "UNION ALL "
      + "SELECT S.TYPE, P.NAME||' '||S.CODE NAME, S.WON "
      + "FROM PRODUCT P, "
      + "  (SELECT 'P' TYPE, CODE, SUM(PRICE*COUNT) WON "
      + "   FROM SALES_HISTORY "
      + "   GROUP BY CODE "
      + "   ORDER BY WON DESC) S "
      + "WHERE ROWNUM < 11 "
      + "AND P.CODE = S.CODE ";
    log.info ("[getChartList] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }



  // =====================================
  // Purchase Statistics
  // =====================================
  public int getPurchaseHistoryStatVendorCount (String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) AS CNT "
      + "FROM "
      + "  (SELECT VENDOR, CODE "
      + "   FROM   PURCHASE_HISTORY "
      + "   WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
      + "   AND    VENDOR LIKE '%" + vendor + "%' "
      + "   GROUP BY VENDOR, CODE) S ";
    log.info ("[getPurchaseHistoryStatVendorCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getPurchaseHistoryStatVendor(String sdate, String edate, String vendor, String code,
                                                     String column, String dir, int sno, int eno) {
    String sql = "SELECT I.*, P.STOCK "
      + "FROM PRODUCT P, "
      + "  (SELECT ROWNUM AS IDX, S.VENDOR, S.CODE, S.PRICE, S.COUNT, S.TOTAL "
      + "   FROM "
      + "     (SELECT VENDOR, CODE, TRUNC(SUM(PRICE)/COUNT(CODE)) PRICE, SUM(COUNT) COUNT, SUM(PRICE * COUNT) TOTAL "
      + "      FROM   PURCHASE_HISTORY "
      + "      WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
      + "      AND    VENDOR LIKE '%" + vendor + "%' "
      + "      GROUP BY VENDOR, CODE "
      + "      ORDER BY " + column + " " + dir + ") S) I "
      + "WHERE IDX BETWEEN " + sno + " AND " + eno + " "
      + "AND   P.VENDOR = I.VENDOR "
      + "AND   P.CODE   = I.CODE ";
    log.info ("[getPurchaseHistoryStatVendor] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public int getPurchaseHistoryStatCodeCount(String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) CNT "
      + "FROM   PURCHASE_HISTORY "
      + "WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
      + "AND    VENDOR LIKE '%" + vendor + "%' "
      + "AND    CODE LIKE '%" + code + "%' ";
    log.info ("[getPurchaseHistoryStatCodeCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getPurchaseHistoryStatCode(String sdate, String edate, String vendor, String code,
                                                   String column, String dir, int sno, int eno) {
    String sql = "SELECT * "
      + "FROM "
      + "  (SELECT ROWNUM AS IDX, S.VENDOR, S.CODE, S.REGDATE, S.PRICE, S.COUNT, S.TOTAL "
      + "   FROM "
      + "     (SELECT VENDOR, CODE, TO_CHAR(REGDATE,'YYYY-MM-DD') REGDATE, PRICE, COUNT, PRICE*COUNT TOTAL "
      + "      FROM   PURCHASE_HISTORY "
      + "      WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
      + "      AND    VENDOR LIKE '%" + vendor + "%' "
      + "      AND    CODE LIKE '%" + code + "%' "
      + "      ORDER BY " + column + " " + dir + ") S) "
      + "WHERE "
      + " IDX BETWEEN " + sno + " AND " + eno;
    log.info ("[getPurchaseHistoryStatCode] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }



  // =====================================
  // Purchase Statistics
  // =====================================
  public int getSalesHistoryStatVendorCount (String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) AS CNT "
               + "FROM "
               + "  (SELECT VENDOR, CODE "
               + "   FROM   SALES_HISTORY "
               + "   WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
               + "   AND    VENDOR LIKE '%" + vendor + "%' "
               + "   GROUP BY VENDOR, CODE) S ";
    log.info ("[getSalesHistoryStatVendorCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getSalesHistoryStatVendor(String sdate, String edate, String vendor, String code,
                                                  String column, String dir, int sno, int eno) {
    /*
    String sql = "SELECT I.*, P.STOCK "
               + "FROM PRODUCT P, "
               + "  (SELECT ROWNUM AS IDX, S.VENDOR, S.CODE, S.PRICE, S.COUNT, S.TOTAL "
               + "   FROM "
               + "     (SELECT VENDOR, CODE, TRUNC(SUM(PRICE)/COUNT(CODE)) PRICE, SUM(COUNT) COUNT, SUM(PRICE * COUNT) TOTAL "
               + "      FROM   SALES_HISTORY "
               + "      WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
               + "      AND    VENDOR LIKE '%" + vendor + "%' "
               + "      GROUP BY VENDOR, CODE "
               + "      ORDER BY " + column + " " + dir + ") S) I "
               + "WHERE IDX BETWEEN " + sno + " AND " + eno + " "
               + "AND   P.VENDOR = I.VENDOR "
               + "AND   P.CODE   = I.CODE ";
     */
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

    log.info ("[getSalesHistoryStatVendor] SQL: \n{}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }

  public int getSalesHistoryStatCodeCount(String sdate, String edate, String vendor, String code) {
    String sql = "SELECT COUNT(*) CNT "
               + "FROM   SALES_HISTORY "
               + "WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
               + "AND    VENDOR LIKE '%" + vendor + "%' "
               + "AND    CODE LIKE '%" + code + "%' ";
    log.info ("[getSalesHistoryStatCodeCount] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return ((Number)query.getSingleResult()).intValue();
  }

  public List<Object[]> getSalesHistoryStatCode(String sdate, String edate, String vendor, String code,
                                                String column, String dir, int sno, int eno) {
    String sql = "SELECT * "
               + "FROM "
               + "  (SELECT ROWNUM AS IDX, S.VENDOR, S.CODE, S.REGDATE, S.PRICE, S.COUNT, S.TOTAL "
               + "   FROM "
               + "     (SELECT VENDOR, CODE, TO_CHAR(REGDATE,'YYYY-MM-DD') REGDATE, PRICE, COUNT, PRICE*COUNT TOTAL "
               + "      FROM   SALES_HISTORY "
               + "      WHERE  REGDATE BETWEEN TO_DATE('" + sdate + "') AND TO_DATE('" + edate + "') "
               + "      AND    VENDOR LIKE '%" + vendor + "%' "
               + "      AND    CODE LIKE '%" + code + "%' "
               + "      ORDER BY " + column + " " + dir + ") S) "
               + "WHERE "
               + " IDX BETWEEN " + sno + " AND " + eno;
    log.info ("[getSalesHistoryStatCode] SQL: {}", sql);
    Query query = em.createNativeQuery(sql);
    return query.getResultList();
  }
}
