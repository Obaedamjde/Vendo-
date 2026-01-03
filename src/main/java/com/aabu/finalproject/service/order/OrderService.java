package com.aabu.finalproject.service.order;

import com.aabu.finalproject.model.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder();

    OrderDto confirmOrder();

    void cancelOrder();

    List<OrderDto> getAllOrders();

}
