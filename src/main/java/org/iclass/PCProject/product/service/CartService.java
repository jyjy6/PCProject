package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Cart;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.repository.CartRepository;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<CartDTO> getItems(String username) {
        List<Cart> items = cartRepository.findAllByUsernameOrderByRegDateDesc(username);
        return items.stream().map(CartDTO::toDTO).collect(Collectors.toList());
    }

    public CartDTO getItems(String username, int pSeq) {
        Cart item = cartRepository.findByUsernameAndpSeq(username, pSeq);
        return CartDTO.toDTO(item);
    }

    private ProductDTO dto = null;
    public void addItem(int seq, int qty, String username) {
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
        item.setThumb(dto.getThumb());
        item.setQuantity(qty);

//        cartRepository.save(item.toEntity());

        boolean flag = false;
        for(Cart c : cartRepository.findAllByUsernameOrderByRegDateDesc(username)) {
            if(c.getPSeq() == item.getPSeq()) flag = true;
        }

        if(flag) {
            int qtyResult = cartRepository.findQuantityBypSeq(seq).getQuantity() + qty;
            cartRepository.updateQuantityBypSeq(item.getPSeq(), qtyResult, username);
        }
        else if(!flag) {
            cartRepository.save(item.toEntity());
        }
    }

    public void removeItems(List<Integer> pSeqs, String username) {
        for(Integer pSeq : pSeqs) {
                cartRepository.deleteByUsernameAndPSeq(pSeq, username);
        }
    }

    public void updateQuantity(int pSeq, int qty, String username) {
        List<Cart> items = cartRepository.findAllByUsernameOrderByRegDateDesc(username);
        for(Cart c : items) {
            if(c.getPSeq() == pSeq) cartRepository.updateQuantityBypSeq(pSeq, qty, username);
        }
    }
}
