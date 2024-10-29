package org.iclass.PCProject.product.repository;

import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByVendor(String vendor);

    @Modifying
    @Query("update Product p set p.stock = :stock where p.seq = :seq")
    void updateStockBySeq(int stock, int seq);
}
