package com.example.ankan.BankingManagement.CustomExceptionHandling;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handlingSecurityException(Exception ex) {
        ProblemDetail errorDetails = null;
        if (ex instanceof BadCredentialsException) {
            errorDetails= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetails.setProperty("reason","Invalid username/password");
        }
        if (ex instanceof SignatureException) {
            errorDetails= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetails.setProperty("reason","Token Mismatch");
        }
        if (ex instanceof ExpiredJwtException) {
            errorDetails= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetails.setProperty("reason","Token already expired");
        }
        return errorDetails;
    }
}
