package com.sonhai.services;

import com.sonhai.exception.OrderException;
import com.sonhai.models.Address;
import com.sonhai.models.Order;
import com.sonhai.models.User;
import com.sonhai.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private CartRepository cartRepository;
    private CartService cartItemService;
    private ProductService productService;

    public OrderServiceImpl(CartRepository cartRepository, CartService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {


        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> userOrdersHistory(Long userId) {
        return null;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }
}
