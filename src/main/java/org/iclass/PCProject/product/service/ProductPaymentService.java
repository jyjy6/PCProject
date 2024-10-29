package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ProductPaymentDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.ProductPayment;
import org.iclass.PCProject.product.repository.ProductPaymentRepository;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.iclass.PCProject.statistics.SalesHistory;
import org.iclass.PCProject.statistics.SalesHistoryRepository;
import org.iclass.PCProject.statistics.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPaymentService {

    private final ProductService productService;
    private final ProductPaymentRepository paymentRepository;
    private final CartService cartService;
    private final StatisticsService statisticsService;
    private final ProductRepository productRepository;
    private final SalesHistoryRepository salesHistoryRepository;

    public void addItems(String username, List<Integer> pSeqs) {
        StringBuilder alertMessage = new StringBuilder();
        List<ProductPayment> existingPayments = paymentRepository.findAllByUsername(username);
        Map<Integer, ProductPayment> paymentMap = new HashMap<>();
        for (ProductPayment payment : existingPayments) {
            paymentMap.put(payment.getPSeq(), payment);
        }
        for (int pSeq : pSeqs) {
            CartDTO dto = cartService.getItems(username, pSeq);
            ProductPayment existingPayment = paymentMap.get(pSeq);
            int stock = productService.getProductBySeq(pSeq).getStock();

            if (existingPayment != null) {
                int newQuantity = existingPayment.getQuantity() + dto.getQuantity();

                if (newQuantity > stock) {
                    existingPayment.setQuantity(stock);
                } else {
                    existingPayment.setQuantity(newQuantity);
                }
                paymentRepository.save(existingPayment);
            } else {
                ProductPaymentDTO item = ProductPaymentDTO.builder()
                        .username(username)
                        .pSeq(dto.getPSeq())
                        .vendor(dto.getVendor())
                        .productname(dto.getName())
                        .code(dto.getCode())
                        .quantity(Math.min(dto.getQuantity(), stock))
                        .price(calcPrice(dto.getPrice(), productService.getProductBySeq(pSeq).getDiscount()))
                        .thumb(productService.getProductBySeq(pSeq).getThumb())
                        .status(0)
                        .build();

                paymentRepository.save(item.toEntity());
            }
        }
    }

    public int calcPrice(int price, int discount) {
        return (int) (price - (price * (discount * 0.01)));
    }

    public List<ProductPaymentDTO> getAllItemsByUsername(String username) {
        List<ProductPayment> items = paymentRepository.findAllByUsername(username);
        return items.stream().map(ProductPaymentDTO::toDto).collect(Collectors.toList());
    }

    public void deleteItemsDonePurchasing(int pSeq, String username) {
        paymentRepository.deleteBypSeqAndUsername(pSeq, username);
    }

    public void saveAllBypSeq(int pSeq) {
        List<ProductPayment> items = paymentRepository.findBypSeq(pSeq);
        for (ProductPayment item : items) {
            String username = item.getUsername();
            String code = item.getCode();
            Integer price = item.getPrice();
            Integer quantity = item.getQuantity();
            String vendor = item.getVendor();
            // SalesHistory 객체 저장을 위해 savePayment 메서드 호출
           /* statisticsService.savePayment(username, code, price, quantity, vendor);*/
        }

    }

    public void updateStock(int pSeq) {
        List<ProductPayment> items =  paymentRepository.findBypSeq(pSeq);
        log.info(":::items : {}:::", items);
        List<ProductDTO> dtos = productRepository.findAll().stream().map(ProductDTO::toDto).collect(Collectors.toList());
        log.info(":::dtos : {}:::", dtos);
        for(ProductPayment item : items) {
            log.info(":::item : {}:::", item);
            for(int i=0; i<dtos.size(); i++) {
                if(item.getPSeq() == dtos.get(i).getSeq()) {
                    log.info(":::item.getPSeq: {}:::", item.getPSeq());
                    log.info(":::dtos.get(i).getSeq(): {}:::", dtos.get(i).getSeq());
                    log.info(":::dtos.get(i).getStock() : {}:::", dtos.get(i).getStock());
                    dtos.get(i).setStock(dtos.get(i).getStock() - item.getQuantity());
                    log.info(":::item.getQuantity() : {}:::", item.getQuantity());
                    log.info(":::dtos.get(i).getStock() : {}:::", dtos.get(i).getStock());
                    productRepository.updateStockBySeq(dtos.get(i).getStock(), dtos.get(i).getSeq());
                }
            }
        }
    }

    public void saveBypSeqIntoSalesHistory(Integer pSeq) {
        List<ProductPayment> items =  paymentRepository.findBypSeq(pSeq);
        for(ProductPayment item : items) {
            SalesHistory dto = new SalesHistory();
            dto.setCode(item.getCode());
            dto.setCount(item.getQuantity());
            dto.setPrice(item.getPrice());
            dto.setUsername(item.getUsername());
            dto.setVendor(item.getVendor());
            dto.setStslogis(0);

            salesHistoryRepository.save(dto);
        }
    }
}
