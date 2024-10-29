package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ProductPaymentDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.ProductPayment;
import org.iclass.PCProject.product.repository.ProductPaymentRepository;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.iclass.PCProject.statistics.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPaymentService {

    private final ProductService productService;
    private final ProductPaymentRepository paymentRepository;
    private final CartService cartService;
    private final StatisticsService statisticsService;
    private final ProductRepository productRepository;

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
                    alertMessage.append("수량 변경: ").append(dto.getName()).append(dto.getCode())
                            .append(" 제품의 수량이 재고를 초과하여 재고 수량으로 수정되었습니다.\\n");
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

                if (dto.getQuantity() > stock) {
                    alertMessage.append("수량 변경: ").append(dto.getName()).append(dto.getCode())
                            .append(" 제품의 수량이 재고를 초과하여 재고 수량으로 수정되었습니다.\\n");
                }
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

    public void updateStatus(int pSeq, String username) {
        paymentRepository.updateAllBypSeqAndUsername(pSeq, username);
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
        List<ProductDTO> dtos = productRepository.findAll().stream().map(ProductDTO::toDto).collect(Collectors.toList());
        for(ProductPayment item : items) {
            for(int i=0; i<dtos.size(); i++) {
                if(item.getPSeq() == dtos.get(i).getSeq()) {
                    dtos.get(i).setSeq(dtos.get(i).getStock() - item.getQuantity());
                }
            }
        }
    }
}
