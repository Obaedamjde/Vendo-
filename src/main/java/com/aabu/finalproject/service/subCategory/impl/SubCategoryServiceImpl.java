package com.aabu.finalproject.service.subCategory.impl;

import com.aabu.finalproject.model.dto.SubCategoryDto;
import com.aabu.finalproject.model.dto.request.SubCategoryReq;
import com.aabu.finalproject.model.entity.CategoryEntity;
import com.aabu.finalproject.model.entity.SubCategoryEntity;
import com.aabu.finalproject.model.exception.NotFoundException;
import com.aabu.finalproject.model.mapper.SubCategoryMapper;
import com.aabu.finalproject.model.repository.CategoryRepository;
import com.aabu.finalproject.model.repository.SubCategoryRepository;
import com.aabu.finalproject.service.subCategory.SubCategoryservice;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryservice {

    private final SubCategoryMapper mapper;
    private final SubCategoryRepository Subrepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SubCategoryDto create(SubCategoryReq req) {


        CategoryEntity category = categoryRepository.findById(req.getCategory_id()).orElseThrow(()->new NotFoundException("Category_NOT_FOUND", "Category not found in database"));


        SubCategoryEntity entity =new SubCategoryEntity();
        entity.setCategoryEntity(category);
        entity.setName(req.getName());


        SubCategoryDto dto = mapper.toDto(entity);
        dto.setCategory_id(req.getCategory_id());
        Subrepository.save(entity);
        return dto;
    }

    @Override
    public SubCategoryDto update( Integer id , SubCategoryReq req) {

        SubCategoryEntity entity = Subrepository.findById(id).orElseThrow(()->new NotFoundException("Sub_Category_NOT_FOUND", "SubCategory not found in database"));
        CategoryEntity categoryEntity= categoryRepository.findById(req.getCategory_id()).orElseThrow(()->new NotFoundException("Category_NOT_FOUND", "Category not found in database"));

        entity.setCategoryEntity(categoryEntity);
        entity.setName(req.getName());

        SubCategoryDto dto =mapper.toDto(entity);
        Subrepository.save(entity);
        return dto;

    }

    @Override
    public boolean delete(Integer id) {
        Subrepository.findById(id).orElseThrow(()->new NotFoundException("Sub_Category_NOT_FOUND", "SubCategory not found in database"));
        Subrepository.deleteById(id);
        return true;
    }

    @Override
    public List<SubCategoryDto> getAllByCategory(Integer categoryId) {
        return mapper.toDto(Subrepository.getAllByCategoryEntity_Id(categoryId));
    }
}
