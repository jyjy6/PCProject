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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPaymentService {

    private final ProductService productService;
    private final ProductPaymentRepository paymentRepository;
    private final CartService cartService;
    private final StatisticsService statisticsService;

    public static int paymentNo = 1;
    private final ProductRepository productRepository;

    public void addItems(String username, List<Integer> pSeqs) {
        ProductPaymentDTO item = new ProductPaymentDTO();
        for(int pSeq : pSeqs) {
            CartDTO dto = cartService.getItems(username, pSeq);
            item.setUsername(username);
            item.setPSeq(dto.getPSeq());
            item.setVendor(dto.getVendor());
            item.setProductname(dto.getName());
            item.setCode(dto.getCode());
            item.setQuantity(dto.getQuantity());
            item.setPrice(calcPrice(dto.getPrice(), productService.getProductBySeq(pSeq).getDiscount()));
            item.setThumb(productService.getProductBySeq(pSeq).getThumb());
            item.setStatus(0);

            paymentRepository.save(item.toEntity());
        }
    }

//    public void addItem(String username, int pSeq, int qty) {
//        ProductPaymentDTO item = new ProductPaymentDTO();
//        ProductDTO dto = productService.getProductBySeq(pSeq);
//
//        item.setUsername(username);
//        item.setPSeq(dto.getSeq());
//        item.setVendor(dto.getVendor());
//        item.setProductname(dto.getName());
//        item.setCode(dto.getCode());
//        item.setQuantity(qty);
//        item.setPrice(calcPrice(dto.getPrice(), productService.getProductBySeq(pSeq).getDiscount()));
//        item.setThumb(productService.getProductBySeq(pSeq).getThumb());
//        item.setStatus(0);
//
//        paymentRepository.save(item.toEntity());
//    }

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
