package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity,Integer> {


    void deleteAllByProductEntity_Id(Integer productEntityId);
}
