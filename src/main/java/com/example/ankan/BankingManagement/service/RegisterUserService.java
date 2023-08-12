package com.example.ankan.BankingManagement.service;

import com.example.ankan.BankingManagement.Dao.RoleDao;
import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.RegisteredUsers;
import com.example.ankan.BankingManagement.Entity.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class RegisterUserService {
    @Autowired
    private UserDao dao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder encoder;


    public ResponseEntity<String> registerDefaultUser(String name, String email, String password) {
        if (dao.findByEmail(email) == null) {
            RegisteredUsers users = new RegisteredUsers();
            users.setName(name);
            users.setEmail(email);
            users.setCreatedAt(new Date(System.currentTimeMillis()));
            users.setLastUpdatedAt(new Date(System.currentTimeMillis()));
            users.setPassword(encoder.encode(password));
            users.setIsEnabled(true);
            users.setRoles(roleDao.findByRole("user"));
            dao.save(users);
            return ResponseEntity.ok().body("user successfully created");
        }
        return ResponseEntity.badRequest().body("user already exist. Please login or use different email");
    }

    public ResponseEntity<String> registerDefaultAdmin(String name, String email, String password) {
        if (dao.findByEmail(email) == null) {
            RegisteredUsers users = new RegisteredUsers();
            users.setName(name);
            users.setEmail(email);
            users.setCreatedAt(new Date(System.currentTimeMillis()));
            users.setLastUpdatedAt(new Date(System.currentTimeMillis()));
            users.setPassword(encoder.encode(password));
            users.setIsEnabled(true);
            users.setRoles(roleDao.findByRole("admin"));
            dao.save(users);
            return ResponseEntity.ok().body("user successfully created");
        }
        return ResponseEntity.badRequest().body("user already exist. Please login or use different email");
    }


}
