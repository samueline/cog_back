package com.example.cognito.mercado.security.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.example.cognito.mercado.Blog.exception.BlogException;
import com.example.cognito.mercado.security.dto.SignInDto;
import com.example.cognito.mercado.security.dto.SignupDto;

import com.example.cognito.mercado.security.entity.User;
import com.example.cognito.mercado.security.enums.RoleName;

import com.example.cognito.mercado.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;


    public UserService(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }



    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    public void createUser(User user){
        userRepository.save(user);
    }


    public void signup(SignupDto signupDto){
        AttributeType attributeType = new AttributeType().withName("email").withValue(signupDto.getEmail());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.withClientId(clientId)
                .withPassword(signupDto.getPassword())
                .withUsername(signupDto.getEmail())
                .withUserAttributes(attributeType);
        awsCognitoIdentityProvider.signUp(signUpRequest);

        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setName(signupDto.getName());
        user.setLastName(signupDto.getLastName());
        user.setRole(roleService.getRoleByRoleName(RoleName.ROLE_USER).get());
        createUser(user);
    }

    public String siginUser(SignInDto signInDto){
        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME",signInDto.getEmail());
        authParams.put("PASSWORD",signInDto.getPassword());

        InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(clientId)
                .withAuthParameters(authParams);

        InitiateAuthResult initiateAuthResult = awsCognitoIdentityProvider.initiateAuth(initiateAuthRequest);
        return initiateAuthResult.getAuthenticationResult().getAccessToken();


    }

    public Optional<User> getUserByEmail(String userEmail){
        return userRepository.findByEmail(userEmail);
    }


    @Transactional
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByName(principal.getUsername())
                .orElseThrow(() -> new BlogException("User name not found - " + principal.getUsername()));
    }
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }























}
