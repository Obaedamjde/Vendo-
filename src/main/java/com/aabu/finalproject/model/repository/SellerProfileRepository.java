package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.SellerProfileEntity;
import com.aabu.finalproject.model.enums.SellerProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerProfileRepository extends JpaRepository<SellerProfileEntity, Integer> {

    Optional<SellerProfileEntity> findByUserEntity_IdAndStatus(Long user_id , SellerProfileStatus status);

    Optional<SellerProfileEntity> deleteByUserEntity_IdAndStatus(Long userId ,  SellerProfileStatus status);

    boolean existsByUserEntity_Id(Long userId);

    Optional<SellerProfileEntity> findById(int id);

}