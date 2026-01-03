package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.CategoryEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {

    @Override
    <S extends CategoryEntity> Page<S> findAll(Example<S> example, Pageable pageable);

    CategoryEntity getByName(String name);
    CategoryEntity getById (Integer id);
    void deleteById(Integer id);

    @Override
    Optional<CategoryEntity> findById(Integer integer);

}
