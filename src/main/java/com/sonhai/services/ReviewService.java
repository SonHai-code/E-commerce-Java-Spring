package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Review;
import com.sonhai.models.User;
import com.sonhai.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllProductReviews(Long productId);
}
