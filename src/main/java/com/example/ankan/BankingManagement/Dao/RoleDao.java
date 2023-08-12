package com.example.ankan.BankingManagement.Dao;

import com.example.ankan.BankingManagement.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDao extends JpaRepository<Roles, Integer> {
    Set<Roles> findByRole(String role);
}
