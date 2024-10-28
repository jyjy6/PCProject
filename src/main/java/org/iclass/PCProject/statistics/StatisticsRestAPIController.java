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
    List<ChartDto> dataPurchase = svc.getChartPurchase();
    List<ChartDto> dataSales    = svc.getChartSales();
    List<ChartDto> dataUsers    = svc.getChartUsers();
    return ResponseEntity.ok().body(Map.of("purchase", dataPurchase, "sales", dataSales, "users", dataUsers));
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
      int total = svc.getPurchaseHistoryCountByVendor(sdate, edate, vendor, code);
      List<PurchaseHistoryByVendorDto> list = svc.getPurchaseHistoryByVendor(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    } else {
      int total = svc.getPurchaseHistoryCountByCode(sdate, edate, vendor, code);
      List<PurchaseHistoryByCodeDto> list = svc.getPurchaseHistoryByCode(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    }
  }

  @GetMapping("/sales")
  public ResponseEntity<?> statSales(String sdate, String edate, String vendor, String code, String column, String dir, int sno, int eno) {
    log.info("[SalesHistoryRestAPIController] sdate: {}, edate: {}, vendor: {}, code: {}, column: {}, dir: {}, sno: {}, eno: {}", sdate, edate, vendor, code, column, dir, sno, eno);

    if ("%".equals (code)) {
      int total = svc.getSalesHistoryCountByVendor(sdate, edate, vendor, code);
      List<SalesHistoryByVendorDto> list = svc.getSalesHistoryByVendor(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    } else {
      int total = svc.getSalesHistoryCountByCode(sdate, edate, vendor, code);
      List<SalesHistoryByCodeDto> list = svc.getSalesHistoryByCode(sdate, edate, vendor, code, column, dir, sno, eno);
      return ResponseEntity.ok().body(Map.of("total", total, "data", list));
    }
  }

  @GetMapping("/users")
  public ResponseEntity<?> statUsers () {
    List<UserDto> list = svc.getUsers();
    return ResponseEntity.ok().body(list);
  }
  @GetMapping("/usersales")
  public ResponseEntity<?> statSalesByUser (String sdate, String edate) {
    List<SalesByUserDto> list = svc.getSalesByUser(sdate, edate);
    return ResponseEntity.ok().body(list);
  }
}
