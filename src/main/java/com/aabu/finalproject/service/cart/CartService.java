package com.aabu.finalproject.service.cart;


import com.aabu.finalproject.model.dto.CartDto;

public interface CartService {

    CartDto createCartForCurrentUser();
    void deleteLatestCartForCurrentUser();
    Long currentUserId();
}
