package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Integer> {

    List<OrderItemEntity> getAllByOrderEntity_Id(Integer orderEntityId);

}
