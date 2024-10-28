package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ProductPaymentDTO;
import org.iclass.PCProject.product.entity.Cart;
import org.iclass.PCProject.product.entity.ProductPayment;
import org.iclass.PCProject.product.repository.ProductPaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPaymentService {

    private final ProductService productService;
    private final ProductPaymentRepository paymentRepository;
    private final CartService cartService;

    public static int paymentNo = 1;

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

    public void updateStatus(String pSeq, String username) {
        paymentRepository.updateAllBypSeqAndUsername(pSeq, username);
    }
}
