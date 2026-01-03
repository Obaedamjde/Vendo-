package com.aabu.finalproject.service.favorite;

import com.aabu.finalproject.config.AuthService;
import com.aabu.finalproject.model.dto.FavoriteDto;
import com.aabu.finalproject.model.dto.request.FavoriteReq;
import com.aabu.finalproject.model.entity.FavoriteEntity;
import com.aabu.finalproject.model.entity.ProductEntity;
import com.aabu.finalproject.model.entity.UserEntity;
import com.aabu.finalproject.model.mapper.FavoriteMapper;
import com.aabu.finalproject.model.repository.FavoriteRepository;
import com.aabu.finalproject.model.repository.ProductRepository;
import com.aabu.finalproject.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService{

     private final FavoriteMapper favoriteMapper;
     private final FavoriteRepository repository;
     private final AuthService authService;
     private final UserRepository userRepository;
     private final ProductRepository productRepository;

    @Override
    public FavoriteDto addToFavorite(FavoriteReq req) {

        Integer userId= authService.getCurrentUserId().intValue();
        UserEntity userEntity= userRepository.getById(userId.longValue());

        ProductEntity productEntity = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean existFavorite=repository.existsByUserEntity_IdAndProductEntity_Id(userId,req.getProductId());
        if(existFavorite){
            throw new RuntimeException("Product already in favorite list ");
        }

        FavoriteEntity favorite = repository.save(
                        FavoriteEntity.builder()
                                .userEntity(userEntity).
                                productEntity(productEntity)
                                .build() );

        return favoriteMapper.toDto(favorite);
    }

    @Override
    public void removeFromFavorite(FavoriteReq req) {
        Integer userId= authService.getCurrentUserId().intValue();

        FavoriteEntity existFavorite=repository.findByUserEntity_IdAndProductEntity_Id(userId,req.getProductId());
                if(existFavorite==null){
                    throw new RuntimeException("product not in favorite ");
                }

                repository.deleteByUserEntity_IdAndProductEntity_Id( userId.longValue() ,req.getProductId());
    }

    @Override
    public List<FavoriteDto> getAllFavorite() {
        Integer userId= authService.getCurrentUserId().intValue();

        return repository.getAllByUserEntity_Id(userId.longValue())
                .stream()
                .map(favoriteMapper::toDto)
                .toList();
    }

    @Override
    public void clearFavorite() {
        Integer userId= authService.getCurrentUserId().intValue();
        repository.removeByUserEntity_Id(userId.longValue());
    }

}
