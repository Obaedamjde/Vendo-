package com.aabu.finalproject.controller.subCategory;


import com.aabu.finalproject.model.dto.SubCategoryDto;
import com.aabu.finalproject.model.dto.request.SubCategoryReq;
import com.aabu.finalproject.model.mapper.SubCategoryMapper;
import com.aabu.finalproject.service.subCategory.impl.SubCategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subCategory")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SubcategoryController {

    private final SubCategoryServiceImpl service;
    private final SubCategoryMapper mapper;

    @PostMapping("")
    public SubCategoryDto createSubCategory(@Valid @RequestBody SubCategoryReq req){
        return service.create(req);
    }

    @PutMapping("/update/{id}")
    public SubCategoryDto updateSubCategory(@Valid  @PathVariable Integer id  ,  @Valid @RequestBody SubCategoryReq req){
        return service.update(id,req);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteSubCategory(@Valid  @PathVariable Integer id ){
        return service.delete(id);
    }

    @GetMapping("/GetAllByCategory/{id}")
    public List<SubCategoryDto> GetAllByCategory(@Valid  @PathVariable Integer id ){
        return service.getAllByCategory(id);
    }




}
