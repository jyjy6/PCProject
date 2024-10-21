package org.iclass.PCProject.saleshis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesHistoryService {
  //private final SalesHistoryRepository dao;
  private final SalesHistoryPersisRepo dao;

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

  public List<SalesHistoryStatVendor> getSalesHistoryStatVendor(String sdate, String edate, String vendor, String code, String column, String dir) {
    List<Object[]> list = dao.getSalesHistoryStatVendor(sdate, edate, vendor);
    return list.stream().map (o -> new SalesHistoryStatVendor(((BigDecimal)o[0]).intValue(),
      (String)o[1],
      (String)o[2],
      ((BigDecimal)o[3]).intValue(),
      ((BigDecimal)o[4]).intValue(),
      ((BigDecimal)o[5]).intValue())).toList();
    /*
    // MariaDB
    return list.stream().map (o -> new SalesHistoryStatDto(((Double)o[0]).intValue(),
                                                            (String)o[1],
                                                            (String)o[2],
                                                           ((BigDecimal)o[3]).intValue(),
                                                           ((BigDecimal)o[4]).intValue(),
                                                           ((BigDecimal)o[5]).intValue())).toList();
     */
  }


  public List<SalesHistoryStatCode> getSalesHistoryStatCode(String sdate, String edate, String vendor, String code, String column, String dir) {
    List<Object[]> list = dao.getSalesHistoryStatCode(sdate, edate, vendor, code);
    /*
    for (Object[] o : list) {
      int temp1 = ((BigDecimal)o[0]).intValue();
      String temp2 = (String)o[1];
      String temp3 = (String)o[2];
      int temp4 = (int)o[3];
      int temp5 = (int)o[4];
      int temp6 = ((BigDecimal)o[5]).intValue();
      log.info ("temp1: {}, temp2: {}, temp3: {}, temp4: {}, temp5: {}, temp6: {}", temp1, temp2, temp3, temp4, temp5);
    }
     */
    return list.stream().map(o -> new SalesHistoryStatCode(((BigDecimal)o[0]).intValue(),
                                                           (String)o[1],
                                                           (String)o[2],
                                                           (String)o[3],
                                                           (int)o[4],
                                                           (int)o[5],
                                                           ((BigDecimal)o[6]).intValue())).toList();
  }
  /*
  public List<SalesHistoryStatVendor> getSalesHistoryStatVendor2(String sdate, String edate, String vendor, String code, String column, String dir) {
    List<Object[]> list = dao2.getSalesHistoryStatVendor(sdate, edate, vendor);
    return list.stream().map (o -> new SalesHistoryStatVendor(((BigDecimal)o[0]).intValue(),
      (String)o[1],
      (String)o[2],
      ((BigDecimal)o[3]).intValue(),
      ((BigDecimal)o[4]).intValue(),
      ((BigDecimal)o[5]).intValue())).toList();
  }
  */
}
