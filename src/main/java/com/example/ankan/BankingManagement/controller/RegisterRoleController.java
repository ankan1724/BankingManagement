package com.example.ankan.BankingManagement.controller;

import com.example.ankan.BankingManagement.Dao.RoleDao;
import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.RegisteredUsers;
import com.example.ankan.BankingManagement.Entity.Roles;
import com.example.ankan.BankingManagement.service.RegisterRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Tag(name = "User's Role Controller")
public class RegisterRoleController {

    @Autowired
    private RegisterRoleService registerRoleService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao dao;

    @PostMapping("/add/roles")
    @Operation(summary = "This endpoint is for adding new role with role_id, role_name, role description for users")
    public ResponseEntity<String> addRoles(@RequestParam String role, @RequestParam String description) {
        return this.registerRoleService.addRoles(role, description);
    }

    @PutMapping("/assign/newRole")
    @Operation(summary = "This endpoint is for assigning new role to an existing user using user's email.")
    public ResponseEntity<String> addNewRoleToExistingUsers(@RequestParam String email, @RequestParam String new_role) {
        RegisteredUsers users = dao.findByEmail(email);
        Set<Roles> newRoleSet = roleDao.findByRole(new_role);
        if (!newRoleSet.isEmpty()) {
            Set<Roles> existingRoleSet = users.getRoles();
            existingRoleSet.addAll(newRoleSet);
            users.setRoles(newRoleSet);
            dao.save(users);
            return ResponseEntity.ok().body("new role " + new_role + " has been added to the user " + email);
        } else return ResponseEntity.ok().body("no such role exist");
    }

    @PutMapping("/modify/role")
    @Operation(summary = "This endpoint is for modifying existing role for an existing user using user's email.")
    public ResponseEntity<String> modifyRoleForExistingUsers(@RequestParam String email, @RequestParam String new_role) {
        RegisteredUsers users = dao.findByEmail(email);
        Set<Roles> newRole = roleDao.findByRole(new_role);
        if (!newRole.isEmpty()) {
            users.setRoles(newRole);
            dao.save(users);
            return ResponseEntity.ok().body("role modified for user " + email);
        } else return ResponseEntity.ok().body("no such role exist");
    }

}
