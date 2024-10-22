package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ProductDetailDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.ProductDetail;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> recommendedProducts() {
        List<Product> list = productRepository.findAll();
        Collections.shuffle(list);
        List<Product> recommendedList = new ArrayList<>();
        for(int i=0; i<5; i++) {                            // 추천 상품 목록에 담을 5개 상품
            if(list.get(i).getStock() == 0) {       // stock(재고 수량)이 0이면 recommendedList에 해당 상품을 담지 않습니다.
                --i;
            } else {
                recommendedList.add(list.get(i));
            }
        }
        return recommendedList.stream().map(ProductDTO::toDto).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> list = productRepository.findAll();
        Collections.shuffle(list);                              // shuffle 하지 않으면 항상 seq 순으로 상품이 전시됩니다.
        return list.stream().map(ProductDTO::toDto).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByVendor(String vendor) {
        List<Product> list = productRepository.findAllByVendor(vendor);
        return list.stream().map(ProductDTO::toDto).collect(Collectors.toList());
    }

    ProductDTO dto = null;
    public ProductDTO getProductBySeq(int seq) {
        Optional<Product> product = productRepository.findById(seq);
        product.ifPresent(p -> {
            Product entity = product.get();
            dto = ProductDTO.toDto(entity);
        });
        return dto;
    }

}
