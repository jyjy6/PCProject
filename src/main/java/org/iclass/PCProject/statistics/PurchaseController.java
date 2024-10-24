package org.iclass.PCProject.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@Controller
public class PurchaseController {
  @GetMapping("/purchase")
  public String showForm(Model model) {
    model.addAttribute("dto", new PurchaseDto());
    return "ryu/purchase.html";
  }

  @PostMapping("/purchase")
  @ResponseBody
  public Map<String, String> submitForm(@ModelAttribute PurchaseDto dto, Model model) {
    log.info(dto.toString());
    model.addAttribute("product", dto);
    return Map.of("result", "<h4>신청 완료되었습니다.</h4>");
  }
}
