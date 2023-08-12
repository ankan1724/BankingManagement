package com.example.ankan.BankingManagement.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class TestController {

    List<String> userAuthorities;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    public void auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userAuthorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    @GetMapping("/test1")
    public ResponseEntity<String> test() {
        auth();
        if (userAuthorities.contains("user") || userAuthorities.contains("admin")) {
            return ResponseEntity.ok().body("accessible by all users");
        }
        return ResponseEntity.ok().body("not accessible");
    }

//    @GetMapping("/test2")
//    public ResponseEntity<String> test2() {
//        auth();
//        if (userAuthorities.contains("admin")) {
//            return ResponseEntity.ok().body("accessible by admins");
//        }
//        return ResponseEntity.ok().body("Test successful. Role based Authorization is working\n. " + "Since this is only accessible by admins" + "users cannot access this");
//    }

    @PostMapping("/send/mail")
    public ResponseEntity<String> sendMail(@RequestParam String email) throws MessagingException {
        auth();
        if (userAuthorities.contains("email_access")||userAuthorities.contains("admin")) {
            sendMail(mailProperties.getUsername(), email);
            return ResponseEntity.ok().body("mail sent");
        }
        return ResponseEntity.ok().body("you don't have sufficient authority to send mail");
    }


    private void sendMail(String fromEmail, String toEmail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
        messageHelper.setSubject("Test");
        messageHelper.setText("This is a testing of email", true);
        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(toEmail);
        javaMailSender.send(message);
    }


}
