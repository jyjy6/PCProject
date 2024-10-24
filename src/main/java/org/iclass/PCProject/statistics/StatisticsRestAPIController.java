package org.iclass.PCProject.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stat")
public class StatisticsRestAPIController {
  private final StatisticsService svc;

  @GetMapping("/")
  public ResponseEntity<?> summary () {
    return ResponseEntity.ok().body("Summary OK");
  }
  @GetMapping("/test")
  public ResponseEntity<?> statTest () {
    SalesHistoryDto dto = svc.testSQL(100);
    return ResponseEntity.ok(List.of(dto));
  }

  @GetMapping("/chart")
  public ResponseEntity<?> statChart() {
    List<ChartDto> list = svc.getChartList();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/prodlist")
  public ResponseEntity<?> statProdList() {
    List<ProdListDto> list = svc.getProdList ();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/purchase")
  public ResponseEntity<?> statPurchase(String sdate, String edate, String vendor, String code, String column, String dir, int sno, int eno) {
    log.info("[PurchaseHistoryRestAPIController] sdate: {}, edate: {}, vendor: {}, code: {}, column: {}, dir: {}, sno: {}, eno: {}", sdate, edate, vendor, code, column, dir, sno, eno);

    if ("%".equals (code)) {
      int total = svc.getPurchaseHistoryStatVendorCount(sdate, edate, vendor, code);
      List<PurchaseHistoryStatVendor> list = svc.getPurchaseHistoryStatVendor(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    } else {
      int total = svc.getPurchaseHistoryStatCodeCount(sdate, edate, vendor, code);
      List<PurchaseHistoryStatCode> list = svc.getPurchaseHistoryStatCode(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    }
  }

  @GetMapping("/sales")
  public ResponseEntity<?> statSales(String sdate, String edate, String vendor, String code, String column, String dir, int sno, int eno) {
    log.info("[SalesHistoryRestAPIController] sdate: {}, edate: {}, vendor: {}, code: {}, column: {}, dir: {}, sno: {}, eno: {}", sdate, edate, vendor, code, column, dir, sno, eno);

    if ("%".equals (code)) {
      int total = svc.getSalesHistoryStatVendorCount(sdate, edate, vendor, code);
      List<SalesHistoryStatVendor> list = svc.getSalesHistoryStatVendor(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    } else {
      int total = svc.getSalesHistoryStatCodeCount(sdate, edate, vendor, code);
      List<SalesHistoryStatCode> list = svc.getSalesHistoryStatCode(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    }
  }

  @GetMapping("/users")
  public ResponseEntity<?> statUser () {
    return ResponseEntity.ok("Users OK");
  }
}
