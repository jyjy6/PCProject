package org.iclass.PCProject.product.repository;

import jakarta.transaction.Transactional;
import org.iclass.PCProject.product.dto.ProductPaymentDTO;
import org.iclass.PCProject.product.entity.ProductPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPaymentRepository extends JpaRepository<ProductPayment, Integer> {

    ProductPayment save(ProductPaymentDTO dto);

    List<ProductPayment> findAllByUsername(String username);

    @Modifying
    @Transactional
    @Query("delete from ProductPayment p where p.username = :username and p.pSeq = :pSeq")
    int deleteBypSeqAndUsername(int pSeq, String username);

    @Modifying
    @Transactional
    @Query("update ProductPayment p set p.status = 1 where p.username = :username and p.pSeq = :pSeq")
    void updateAllBypSeqAndUsername(Integer pSeq, String username);

    List<ProductPayment> findBypSeq(Integer pSeq);  // P를 소문자 p로 변경
}
