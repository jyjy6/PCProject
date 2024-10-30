package org.iclass.PCProject.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.admin.repository.AdminProductRepository;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@Slf4j
@RequiredArgsConstructor
@Service
public class AdminProductService {
    private final ProductRepository productRepository;
    private final AdminProductRepository adminProductRepository;

    public List<ProductDTO> getAllProducts() {
        List<Product> list = productRepository.findAll();
        Collections.shuffle(list);                              // shuffle 하지 않으면 항상 seq 순으로 상품이 전시됩니다.
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

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setSeq(product.getSeq());
        dto.setCode(product.getCode());
        dto.setDiscount(product.getDiscount());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setRegDate(product.getRegDate());
        dto.setStatus(product.getStatus());
        dto.setStock(product.getStock());
        dto.setThumb(product.getThumb());
        dto.setVendor(product.getVendor());
        return dto;
    }

    public Page<ProductDTO> getProducts(String vendor, LocalDateTime regDate, Integer price, Integer stock, String search, Pageable pageable) {
        Page<Product> productsPage = adminProductRepository.findByFilters(vendor, regDate, price, stock, search, pageable);
        return productsPage.map(this::convertToDTO);
    }

    public void createProduct(ProductDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }

    public void updateProduct(ProductDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }

    public void deleteProduct(int seq) {

    }
}



