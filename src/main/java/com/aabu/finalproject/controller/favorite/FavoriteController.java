package com.aabu.finalproject.controller.favorite;


import com.aabu.finalproject.model.dto.FavoriteDto;
import com.aabu.finalproject.model.dto.request.FavoriteReq;
import com.aabu.finalproject.service.favorite.FavoriteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteServiceImpl service;

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @PostMapping("")
    public FavoriteDto addToFavorite(@RequestBody FavoriteReq req){
        return service.addToFavorite(req);
    }

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @DeleteMapping("/deleteItem")
    public void removeFromFavorite( @RequestBody FavoriteReq req){
         service.removeFromFavorite(req);
    }

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @GetMapping("/getAllFavoriteProduct")
    public List<FavoriteDto> addToFavorite(){
        return service.getAllFavorite();
    }

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @DeleteMapping("/clearFavorite")
    public void clearFavorite(){
        service.clearFavorite();
    }


}
