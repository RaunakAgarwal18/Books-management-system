package com.raunak.library.book_service.dto;

import lombok.Data;

@Data
public class AddReviewRequest {
    private double rating;
    private Long bookId;
    private String reviewDescription;
}
