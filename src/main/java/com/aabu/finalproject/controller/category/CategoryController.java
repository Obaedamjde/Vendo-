package com.aabu.finalproject.controller.category;

import com.aabu.finalproject.model.dto.CategoryDto;
import com.aabu.finalproject.model.dto.request.CategoryReq;
import com.aabu.finalproject.model.mapper.CategoryMapper;
import com.aabu.finalproject.service.category.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {

    private final CategoryMapper mapper;
    private final CategoryServiceImpl service;

    @PostMapping("")
    public CategoryDto createCategory(@Valid @RequestBody CategoryReq req){
         return service.create(req);
    }

    @PatchMapping("/update/{id}")
    public CategoryDto updateCategory(@Valid @PathVariable  Integer id , @RequestBody CategoryReq req ){
        return service.update(id ,req);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCategory(@Valid @PathVariable Integer id){
        return service.delete(id);
    }

    @PostMapping("/getAllCategory")
    public List<CategoryDto> getAllCategory(){
        return service.getAll();
    }

}
