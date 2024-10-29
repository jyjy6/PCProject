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
    List<ProductPayment> findBypSeq(Integer pSeq);

    @Modifying
    @Transactional
    @Query("delete from ProductPayment p where p.username = :username and p.pSeq = :pSeq")
    void deleteBypSeqAndUsername(int pSeq, String username);

}
