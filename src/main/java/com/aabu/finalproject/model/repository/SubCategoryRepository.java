package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.dto.SubCategoryDto;
import com.aabu.finalproject.model.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity,Integer> {

    SubCategoryEntity findByCategoryEntity_Id(Integer id);

    void deleteById(Integer id);

    List<SubCategoryEntity> getAllByCategoryEntity_Id(Integer categoryEntityId);
}
