package com.rso.orderservice.service;

import com.rso.orderservice.dto.OrderRequest;
import com.rso.orderservice.dto.OrderResponse;
import com.rso.orderservice.model.Order;
import com.rso.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .name(orderRequest.getName())
                .price(orderRequest.getPrice())
                .userId(orderRequest.getUserId())
                .address(orderRequest.getAddress())
                .build();

        orderRepository.save(order);
        log.info("Order {} is saved", order.getId());

    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .name(order.getName())
                .price(order.getPrice())
                .userId(order.getUserId())
                .address(order.getAddress())
                .build();
    }
}
