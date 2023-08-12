package com.example.ankan.BankingManagement.controller;

import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.AuthRequest;
import com.example.ankan.BankingManagement.Entity.AuthResponse;
import com.example.ankan.BankingManagement.JwtUtils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenGenerator utils;

    @Autowired
    UserDao dao;

    @GetMapping("/get/token")
    public AuthResponse getToken(@RequestBody AuthRequest request) throws AuthenticationException {
        Map<String, Object> results = new HashMap<>();
        Authentication auth = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (auth.isAuthenticated()) {
            String token=utils.generateToken(request.getEmail());
            AuthResponse response=new AuthResponse();
            response.setToken(token);
            response.setExpires_in(utils.getExpirationOfToken(token).getTime()-System.currentTimeMillis());
            response.setType("Bearer");
            response.setUsers(utils.getUsernameFromToken(token));
            response.setRole(utils.getRolesFromToken(token));
            return response;
        }
        else throw new UsernameNotFoundException("Invalid user");
    }
}
