package com.example.ankan.BankingManagement.controller;

import com.example.ankan.BankingManagement.Dao.RoleDao;
import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.RegisteredUsers;
import com.example.ankan.BankingManagement.Entity.Roles;
import com.example.ankan.BankingManagement.service.RegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
@Tag(name = "User Controller")
public class RegisterUserController {

    @Autowired
    private UserDao dao;

    @Autowired
    private RegisterUserService registerUserService;

    @PostMapping("/register/user")
    @Operation(summary = "This endpoint is for registering new user account")
    public ResponseEntity<String> registerUser(String name, String email, String password){
        return this.registerUserService.registerDefaultUser(name, email, password);
    }

    @PostMapping("/register/admin")
    @Operation(summary = "This endpoint is for registering new admin account")
    public ResponseEntity<String> registerAdmin(String name, String email, String password){
        return this.registerUserService.registerDefaultAdmin(name, email, password);
    }

}

