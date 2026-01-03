package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Integer> {

    Optional<CartEntity> findTopByUserEntity_IdOrderByCreatedAtDesc(Long id);

    CartEntity getByUserEntity_Id(Long userEntityId);
}
