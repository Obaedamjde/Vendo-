package com.aabu.finalproject.service.product;


import com.aabu.finalproject.model.dto.ProductDto;
import com.aabu.finalproject.model.dto.SearchResultItemDto;
import com.aabu.finalproject.model.dto.common.PageReqDTO;
import com.aabu.finalproject.model.dto.common.PageResponse;
import com.aabu.finalproject.model.dto.request.SearchProductDtoReq;
import com.aabu.finalproject.model.dto.updateReq.UpdateProductReq;
import com.aabu.finalproject.model.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    void addPic(UpdateProductReq req , ProductEntity entity);

    ProductDto create(UpdateProductReq req);
    ProductDto update(boolean updatePic,Integer id , UpdateProductReq req );

    boolean delete(Integer id);

    void deleteImageFromProduct(Integer id);

    PageResponse<SearchResultItemDto> search(SearchProductDtoReq req, PageReqDTO pageReqDTO) ;

    List<ProductDto> getAllBySeller(Long seller_id);
    }





