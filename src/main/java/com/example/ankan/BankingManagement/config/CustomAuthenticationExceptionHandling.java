package com.example.ankan.BankingManagement.config;

import com.example.ankan.BankingManagement.Entity.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationExceptionHandling implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(401);
        response.setMessage("Unauthorized");
//        LocalDateTime dateTime = LocalDateTime.now();
//        response.setTimestamp(ZonedDateTime.now());
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(out, response);
        out.flush();
    }
}
