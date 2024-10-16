package org.iclass.PCProject.product;

import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void products() {
        List<Product> products = productRepository.findAll();
        for(Product product : products) log.info(":::product entity: {}:::", product.toString());
        log.info("111111111111111111111111111111");
        assertNotNull(products);
    }

}