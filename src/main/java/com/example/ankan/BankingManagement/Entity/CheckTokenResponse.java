package com.example.ankan.BankingManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CheckTokenResponse {

    private String username;
    private String roles;
    private Boolean isTokenValid;
    private Boolean isTokenNonExpired;
}
