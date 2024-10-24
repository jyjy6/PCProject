package org.iclass.PCProject.product.service;

import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.entity.Review;
import org.iclass.PCProject.product.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ReviewServiceTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void getReviews() {
        List<Review> list = reviewRepository.findAllBypSeqOrderByRegDateDesc(11);
        log.info(":::Review List: {}:::", list.toString());
        assertNotNull(list);
    }
}