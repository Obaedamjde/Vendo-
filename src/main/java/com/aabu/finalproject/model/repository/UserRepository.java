package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    Optional<UserEntity> getByPhone(String phone);

    UserEntity getById(Long id);

}
