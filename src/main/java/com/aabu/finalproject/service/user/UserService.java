package com.aabu.finalproject.service.user;

import com.aabu.finalproject.model.dto.UserDto;
import com.aabu.finalproject.model.dto.request.UserReqLogIn;
import com.aabu.finalproject.model.dto.request.UserReqSignUp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {

    UserDto signUpUser(UserReqSignUp req);

    UserDto LogInUser(UserReqLogIn reqLogIn,
     HttpServletRequest httpServletRequest ,
                      HttpServletResponse httpServletResponse);

    List<UserDto>getAllUsers();
}
