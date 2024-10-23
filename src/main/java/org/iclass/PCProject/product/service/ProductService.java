package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        for (int i = 0; i < 5; i++) {                            // 추천 상품 목록에 담을 5개 상품
            if (list.get(i).getStock() == 0) {       // stock(재고 수량)이 0이면 recommendedList에 해당 상품을 담지 않습니다.
                --i;
            } else {
                recommendedList.add(list.get(i));
            }
        }
        return recommendedList.stream().map(ProductDTO::toDto).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsList() {
        List<Product> list = productRepository.findAll();         // shuffle 하지 않으면 항상 seq 순으로 상품이 전시됩니다.
        return list.stream().map(ProductDTO::toDto).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByVendor(String vendor) {
        List<Product> list = productRepository.findAllByVendor(vendor);
        return list.stream().map(ProductDTO::toDto).collect(Collectors.toList());
    }

    public Optional<Product> getProductBySeq(int seq) {
        return productRepository.findById(seq);
    }

    public void createProduct(ProductDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }

    public void updateProduct(ProductDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }

    @Transactional
    public void deleteProduct(int seq) {
        System.out.println("삭제 요청 seq: " + seq); // seq 값을 출력
        productRepository.deleteById(seq);  // ID로 삭제

    }

}

//    public List<String> getRecentThumbnailBySeq(int seq) {
//        LinkedList<String> thumbsList = new LinkedList<>();
//        Optional<Product> dto = productRepository.findById(seq);
//        if(thumbsList.contains(dto.get().getThumb())) thumbsList.remove(dto.get().getThumb());
//        thumbsList.addFirst(dto.get().getThumb());
//        if(thumbsList.size() > 4) thumbsList.removeLast();
//        return thumbsList.stream().collect(Collectors.toList());
//    }


/*    private final LinkedList<String> recentProducts = new LinkedList<>();

    public void addProduct(String thumb) {
        if (recentProducts.contains(thumb)) {
            recentProducts.remove(thumb);
        }
        recentProducts.addFirst(thumb);
        if (recentProducts.size() > 4) {
            recentProducts.removeLast();
        }
    }

    public List<String> getRecentProducts() {
        return recentProducts;
    }*/

