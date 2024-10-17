package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Product;
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
        for(int i=0; i<5; i++) recommendedList.add(list.get(i));
        return recommendedList.stream().map(ProductDTO::toDto).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsList() {
        List<Product> list = productRepository.findAll();
        Collections.shuffle(list);
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
}
