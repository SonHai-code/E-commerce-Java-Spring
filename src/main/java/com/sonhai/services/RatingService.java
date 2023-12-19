package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Rating;
import com.sonhai.models.User;
import com.sonhai.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductRatings(Long productId);


}
