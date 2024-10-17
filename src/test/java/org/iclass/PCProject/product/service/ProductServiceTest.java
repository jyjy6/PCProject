package org.iclass.PCProject.product.service;

import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void products() {
        List<Product> products = productRepository.findAll();
        for(Product product : products) log.info(":::product entity: {}:::", product.toString());
        assertNotNull(products);
    }

    @Test
    void recommendedProducts() {
        List<Product> list = productRepository.findAll();
        Collections.shuffle(list);
        List<Product> recommendedList = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {                            // 추천 상품 목록에 담을 5개 상품
            if(list.get(i).getStock() == 0) {    // stock(재고 수량)이 0이면 recommendedList에 해당 상품을 담지 않습니다.
//                --i;
                continue;
            } else {
                recommendedList.add(list.get(i));
            }
        }
        for(Product product : recommendedList) log.info(":::product entity: {}:::", product.getStock());    // stock이 0이 아닌 것만 확인
        assertNotNull(recommendedList);
    }

    @Test
    void listByVendor() {
        for(ProductDTO entity : productService.getProductsByVendor("삼성")) log.info(entity.toString());
        assertNotNull(productService.getProductsByVendor("삼성"));
    }

    ProductDTO dto = null;
    @Test
    void dtoBySeq() {
        int seq = 1;
        Optional<Product> product = productRepository.findById(seq);
        product.ifPresent(p -> {
            Product entity = product.get();
            dto = ProductDTO.toDto(entity);
        });
        log.info(":::dto: {}:::", dto.toString());
        assertNotNull(dto);
    }
}