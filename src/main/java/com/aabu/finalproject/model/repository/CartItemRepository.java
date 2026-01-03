package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Integer> {

    Optional<CartItemEntity> findByIdAndCartEntity_UserEntity_Id(Long cartItemId, Long userId);

    List<CartItemEntity> getAllByCartEntity_Id(Integer cartEntityId);
}
