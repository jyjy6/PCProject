package org.iclass.PCProject.product.repository;

import org.iclass.PCProject.product.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findAllBypSeqOrderBySeq(int pSeq);

}
