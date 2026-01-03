package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.dto.FavoriteDto;
import com.aabu.finalproject.model.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    FavoriteEntity findByUserEntity_IdAndProductEntity_Id(Integer userId, Integer productId);

    boolean existsByUserEntity_IdAndProductEntity_Id(Integer userId, Integer productId);

    Optional< FavoriteEntity> findByUserEntity_Id(Long id);

    void deleteByUserEntity_IdAndProductEntity_Id(Long userID, Integer productId);

    void removeByUserEntity_Id(Long userEntityId); // clear favorite

    List<FavoriteEntity> getAllByUserEntity_Id(Long userId);
}
