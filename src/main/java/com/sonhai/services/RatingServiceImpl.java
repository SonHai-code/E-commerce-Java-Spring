package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Product;
import com.sonhai.models.Rating;
import com.sonhai.models.User;
import com.sonhai.repository.RatingRepository;
import com.sonhai.request.RatingRequest;

import java.time.LocalDateTime;
import java.util.List;

public class RatingServiceImpl implements RatingService{

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImpl(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setProduct(product);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRatings(Long productId) {
        return ratingRepository.getAllProductRatings(productId);
    }
}
