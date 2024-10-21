package org.iclass.PCProject.saleshis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stat")
public class SalesHistoryRestAPIController {
  private final SalesHistoryService svc;

  @GetMapping("/")
  public ResponseEntity<?> summary () {
    return ResponseEntity.ok().body("Summary OK");
  }
  @GetMapping("/test")
  public ResponseEntity<?> statTest () {
    SalesHistoryDto dto = svc.testSQL(100);
    return ResponseEntity.ok(List.of(dto));
  }

  @GetMapping("/prodlist")
  public ResponseEntity<?> statProdList() {
    List<ProdListDto> list = svc.getProdList ();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/purchase")
  public ResponseEntity<?> statPurchase() {
    return ResponseEntity.ok("Purchase OK");
  }

  @GetMapping("/sales")
  public ResponseEntity<?> statSales(String sdate, String edate, String vendor, String code, String column, String dir) {
    log.info("[SalesHistoryRestAPIController] sdate: {}, edate: {}, vendor: {}, code: {}, column: {}, dir: {}", sdate, edate, vendor, code, column, dir);
    if ("%".equals (code)) {
      List<SalesHistoryStatVendor> list = svc.getSalesHistoryStatVendor(sdate, edate, vendor, code, column, dir);
      return ResponseEntity.ok().body(list);
    } else {
      List<SalesHistoryStatCode> list = svc.getSalesHistoryStatCode(sdate, edate, vendor, code, column, dir);
      return ResponseEntity.ok().body(list);
    }
  }

  /*
  @GetMapping("/sales2")
  public ResponseEntity<?> statSales2(String sdate, String edate, String vendor, String code, String column, String dir) {
    log.info("[SalesHistoryRestAPIController] sdate: {}, edate: {}, vendor: {}, code: {}, column: {}, dir: {}", sdate, edate, vendor, code, column, dir);
    List<SalesHistoryStatVendor> list = svc.getSalesHistoryStatVendor2(sdate, edate, vendor, code, column, dir);
    return ResponseEntity.ok().body(list);
  }
  */

  @GetMapping("/users")
  public ResponseEntity<?> statUser () {
    return ResponseEntity.ok("Users OK");
  }
}
