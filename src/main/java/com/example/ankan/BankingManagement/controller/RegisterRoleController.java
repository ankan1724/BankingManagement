package com.example.ankan.BankingManagement.controller;

import com.example.ankan.BankingManagement.service.RegisterRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRoleController {

    @Autowired
    private RegisterRoleService registerRoleService;

    @PostMapping("/add/roles")
    public ResponseEntity<String> addRoles(@RequestParam String role,@RequestParam String description){
        return this.registerRoleService.addRoles(role, description);
    }

}
