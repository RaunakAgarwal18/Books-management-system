package com.raunak.library.review_service.controller;

import com.raunak.library.review_service.dto.AddReviewRequest;
import com.raunak.library.review_service.dto.UpdateReviewRequest;
import com.raunak.library.review_service.entity.Review;
import com.raunak.library.review_service.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<Review>> getReviewsByBookId(@PathVariable Long id, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize){
        Page<Review> reviews = reviewService.findReviewByBookId(id,pageNumber,pageSize);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("")
    public ResponseEntity<Review> addReview(Principal principal, AddReviewRequest reviewRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(principal.getName(),reviewRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, UpdateReviewRequest reviewRequest){
        return ResponseEntity.ok(reviewService.updateReview(id,reviewRequest));
    }
}
