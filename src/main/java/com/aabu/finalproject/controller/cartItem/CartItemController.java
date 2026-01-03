package com.aabu.finalproject.controller.cartItem;


import com.aabu.finalproject.model.dto.CartItemDto;
import com.aabu.finalproject.model.dto.request.CartItemReq;
import com.aabu.finalproject.model.dto.updateReq.UpdateCartItemReq;
import com.aabu.finalproject.service.cartItem.CartItemImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartItem")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemImpl cartItem;

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("")
    public CartItemDto addItem(@RequestBody CartItemReq req){
        return cartItem.addItem(req);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PutMapping("/update")
    public CartItemDto updateQuantity(@RequestBody UpdateCartItemReq req){
        return cartItem.updateQuantity(req);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @DeleteMapping("/delete/{cartItemId}")
    public void deleteItem(@PathVariable  Long cartItemId ){
         cartItem.deleteItem(cartItemId);
    }
}
