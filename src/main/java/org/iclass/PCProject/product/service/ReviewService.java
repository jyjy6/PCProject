package org.iclass.PCProject.product.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.ReviewDTO;
import org.iclass.PCProject.product.entity.Review;
import org.iclass.PCProject.product.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviews(int pSeq) {
        List<Review> list = reviewRepository.findAllBypSeqOrderByRegDateDesc(pSeq);
        return list.stream().map(ReviewDTO::toDTO).collect(Collectors.toList());
    }
}
