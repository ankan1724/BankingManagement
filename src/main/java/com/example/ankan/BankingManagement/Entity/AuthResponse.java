package com.example.ankan.BankingManagement.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthResponse {

    String users;
    String role;
    String token;
    String type;
    Long expires_in;

}
