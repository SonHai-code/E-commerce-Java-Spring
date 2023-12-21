package com.sonhai.services;

import com.sonhai.exception.OrderException;
import com.sonhai.models.*;
import com.sonhai.repository.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;

    private CartService cartService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, AddressRepository addressRepository, UserRepository userRepository, OrderItemService orderItemService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Set the order(which contains multiple order items) for the user following specific address.
     * */
    @Override
    public Order createOrder(User user, Address shippingAddress) {
        /* Set the user field for the address and the address field for the user */
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);

        user.getAddresses().add(address);
        userRepository.save(user);

        /* Get the Cart of the user */
        Cart cart = cartService.findUserCart(user.getId());

        /* Setting values for OrderItems */
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setSize(item.getSize());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());
            orderItem.setUserId(user.getId());
            orderItem.setQuantity(item.getQuantity());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

        /* Setting values for the order */
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        order.setDiscounted(cart.getDiscounted());
        order.setTotalItems(cart.getTotalItems());

        order.setShippingAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PENDING");
        order.getPaymentDetails().setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        /* Set the order for each order item */
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);

        if (opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("Cannot found the Order with the ID: " + orderId);
    }

    @Override
    public List<Order> userOrdersHistory(Long userId) {
        List<Order> orders = orderRepository.getUserOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");

        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        orderRepository.deleteById(orderId);
    }
}
