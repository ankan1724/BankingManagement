package com.example.ankan.BankingManagement.service;

import com.example.ankan.BankingManagement.Dao.RoleDao;
import com.example.ankan.BankingManagement.Entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterRoleService {

    @Autowired
    private RoleDao dao;

    public ResponseEntity<String> addRoles(String role, String description){
        Roles roles=new Roles(role,description);
        dao.save(roles);
        return ResponseEntity.ok().body(role+" role added");
    }

}
