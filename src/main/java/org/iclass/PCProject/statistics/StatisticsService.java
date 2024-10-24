package org.iclass.PCProject.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {
  private final StatisticsRepository dao;

  public String getStatUser() {
    return "OK";
  }

  public SalesHistoryDto testSQL(int i) {
    List<Object[]> list = dao.testSQL(i);
    Object[] o = list.get(0);
    return new SalesHistoryDto(((BigDecimal)o[0]).intValue(),
                                (String)o[1],
                                (String)o[2],
                                ((BigDecimal)o[3]).intValue(),
                                ((BigDecimal)o[4]).intValue(),
                                (String)o[5],
                                (LocalDateTime)o[6],
                                ((BigDecimal)o[7]).intValue());
  }

  public List<ProdListDto> getProdList() {
    List<Object[]> list = dao.getProdList();
    return list.stream().map (o -> new ProdListDto((String)o[0], (String)o[1])).toList();
  }

  public List<ChartDto> getChartList() {
    List<Object[]> list = dao.getChartList();
    return list.stream().map (o -> new ChartDto(((Character)o[0]).toString(), (String)o[1], ((BigDecimal)o[2]).longValue())).toList();
  }

  // =====================================
  // Purchase Statistics
  // =====================================
  public int getSalesHistoryStatVendorCount(String sdate, String edate, String vendor, String code) {
    return dao.getSalesHistoryStatVendorCount(sdate, edate, vendor, code);
  }

  public List<SalesHistoryStatVendor> getSalesHistoryStatVendor(String sdate, String edate, String vendor, String code,
                                                                String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getSalesHistoryStatVendor(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map (o -> new SalesHistoryStatVendor(((BigDecimal)o[0]).intValue(),
                                                              (String)o[1],
                                                              (String)o[2],
                                                              //((BigDecimal)o[3]).intValue(),
                                                              (int)o[3],
                                                              ((BigDecimal)o[4]).intValue(),
                                                              ((BigDecimal)o[5]).intValue(),
                                                              ((BigDecimal)o[6]).intValue(),
                                                              ((BigDecimal)o[7]).intValue())).toList();
                                                              //(int)o[7])).toList();
  }

  public int getSalesHistoryStatCodeCount(String sdate, String edate, String vendor, String code) {
    return dao.getSalesHistoryStatCodeCount(sdate, edate, vendor, code);
  }

  public List<SalesHistoryStatCode> getSalesHistoryStatCode(String sdate, String edate, String vendor, String code,
                                                            String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getSalesHistoryStatCode(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map(o -> new SalesHistoryStatCode(((BigDecimal)o[0]).intValue(),
                                                           (String)o[1],
                                                           (String)o[2],
                                                           (String)o[3],
                                                           (int)o[4],
                                                           (int)o[5],
                                                           ((BigDecimal)o[6]).intValue(),
                                                           1)).toList();
  }

  // =====================================
  // Purchase Statistics
  // =====================================
  public int getPurchaseHistoryStatVendorCount(String sdate, String edate, String vendor, String code) {
    return dao.getPurchaseHistoryStatVendorCount(sdate, edate, vendor, code);
  }

  public List<PurchaseHistoryStatVendor> getPurchaseHistoryStatVendor(String sdate, String edate, String vendor, String code, String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getPurchaseHistoryStatVendor(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map (o -> new PurchaseHistoryStatVendor(((BigDecimal)o[0]).intValue(),
                                                                 (String)o[1],
                                                                 (String)o[2],
                                                                 ((BigDecimal)o[3]).intValue(),
                                                                 ((BigDecimal)o[4]).intValue(),
                                                                 ((BigDecimal)o[5]).longValue(),
                                                                 (int)o[6])).toList();
  }

  public int getPurchaseHistoryStatCodeCount(String sdate, String edate, String vendor, String code) {
    return dao.getPurchaseHistoryStatCodeCount(sdate, edate, vendor, code);
  }

  public List<PurchaseHistoryStatCode> getPurchaseHistoryStatCode(String sdate, String edate, String vendor, String code, String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getPurchaseHistoryStatCode(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map(o -> new PurchaseHistoryStatCode(((BigDecimal)o[0]).intValue(),
                                                              (String)o[1],
                                                              (String)o[2],
                                                              (String)o[3],
                                                              (int)o[4],
                                                              (int)o[5],
                                                              ((BigDecimal)o[6]).intValue(),
                                                              1)).toList();
  }
}
