package com.sonhai.controller;

import com.sonhai.exception.ProductException;
import com.sonhai.exception.UserException;
import com.sonhai.models.Review;
import com.sonhai.request.ReviewRequest;
import com.sonhai.services.ReviewService;
import com.sonhai.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    /* Create new review for the product */
    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        Review review = reviewService.createReview(req, userService.findUserProfileByJwt(jwt));

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    /* Get all the reviews of the product */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        List<Review> reviews = reviewService.getAllProductReviews(productId);
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }


}
