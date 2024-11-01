package org.iclass.PCProject.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ReviewDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.Review;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.iclass.PCProject.product.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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

    public List<ReviewDTO> getReviews(int pSeq) {
        List<Review> list = reviewRepository.findAllBypSeqOrderByRegDateDesc(pSeq);
        return list.stream().map(ReviewDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void addReview(Map<String, Object> map, ProductDTO dto, String username) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setCode(dto.getCode());
        reviewDTO.setContent(map.get("content").toString());
        reviewDTO.setScore((Integer) map.get("score"));
        reviewDTO.setUsername(username);
        reviewDTO.setPSeq(dto.getSeq());

        reviewRepository.save(reviewDTO.toEntity());
    }

    public double getAvgScore(int pSeq) {

        double avgScore = 0;
        List<Review> list = reviewRepository.findAllBypSeqOrderByRegDateDesc(pSeq);
        for(Review entity : list) {
            avgScore += entity.getScore();
        }

        if(list.size() > 0) avgScore = avgScore / list.size();
        else avgScore = 0.0;

        DecimalFormat df = new DecimalFormat("#.00");
        String formattedValue = df.format(avgScore);
        return Double.parseDouble(formattedValue);
    }
}
