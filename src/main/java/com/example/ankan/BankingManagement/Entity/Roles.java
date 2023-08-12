package com.example.ankan.BankingManagement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_users_roles_tbl")
@Data
@NoArgsConstructor
public class Roles {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String role;

    private String description;

    public Roles(String role, String description) {
        this.role = role;
        this.description = description;
    }
}
