package com.web.garimaElectrical.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.garimaElectrical.model.cart;
import com.web.garimaElectrical.model.cartItems;
import com.web.garimaElectrical.model.orderItems;
import com.web.garimaElectrical.model.orders;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.orderRepository;

@Service
public class orderService {

    @Autowired
    private orderRepository orderRepository;

    public List<orders> getOrdersByUser(user user) {
        return orderRepository.findByUser(user);
    }

    public orders createOrderFromCart(cart cart,double total) {
        orders order = new orders();
        order.setUser(cart.getUser());
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());

        for (cartItems cartItem : cart.getItems()) {
            orderItems orderItem = new orderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);
        }
        	return orderRepository.save(order);
    }
}