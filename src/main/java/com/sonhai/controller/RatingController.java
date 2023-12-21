package com.sonhai.controller;

import com.sonhai.exception.ProductException;
import com.sonhai.exception.UserException;
import com.sonhai.models.Rating;
import com.sonhai.models.User;
import com.sonhai.request.RatingRequest;
import com.sonhai.services.RatingService;
import com.sonhai.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    /* Create new rating for the product */
    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req, user);

        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    /* Get all ratings of the product */
    @GetMapping("product/{productId}")
    public ResponseEntity<List<Rating>> getProductRatings(@PathVariable Long productId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        List<Rating> ratings = ratingService.getProductRatings(productId);

        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

}
