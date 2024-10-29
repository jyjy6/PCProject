package org.iclass.PCProject.admin.repository;

import org.apache.ibatis.annotations.Param;
import org.iclass.PCProject.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdminProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE "
            + "(:vendor IS NULL OR p.vendor = :vendor) AND "
            + "(:regDate IS NULL OR p.regDate >= :regDate) AND "
            + "(:price IS NULL OR p.price = :price) AND "
            + "(:stock IS NULL OR p.stock = :stock)")
    Page<Product> findAllByCriteria(@Param("vendor") String vendor,
                                    @Param("regDate") LocalDateTime regDate,
                                    @Param("price") Integer price,
                                    @Param("stock") Integer stock,
                                    Pageable pageable);

}
