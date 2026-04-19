package com.raunak.library.book_service.dto;

import lombok.Data;

@Data
public class UpdateReviewRequest {
    private double rating;
    private String reviewDescription;
}
