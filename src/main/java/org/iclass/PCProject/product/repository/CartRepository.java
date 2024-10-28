package org.iclass.PCProject.product.repository;

import jakarta.transaction.Transactional;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUsernameOrderByRegDateDesc(String username);
    @Query("select c from Cart c where c.username = :username and c.pSeq = :pSeq")
    Cart findByUsernameAndpSeq(String username, int pSeq);
    Cart save(Cart cart);
    Cart findQuantityBypSeq(int pSeq);
    @Modifying
    @Transactional
    @Query("update Cart c set c.quantity = :quantity where c.username = :username and c.pSeq = :pSeq")
    void updateQuantityBypSeq(int pSeq, int quantity, String username);
    @Modifying
    @Transactional
    @Query("delete from Cart c where c.username = :username and c.pSeq = :pSeq")
    void deleteByUsernameAndPSeq(int pSeq, String username);
}