package com.raunak.library.review_service.dto;

import lombok.Data;

@Data
public class UpdateReviewRequest {
    private double rating;
    private String reviewDescription;
}
