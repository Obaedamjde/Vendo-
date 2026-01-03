package com.aabu.finalproject.service.favorite;

import com.aabu.finalproject.model.dto.FavoriteDto;
import com.aabu.finalproject.model.dto.request.FavoriteReq;

import java.util.List;

public interface FavoriteService {

    FavoriteDto addToFavorite(FavoriteReq req);
    void removeFromFavorite(FavoriteReq req);

    List<FavoriteDto> getAllFavorite();

    void clearFavorite();
}
