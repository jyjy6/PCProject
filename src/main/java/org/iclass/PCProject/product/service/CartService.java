package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Cart;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.repository.CartRepository;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<CartDTO> getItems(String username) {
        List<Cart> items = cartRepository.findAllByUsernameOrderByRegDateDesc(username);
        return items.stream().map(CartDTO::toDTO).collect(Collectors.toList());
    }

    ProductDTO dto = null;
    public CartDTO addItem(int seq, int qty, String username) {
        Optional<Product> product = productRepository.findById(seq);
        product.ifPresent(p -> {
            Product entity = product.get();
            dto = ProductDTO.toDto(entity);
        });

        CartDTO item = new CartDTO();
        item.setUsername(username);
        item.setPSeq(dto.getSeq());
        item.setVendor(dto.getVendor());
        item.setName(dto.getName());
        item.setCode(dto.getCode());
        item.setPrice(dto.getPrice());
        item.setQuantity(qty);

        Cart entity = cartRepository.save(item.toEntity());

        return CartDTO.toDTO(entity);
    }
}