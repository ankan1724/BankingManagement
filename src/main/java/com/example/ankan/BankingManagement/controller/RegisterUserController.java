package com.example.ankan.BankingManagement.controller;

import com.example.ankan.BankingManagement.Dao.RoleDao;
import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.RegisteredUsers;
import com.example.ankan.BankingManagement.Entity.Roles;
import com.example.ankan.BankingManagement.service.RegisterUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class RegisterUserController {

    @Autowired
    private UserDao dao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RegisterUserService registerUserService;

    @PostMapping("/register/user")
    public ResponseEntity<String> registerUser(String name, String email, String password){
        return this.registerUserService.registerDefaultUser(name, email, password);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(String name, String email, String password){
        return this.registerUserService.registerDefaultAdmin(name, email, password);
    }

    @PutMapping("/assign/newRole")
    public void addNewRoleToExistingUsers(@RequestParam String email,@RequestParam String new_role) {
        RegisteredUsers users=dao.findByEmail(email);
        Set<Roles> rolesSet= roleDao.findByRole(new_role);
        Set<Roles> newRoleSet=users.getRoles();
        newRoleSet.addAll(rolesSet);
        users.setRoles(newRoleSet);
        dao.save(users);
        log.info(users.getRoles().toString());
    }
}

