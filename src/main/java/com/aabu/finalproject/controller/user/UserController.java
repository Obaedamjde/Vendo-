package com.aabu.finalproject.controller.user;


import com.aabu.finalproject.model.dto.UserDto;
import com.aabu.finalproject.model.dto.request.UserReqLogIn;
import com.aabu.finalproject.model.dto.request.UserReqSignUp;
import com.aabu.finalproject.service.user.impl.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceImp userService;


    @PreAuthorize("permitAll()")
    @PostMapping("")
    public ResponseEntity <UserDto> signUpUser(@RequestBody @Valid UserReqSignUp req){

       UserDto userDto= userService.signUpUser(req);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDto);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity <UserDto> logInUser (@RequestBody @Valid UserReqLogIn req ,
                                               HttpServletRequest httpServletRequest ,
                                               HttpServletResponse httpServletResponse ){

        UserDto userDto= userService.LogInUser(req ,httpServletRequest ,httpServletResponse);
        return ResponseEntity.ok(userDto);

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/getAll")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }


}
