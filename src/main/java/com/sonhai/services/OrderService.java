package com.sonhai.services;

import com.sonhai.exception.OrderException;
import com.sonhai.models.Address;
import com.sonhai.models.Order;
import com.sonhai.models.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress);
    
    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> userOrdersHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
    





}
