package com.example.cognito.mercado.security.controller;

import com.example.cognito.mercado.dto.ResponseMessageDto;
import com.example.cognito.mercado.security.dto.SignInDto;
import com.example.cognito.mercado.security.dto.SignupDto;
import com.example.cognito.mercado.security.entity.User;
import com.example.cognito.mercado.security.service.UserDetailsServiceImpl;
import com.example.cognito.mercado.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessageDto> signupUser(@RequestBody @Valid SignupDto signupDto, BindingResult bindingResult){

        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
        }
        userService.signup(signupDto);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto("Se ha registrado el usuario"), HttpStatus.OK);

    }


    @PostMapping("/sign-in")
    public ResponseEntity<ResponseMessageDto> signInUser(@RequestBody @Valid SignInDto signinDto, BindingResult bindingResult){

        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
        }
        String accessToken = userService.siginUser(signinDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(signinDto.getEmail());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<ResponseMessageDto>(new ResponseMessageDto(accessToken), HttpStatus.OK);

    }

    @GetMapping("/user-details")
    public Optional<User> getUserDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userService.getUserByEmail(email);
    }



}
