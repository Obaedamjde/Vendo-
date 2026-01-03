package com.aabu.finalproject.controller.product;


import com.aabu.finalproject.model.dto.ProductDto;
import com.aabu.finalproject.model.dto.SearchResultItemDto;
import com.aabu.finalproject.model.dto.common.PageReqDTO;
import com.aabu.finalproject.model.dto.common.PageResponse;
import com.aabu.finalproject.model.dto.request.SearchProductDtoReq;
import com.aabu.finalproject.model.dto.updateReq.UpdateProductReq;
import com.aabu.finalproject.service.product.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl service;

    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @PostMapping("")
    public ProductDto create(@Valid @RequestBody UpdateProductReq req){
       return service.create(req);
    }

    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @PutMapping("/update/{UpdatePic}/{id}")
    public ProductDto update( @Valid @PathVariable Boolean UpdatePic,
                              @Valid @PathVariable Integer id  ,
                              @Valid @RequestBody UpdateProductReq req){
        return service.update(UpdatePic,id,req);
    }

    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@Valid @PathVariable Integer id ){
        return service.delete(id);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/search")
    public PageResponse<SearchResultItemDto> search(
            @RequestBody SearchProductDtoReq req,
            @ModelAttribute PageReqDTO pageReqDTO
            )
    {
        return service.search(req,pageReqDTO);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/getAllProductBySeller/{sellerId}")
    public List<ProductDto> getAllProductBySeller(@PathVariable  Long sellerId){
       return service.getAllBySeller(sellerId);
    }

}
