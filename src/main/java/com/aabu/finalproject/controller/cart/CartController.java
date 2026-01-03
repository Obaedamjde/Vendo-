package com.aabu.finalproject.controller.cart;


import com.aabu.finalproject.model.dto.CartDto;
import com.aabu.finalproject.service.cart.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartServiceImpl cartService;

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @PostMapping("")
    public CartDto createCart(){
        return cartService.createCartForCurrentUser();
    }


    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @DeleteMapping("/delete")
    public void deleteCart(){
        cartService.deleteLatestCartForCurrentUser();
    }
}
