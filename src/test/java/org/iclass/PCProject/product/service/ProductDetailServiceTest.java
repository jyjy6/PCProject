package org.iclass.PCProject.product.service;

import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDetailDTO;
import org.iclass.PCProject.product.entity.ProductDetail;
import org.iclass.PCProject.product.repository.ProductDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductDetailServiceTest {

    @Autowired
    private ProductDetailRepository detailRepository;
    @Autowired
    private ProductDetailService detailService;

    @Test
    void getProductDetailImgs() {
        int pSeq = 6;
        List<ProductDetail> list = detailRepository.findAllBypSeqOrderBySeq(pSeq);
        for(ProductDetail entity : list) log.info(":::entity: {}:::", entity);
        assertNotNull(list);
    }
}