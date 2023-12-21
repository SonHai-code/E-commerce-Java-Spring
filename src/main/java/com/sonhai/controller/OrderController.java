package com.sonhai.controller;

import com.sonhai.exception.OrderException;
import com.sonhai.exception.UserException;
import com.sonhai.models.Address;
import com.sonhai.models.Order;
import com.sonhai.models.User;
import com.sonhai.services.OrderService;
import com.sonhai.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /* Create new order */
    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);

        return  new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /* Get user's order history */
    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrdersHistory(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.userOrdersHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /* Get a single order by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long orderId,
                                               @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

}
