package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Cart;
import com.sonhai.models.CartItem;
import com.sonhai.models.Product;
import com.sonhai.models.User;
import com.sonhai.repository.CartRepository;
import com.sonhai.repository.ProductRepository;
import com.sonhai.request.AddItemRequest;

public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private  CartItemService cartItemService;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    /**
     * The user would send the request about adding cart items to their available cart.
     * */
    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        /* Get the current cart and the new products which the user want to add */
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());

        /* Check if the cart item has been added before */
        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

        /* If there is completely new card item crate new one */
        if(isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            /* Create a new cart item and add it to the cart */
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item has been added to the cart.";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);
        cart.setDiscounted(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
