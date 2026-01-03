package com.aabu.finalproject.service.seller;

import com.aabu.finalproject.model.dto.SellerProfileDto;
import com.aabu.finalproject.model.dto.request.SellerReq;

import java.util.List;

public interface SellerService {

    SellerProfileDto CreateShop(SellerReq req );

    boolean AcceptCreateShop(Long user_Id);

    boolean RejectCreateShop(Long user_Id);

    List<SellerProfileDto>getAllSellers();
}
