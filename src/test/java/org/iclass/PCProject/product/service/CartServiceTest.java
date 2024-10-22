package org.iclass.PCProject.product.service;

import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Cart;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.repository.CartRepository;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
@Slf4j
class CartServiceTest {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;


    ProductDTO dto = null;
    @Test
    void addItem() {
        Optional<Product> product = productRepository.findById(17);
        product.ifPresent(p -> {
            Product entity = product.get();
            dto = ProductDTO.toDto(entity);
        });

        CartDTO item = new CartDTO();
        item.setUsername("test");
        item.setPSeq(dto.getSeq());
        item.setVendor(dto.getVendor());
        item.setName(dto.getName());
        item.setCode(dto.getCode());
        item.setPrice(dto.getPrice());
        item.setQuantity(1);

        Cart entity = cartRepository.save(item.toEntity());

        log.info(":::entity: {}:::", entity);

    }

}