package com.example.ankan.BankingManagement.controller;

import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.AuthRequest;
import com.example.ankan.BankingManagement.Entity.AuthResponse;
import com.example.ankan.BankingManagement.Entity.CheckTokenResponse;
import com.example.ankan.BankingManagement.JwtUtils.TokenGenerator;
import com.example.ankan.BankingManagement.config.CustomUserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Token Controller")
public class TokenController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenGenerator utils;

    @Autowired
    UserDao dao;
    @Autowired
    CustomUserDetailService customUserDetailService;

    @GetMapping("/get/token")
    @Operation(summary = "This endpoint is for fetching access JWT token using user email and password")
    public AuthResponse getToken(@RequestBody AuthRequest request) throws AuthenticationException {
        Map<String, Object> results = new HashMap<>();
        AuthResponse response=new AuthResponse();
        Authentication auth = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (auth.isAuthenticated()) {
            String token=utils.generateToken(request.getEmail());
            response.setToken(token);
            response.setExpires_in(utils.getExpirationOfToken(token).getTime()-System.currentTimeMillis());
            response.setType("Bearer");
            response.setUsers(utils.getUsernameFromToken(token));
            response.setRole(utils.getRolesFromToken(token));

        }
        return response;
    }

    @GetMapping(value = "/check_token")
    private CheckTokenResponse checkToken(@RequestParam String token) throws Exception {

        if (token == null) {
            throw new Exception("token not present");
        }
        String username = utils.getUsernameFromToken(token);
        String roles = utils.getRolesFromToken(token);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        Boolean isTokenValid = utils.validateToken(token, userDetails);
        boolean isTokenNonExpired = utils.isTokenExpired(token);
        return new CheckTokenResponse(username,roles,isTokenValid,!isTokenNonExpired);
    }

}
