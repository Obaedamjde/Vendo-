package com.aabu.finalproject.service.seller.impl;

import com.aabu.finalproject.config.AuthService;
import com.aabu.finalproject.model.dto.SellerProfileDto;
import com.aabu.finalproject.model.dto.request.SellerReq;
import com.aabu.finalproject.model.entity.SellerProfileEntity;
import com.aabu.finalproject.model.entity.UserEntity;
import com.aabu.finalproject.model.enums.SellerProfileStatus;
import com.aabu.finalproject.model.exception.ConflictException;
import com.aabu.finalproject.model.exception.NotFoundException;
import com.aabu.finalproject.model.mapper.SellerProfileMapper;
import com.aabu.finalproject.model.repository.SellerProfileRepository;
import com.aabu.finalproject.model.repository.UserRepository;
import com.aabu.finalproject.service.seller.SellerService;
import com.aabu.finalproject.utility.UserSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl  implements SellerService {

    private final SellerProfileMapper sellerProfileMapper;
    private final SellerProfileRepository sellerProfileRepository;
    private final UserRepository userRepository;
    private final AuthService authService;


    @Override
    public  SellerProfileDto CreateShop(SellerReq req) {

        Long userId=authService.getCurrentUserId();

        UserEntity  userEntity =userRepository.getById(userId);

        log.info("CreateShop: currentUserId={}", userId);
        log.info("CreateShop: currentUserEmail={}", userEntity.getEmail());

        if(sellerProfileRepository.existsByUserEntity_Id(userId)){
            throw new ConflictException("SHOP_ALREADY_EXISTS", "User already owns a shop");
        }

        SellerProfileEntity sellerEntity = SellerProfileEntity.builder()
                .userEntity(userEntity)
                .shopName(req.getShopName())
                .instagramHandle(req.getInstagramHandle())
                .instagramUrl(req.getInstagramUrl())
                .bio(req.getBio())
                .status(SellerProfileStatus.PENDING)
                .build();

        sellerProfileRepository.save(sellerEntity);

        SellerProfileDto dto= sellerProfileMapper.toSellerDto(sellerEntity);


        return dto;
    }

    @Override
    //ADMIN
    public boolean AcceptCreateShop(Long user_Id) {

        Optional<SellerProfileEntity> OpsellerEntity= sellerProfileRepository.findByUserEntity_IdAndStatus(user_Id ,SellerProfileStatus.PENDING);

        if(OpsellerEntity.isEmpty())return false;

        OpsellerEntity.get().setStatus(SellerProfileStatus.ACTIVE);

        SellerProfileEntity sellerEntity = OpsellerEntity.get();

        sellerProfileRepository.save(sellerEntity);
        return true;


    }

    @Override
    //ADMIN ONLY
    public boolean RejectCreateShop(Long user_Id) {
        //  add   check if session now ADMIN
        //


        //Deleted
        //String userPhone= userSession.getUserDetailsBySession();

        //Deleted
     //   var userEntity=userRepository.getByPhone(userPhone).orElseThrow();

        Optional<SellerProfileEntity> OpsellerEntity= sellerProfileRepository.findByUserEntity_IdAndStatus(user_Id,SellerProfileStatus.PENDING);

        if(OpsellerEntity.isEmpty())throw  new NotFoundException("USER_NOT_FOUND", "User not found in database");

        if(OpsellerEntity.get().getStatus()==SellerProfileStatus.PENDING){
            sellerProfileRepository.deleteByUserEntity_IdAndStatus(user_Id , SellerProfileStatus.PENDING);
            return true;
        }

        return false;
    }

    @Override
    public List<SellerProfileDto> getAllSellers() {
       return sellerProfileMapper.toDto(sellerProfileRepository.findAll());
    }

}
