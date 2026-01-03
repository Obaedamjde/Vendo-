package com.aabu.finalproject.service.user.impl;

import com.aabu.finalproject.model.dto.UserDto;
import com.aabu.finalproject.model.dto.request.UserReqLogIn;
import com.aabu.finalproject.model.dto.request.UserReqSignUp;
import com.aabu.finalproject.model.entity.UserEntity;
import com.aabu.finalproject.model.mapper.UserMapper;
import com.aabu.finalproject.model.repository.UserRepository;
import com.aabu.finalproject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository ;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserDto signUpUser(UserReqSignUp req) {

        UserEntity userEntity= userMapper.toUserEntity(req);
        userEntity.setPasswordHash(passwordEncoder.encode(req.getPasswordHash()));
        userRepository.save(userEntity);

        UserDto userDto=userMapper.toUserDto(userEntity);
        return userDto;
    }

    @Override
    public UserDto LogInUser(UserReqLogIn req ,
                             HttpServletRequest httpServletRequest ,
                             HttpServletResponse httpServletResponse){


        Authentication authReq=authenticationManager .authenticate(
                new UsernamePasswordAuthenticationToken(req.getPhone(),req.getPasswordHash())); // like bridge

        httpServletRequest.getSession(true);

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authReq);
        SecurityContextHolder.setContext(context);

        var repo = new org.springframework.security.web.context.HttpSessionSecurityContextRepository();
        repo.saveContext(context, httpServletRequest, httpServletResponse);

        UserDetails userDetails = (UserDetails) authReq.getPrincipal();
        var user= userRepository.getByPhone(userDetails.getUsername()).orElseThrow();
        return userMapper.toUserDto(user);

    }

    @Override
    public List<UserDto> getAllUsers() {
        return  userMapper.toDto(userRepository.findAll());
    }


}
