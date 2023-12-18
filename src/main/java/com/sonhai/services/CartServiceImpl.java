package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Cart;
import com.sonhai.models.User;
import com.sonhai.request.AddItemRequest;

public class CartServiceImpl implements CartService {

    @Override
    public Cart createCart(User user) {
        return null;
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        return null;
    }

    @Override
    public Cart findUserCart(Long userId) {
        return null;
    }
}
