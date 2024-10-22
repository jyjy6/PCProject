package org.iclass.PCProject.product.repository;

import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUsernameOrderByRegDateDesc(String username);
    Cart save(Cart cart);
}
