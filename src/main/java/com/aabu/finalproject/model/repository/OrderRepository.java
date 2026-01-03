package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

    OrderEntity getByUserEntity_Id(Long userEntityId);

    void deleteByIdAndUserEntity_Id(Integer id, Long userEntityId);

}
