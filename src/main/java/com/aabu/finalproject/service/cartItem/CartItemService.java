package com.aabu.finalproject.service.cartItem;

import com.aabu.finalproject.model.dto.CartDto;
import com.aabu.finalproject.model.dto.CartItemDto;
import com.aabu.finalproject.model.dto.request.CartItemReq;
import com.aabu.finalproject.model.dto.updateReq.UpdateCartItemReq;

public interface CartItemService {


    CartItemDto addItem(CartItemReq req );
    CartItemDto updateQuantity(UpdateCartItemReq req );
    void deleteItem(Long cartItemId );


}
