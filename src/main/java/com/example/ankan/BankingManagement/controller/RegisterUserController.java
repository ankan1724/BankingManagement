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
    public ResponseEntity<String> addNewRoleToExistingUsers(@RequestParam String email,@RequestParam String new_role) {
        RegisteredUsers users = dao.findByEmail(email);
        Set<Roles> newRoleSet = roleDao.findByRole(new_role);
        if (!newRoleSet.isEmpty()) {
            Set<Roles> existingRoleSet = users.getRoles();
            existingRoleSet.addAll(newRoleSet);
            users.setRoles(newRoleSet);
            dao.save(users);
            return ResponseEntity.ok().body("new role " + new_role + " has been added to the user " + email);
        }
        else return ResponseEntity.ok().body("no such role exist");
    }
    @PutMapping("/modify/role")
    public ResponseEntity<String> modifyRoleForExistingUsers(@RequestParam String email,@RequestParam String new_role) {
        RegisteredUsers users=dao.findByEmail(email);
        Set<Roles> newRole= roleDao.findByRole(new_role);
        if(!newRole.isEmpty()){
            users.setRoles(newRole);
            dao.save(users);
            return ResponseEntity.ok().body("role modified for user "+email);
        }
        else return ResponseEntity.ok().body("no such role exist");
    }
}

