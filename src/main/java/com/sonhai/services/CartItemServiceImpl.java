package com.sonhai.services;

import com.sonhai.exception.CartItemException;
import com.sonhai.exception.UserException;
import com.sonhai.models.*;
import com.sonhai.repository.CartItemRepository;
import com.sonhai.repository.CartRepository;

import java.util.Optional;

public class CartItemServiceImpl implements CartItemService{
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        CartItem createdCartItem = cartItemRepository.save(cartItem);

        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws CartItemException, UserException {
        /* Find the item using cardItemId */
        CartItem item = findCartItemById(cartItemId);
        User user = userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, Size size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size,userId);
        return  cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);

        User user = userService.findUserById(cartItem.getUserId());

        User reqUser = userService.findUserById(userId);

        if (user.getId().equals(reqUser.getId())) {
            cartRepository.deleteById(cartItemId);
        }
        else {
            throw new UserException("You can't remove another users item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new CartItemException("CartItem not Found ");
    }
}
