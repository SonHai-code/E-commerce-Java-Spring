package com.sonhai.controller;

import com.sonhai.exception.ProductException;
import com.sonhai.exception.UserException;
import com.sonhai.models.Cart;
import com.sonhai.models.User;
import com.sonhai.request.AddItemRequest;
import com.sonhai.response.ApiResponse;
import com.sonhai.services.CartService;
import com.sonhai.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Cart Management - Find the user cart and add items to the cart.
 * */

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    /* Get the cart from user's ID */
    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /* Add cart items to the cart */
    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addCartItemsToCart(@RequestBody AddItemRequest req,
                                                          @RequestHeader("Authorization") String jwt)
                                                          throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(),req);

        ApiResponse res = new ApiResponse();
        res.setMessage("The items has been added to the cart.");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
