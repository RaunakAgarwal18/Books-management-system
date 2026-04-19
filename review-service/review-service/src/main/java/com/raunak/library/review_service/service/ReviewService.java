package com.raunak.library.review_service.service;

import com.raunak.library.review_service.dto.AddReviewRequest;
import com.raunak.library.review_service.dto.UpdateReviewRequest;
import com.raunak.library.review_service.entity.Review;
import org.springframework.data.domain.Page;

public interface ReviewService {
    public Review addReview(String userName, AddReviewRequest reviewRequest);
    public Review updateReview(Long id, UpdateReviewRequest reviewRequest);
    public Page<Review> findReviewByBookId(Long id, Integer pageNumber, Integer pageSize);
}
