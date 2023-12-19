package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Product;
import com.sonhai.models.Review;
import com.sonhai.models.User;
import com.sonhai.repository.ProductRepository;
import com.sonhai.repository.ReviewRepository;
import com.sonhai.request.ReviewRequest;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewServiceImpl implements ReviewService{
    private ReviewRepository reviewRepository;
    private ProductService productService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Review review = new Review();
        review.setReview(req.getReview());
        review.setUser(user);
        review.setProduct(product);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllProductReviews(Long productId) {
        return reviewRepository.getAllProductReviews(productId);
    }
}
