package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Cart;
import com.sonhai.models.User;
import com.sonhai.request.AddItemRequest;

public interface CartItemService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
