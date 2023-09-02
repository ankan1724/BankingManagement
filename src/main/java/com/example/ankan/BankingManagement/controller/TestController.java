package com.example.ankan.BankingManagement.controller;

import com.example.ankan.BankingManagement.jobs.JobData;
import com.example.ankan.BankingManagement.jobs.ScheduledJob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@Tag(name = "Test Controller")
public class TestController {

    List<String> userAuthorities;
    @Autowired
    Scheduler scheduler;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;

    public void auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userAuthorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    @GetMapping("/test1")
    @Operation(summary = "This endpoint is for testing purpose. It's accessible by all")
    public ResponseEntity<String> test() throws Exception {

            auth();
            if ((userAuthorities.contains("user") || userAuthorities.contains("admin"))) {
                return ResponseEntity.ok().body("accessible by all users");
            }
            else return ResponseEntity.badRequest().body("Access Denied");
        }


    @GetMapping("/test2")
    @Operation(summary = "This endpoint is for testing purpose. Its accessible by only admins")
    public ResponseEntity<String> test2() {
        auth();
        if (userAuthorities.contains("admin")) {
            return ResponseEntity.ok().body("accessible by admins");
        }
        return ResponseEntity.ok().body("Test successful. Role based Authorization is working\n. " + "Since this is only accessible by admins" + "users cannot access this");
    }

    @PostMapping("/send/mail")
    @Operation(summary = "This endpoint is for sending email. Its accessible by admins and Email_access role holders ")
    public ResponseEntity<String> sendMail(@RequestParam String email) throws MessagingException {
        auth();
        if (userAuthorities.contains("email_access") || userAuthorities.contains("admin")) {
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

    @PostMapping("/schedule")
    public ResponseEntity<String> schedule(@RequestBody JobData data) throws SchedulerException {
        scheduled(data);
        return ResponseEntity.ok().body("Success");
    }


    public void scheduled(JobData data) throws SchedulerException {

        String jobName = data.getJobName();
        String jobGroup = data.getJobGroup();
        int counter = data.getCounter();
        int gapDuration = data.getGapDuration();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(data.getStartTime(), ZoneId.of("Asia/Kolkata"));

        JobDataMap dataMap = new JobDataMap();
        dataMap.put("Task", "this is for fetching no. of registered users");
        JobDetail details = JobBuilder.newJob(ScheduledJob.class).withIdentity(jobName, jobGroup).usingJobData(dataMap).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).startAt(Date.from(zonedDateTime.toInstant())).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(gapDuration).withRepeatCount(counter)).build();

        scheduler.scheduleJob(details, trigger);
    }
}
