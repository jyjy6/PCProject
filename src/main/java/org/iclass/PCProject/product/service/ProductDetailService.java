package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.ProductDetailDTO;
import org.iclass.PCProject.product.entity.ProductDetail;
import org.iclass.PCProject.product.repository.ProductDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductDetailRepository detailRepository;

    public List<ProductDetailDTO> getProductDetailImgs(int pSeq) {
        List<ProductDetail> list = detailRepository.findAllBypSeqOrderBySeq(pSeq);
        return list.stream().map(ProductDetailDTO::toDto).collect(Collectors.toList());
    }
}
