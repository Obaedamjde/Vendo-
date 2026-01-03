package com.aabu.finalproject.controller.order;


import com.aabu.finalproject.model.dto.OrderDto;
import com.aabu.finalproject.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @PostMapping("")
    public OrderDto createOrder(){
      return   orderService.createOrder();
    }

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @PostMapping("/confirm")
    public OrderDto confirmOrder(){
        return orderService.confirmOrder();
    }

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @DeleteMapping("/cancel")
    public void cancelOrder (){
        orderService.cancelOrder();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/getAllOrders")
    public List<OrderDto> getAllOrders (){
      return   orderService.getAllOrders();
    }


}
