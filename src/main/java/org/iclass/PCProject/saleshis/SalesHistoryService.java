package org.iclass.PCProject.saleshis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalesHistoryService {
  private final SalesHistoryRepository dao;

  public String getStatUser() {
    return "OK";
  }

  public SalesHistoryDto testSQL(int i) {
    SalesHistory entity = dao.testSQL(i);
    return SalesHistoryDto.toDto(entity);
  }

  public List<SalesHistoryDto> getSalesHistory() {
    List<SalesHistory> rslt = dao.getSalesHistory();
    List<SalesHistoryDto> listDto = new ArrayList<>();
    rslt.forEach(e -> listDto.add(SalesHistoryDto.toDto(e)));
    return listDto;
  }

  public List<SalesHistoryStatDto> getSalesHistoryStat() {
    List<Object[]> list = dao.getSalesHistoryStat();
    return list.stream().map (o -> new SalesHistoryStatDto(((BigDecimal)o[0]).intValue(),
                                                           (String)o[1],
                                                           (String)o[2],
                                                           ((BigDecimal)o[3]).intValue(),
                                                           ((BigDecimal)o[4]).intValue(),
                                                           ((BigDecimal)o[5]).intValue())).toList();
  }
}
