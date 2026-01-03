package com.aabu.finalproject.service.category.impl;

import com.aabu.finalproject.model.dto.CategoryDto;
import com.aabu.finalproject.model.dto.request.CategoryReq;
import com.aabu.finalproject.model.entity.CategoryEntity;
import com.aabu.finalproject.model.exception.NotFoundException;
import com.aabu.finalproject.model.mapper.CategoryMapper;
import com.aabu.finalproject.model.repository.CategoryRepository;
import com.aabu.finalproject.service.category.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    @Override
    public CategoryDto create(CategoryReq req) {

        CategoryEntity entity= CategoryEntity.builder()
                .name(req.getName())
                .build();
        repository.save(entity);

        return mapper.toDto(entity);
    }

    @Override
    public CategoryDto update(int id , CategoryReq req) {

        CategoryEntity entity=repository.getById(id);

        repository.findById(id).orElseThrow(()->new NotFoundException("USER_NOT_FOUND", "User not found in database"));

        entity.setName(req.getName());
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public boolean delete(Integer id) {

        CategoryEntity entity=repository.getById(id);

        repository.findById(id).orElseThrow(()->new NotFoundException("USER_NOT_FOUND", "User not found in database"));
        repository.deleteById(entity.getId());
        return true;
    }

    @Override
    public List<CategoryDto> getAll() {
        return  mapper.toDto(repository.findAll());
    }
}
