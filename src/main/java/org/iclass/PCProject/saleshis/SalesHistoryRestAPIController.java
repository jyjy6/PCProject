package org.iclass.PCProject.salesHis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/purchase")
  public ResponseEntity<?> statPurchase() {
    return ResponseEntity.ok("Purchase OK");
  }

  @GetMapping("/sales")
  public ResponseEntity<?> statSales() {
    List<SalesHistoryStatDto> list = svc.getSalesHistoryStat();
    return ResponseEntity.ok(list);
  }
  @GetMapping("/users")
  public ResponseEntity<?> statUser () {
    return ResponseEntity.ok("Users OK");
  }
}
