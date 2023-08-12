package com.example.ankan.BankingManagement.Dao;

import com.example.ankan.BankingManagement.Entity.RegisteredUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<RegisteredUsers, Integer> {
    RegisteredUsers findByEmail(String email);
}
