package org.iclass.PCProject.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {
  private final StatisticsRepository dao;
  private final SalesHistoryRepository historyDao;

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



  // =====================================
  // Data for Chart
  // =====================================
  public List<ChartDto> getChartPurchase() {
    List<Object[]> list = dao.getChartPurchase();
    return list.stream().map (o -> new ChartDto(((Character)o[0]).toString(), (String)o[1], ((BigDecimal)o[2]).longValue())).toList();
  }

  public List<ChartDto> getChartSales() {
    List<Object[]> list = dao.getChartSales();
    return list.stream().map (o -> new ChartDto(((Character)o[0]).toString(), (String)o[1], ((BigDecimal)o[2]).longValue())).toList();
  }

  public List<ChartDto> getChartUsers() {
    List<UserDto> list = getUsers();
    return list.stream().map (ChartDto::cvtDto).toList();
  }



  // =====================================
  // Purchase Statistics
  // =====================================
  public int getPurchaseHistoryCountByVendor(String sdate, String edate, String vendor, String code) {
    return dao.getPurchaseHistoryCountByVendor(sdate, edate, vendor, code);
  }

  public List<PurchaseHistoryByVendorDto> getPurchaseHistoryByVendor(String sdate, String edate, String vendor, String code, String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getPurchaseHistoryByVendor(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map (o -> new PurchaseHistoryByVendorDto(((BigDecimal)o[0]).intValue(),
      (String)o[1],
      (String)o[2],
      ((BigDecimal)o[3]).intValue(),
      ((BigDecimal)o[4]).intValue(),
      ((BigDecimal)o[5]).longValue(),
      (int)o[6])).toList();
  }

  public int getPurchaseHistoryCountByCode(String sdate, String edate, String vendor, String code) {
    return dao.getPurchaseHistoryCountByCode(sdate, edate, vendor, code);
  }

  public List<PurchaseHistoryByCodeDto> getPurchaseHistoryByCode(String sdate, String edate, String vendor, String code, String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getPurchaseHistoryByCode(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map(o -> new PurchaseHistoryByCodeDto(((BigDecimal)o[0]).intValue(),
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
  public int getSalesHistoryCountByVendor(String sdate, String edate, String vendor, String code) {
    return dao.getSalesHistoryCountByVendor(sdate, edate, vendor, code);
  }

  public List<SalesHistoryByVendorDto> getSalesHistoryByVendor(String sdate, String edate, String vendor, String code,
                                                               String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getSalesHistoryByVendor(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map (o -> new SalesHistoryByVendorDto(((BigDecimal)o[0]).intValue(),
                                                               (String)o[1],
                                                               (String)o[2],
                                                               (int)o[3],
                                                               ((BigDecimal)o[4]).intValue(),
                                                               ((BigDecimal)o[5]).intValue(),
                                                               ((BigDecimal)o[6]).intValue(),
                                                               ((BigDecimal)o[7]).intValue())).toList();
  }

  public int getSalesHistoryCountByCode(String sdate, String edate, String vendor, String code) {
    return dao.getSalesHistoryCountByCode(sdate, edate, vendor, code);
  }

  public List<SalesHistoryByCodeDto> getSalesHistoryByCode(String sdate, String edate, String vendor, String code,
                                                           String column, String dir, int sno, int eno) {
    List<Object[]> list = dao.getSalesHistoryByCode(sdate, edate, vendor, code, column, dir, sno, eno);
    return list.stream().map(o -> new SalesHistoryByCodeDto(((BigDecimal)o[0]).intValue(),
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
  public List<UserDto> getUsers() {
    List<Object[]> list = dao.getUsers();
    return list.stream().map(o -> new UserDto((String)o[0], ((BigDecimal)o[1]).intValue(), ((BigDecimal)o[2]).intValue())).toList();
  }

  public List<SalesByUserDto> getSalesByUser(String sdate, String edate) {
    List<Object[]> list = dao.getSalesByUser(sdate, edate);
    return list.stream().map(o -> new SalesByUserDto((String)o[0],
                                                     ((BigDecimal)o[1]).intValue(),
                                                     (String)o[2],
                                                     (String)o[3],
                                                     ((BigDecimal)o[4]).intValue(),
                                                     ((BigDecimal)o[5]).intValue(),
                                                     ((BigDecimal)o[6]).intValue())).toList();
  }

  public List<SalesHistory> findPurchaseHistory(String username, LocalDateTime startDate, LocalDateTime endDate) {
    return historyDao.findByUsernameAndRegdateBetween(username, startDate, endDate);
  }

}
