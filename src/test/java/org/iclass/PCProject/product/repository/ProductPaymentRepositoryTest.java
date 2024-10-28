package org.iclass.PCProject.product.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductPaymentRepositoryTest {

    @Autowired
    private ProductPaymentRepository paymentRepository;

    @Test
    void delete() {
        paymentRepository.deleteBypSeqAndUsername(3, "test");
    }
}