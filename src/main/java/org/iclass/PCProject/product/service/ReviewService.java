package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ReviewDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.Review;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.iclass.PCProject.product.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public List<ReviewDTO> getReviews(int pSeq) {
        List<Review> list = reviewRepository.findAllBypSeqOrderByRegDateDesc(pSeq);
        return list.stream().map(ReviewDTO::toDTO).collect(Collectors.toList());
    }

    public void addReview(Map<String, Object> map, ProductDTO dto, String username) {

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setCode(dto.getCode());
        reviewDTO.setContent(map.get("content").toString());
        reviewDTO.setScore((Long) map.get("score"));
        reviewDTO.setUsername(username);
        reviewDTO.setPSeq(dto.getSeq());
    }
}
