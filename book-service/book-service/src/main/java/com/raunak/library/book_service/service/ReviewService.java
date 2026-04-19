package com.raunak.library.book_service.service;

import com.raunak.library.book_service.dto.AddReviewRequest;
import com.raunak.library.book_service.entity.Review;

public interface ReviewService {
    public Review addReview(AddReviewRequest reviewRequest);
    public Review updateReview()
}
