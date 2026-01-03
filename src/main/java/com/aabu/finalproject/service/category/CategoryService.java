package com.aabu.finalproject.service.category;


import com.aabu.finalproject.model.dto.CategoryDto;
import com.aabu.finalproject.model.dto.request.CategoryReq;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryReq req);
    CategoryDto update( int id , CategoryReq req);
    boolean delete(Integer id);
    List<CategoryDto>getAll();
}
