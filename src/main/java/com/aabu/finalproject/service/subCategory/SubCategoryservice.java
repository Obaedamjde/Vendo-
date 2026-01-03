package com.aabu.finalproject.service.subCategory;


import com.aabu.finalproject.model.dto.SubCategoryDto;
import com.aabu.finalproject.model.dto.request.SubCategoryReq;

import java.util.List;

public interface SubCategoryservice {

    SubCategoryDto create(SubCategoryReq req);

    SubCategoryDto update(Integer id ,SubCategoryReq req);

    boolean delete(Integer id);

    List<SubCategoryDto>getAllByCategory(Integer categoryId);
}
