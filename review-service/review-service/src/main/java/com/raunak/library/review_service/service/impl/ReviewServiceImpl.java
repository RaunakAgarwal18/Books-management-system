package com.raunak.library.review_service.service.impl;

import com.raunak.library.review_service.dao.ReviewRepository;
import com.raunak.library.review_service.dto.AddReviewRequest;
import com.raunak.library.review_service.dto.UpdateReviewRequest;
import com.raunak.library.review_service.entity.Review;
import com.raunak.library.review_service.service.ReviewService;
import com.raunak.library.review_service.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review addReview(String userName, AddReviewRequest reviewRequest) {
        Review review = new Review();
        review.setReviewDescription(reviewRequest.getReviewDescription());
        review.setRating(reviewRequest.getRating());
        review.setBookId(reviewRequest.getBookId());
        review.setDate(new Date());
        review.setUserEmail(userName);
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, UpdateReviewRequest reviewRequest) throws RuntimeException {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if(existingReview.isPresent()){
            Review review = existingReview.get();
            review.setReviewDescription(reviewRequest.getReviewDescription());
            review.setRating(reviewRequest.getRating());
            return reviewRepository.save(review);
        }
        throw new RuntimeException("Review not found");
    }

    @Override
    public Page<Review> findReviewByBookId(Long id, Integer pageNumber, Integer pageSize) {
        Pageable pageable = AppUtil.createPageable(pageNumber,pageSize);
        return reviewRepository.findByBookId(id, pageable);
    }
}
