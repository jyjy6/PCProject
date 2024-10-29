package org.iclass.PCProject.product.repository;

import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllBypSeqOrderByRegDateDesc(int pSeq);

    void deleteBySeq(int seq);
}
