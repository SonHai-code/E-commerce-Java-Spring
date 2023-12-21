package com.sonhai.services;

import com.sonhai.models.OrderItem;
import com.sonhai.repository.OrderItemRepository;

public class OrderItemServiceImpl implements OrderItemService{
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
