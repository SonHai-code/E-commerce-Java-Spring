package com.sonhai.services;

import com.sonhai.exception.CartItemException;
import com.sonhai.exception.UserException;
import com.sonhai.models.Cart;
import com.sonhai.models.CartItem;
import com.sonhai.models.Product;
import com.sonhai.models.Size;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
