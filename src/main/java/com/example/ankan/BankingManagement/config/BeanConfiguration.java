package com.example.ankan.BankingManagement.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class BeanConfiguration {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${mail.transport.protocol}")
    private String protocol;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttls;

    @Autowired
    private Environment env;


    @Bean
    @Primary
    public MailProperties mailProperties() {
        MailProperties mailProperties = new MailProperties();
        mailProperties.setHost(host);
        mailProperties.setPort(587);

        mailProperties.setUsername(username);
        mailProperties.setPassword(password);

        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", "true");
        return mailProperties;
    }
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(this.env.getProperty("driver-class-name"));
        dataSourceBuilder.url(this.env.getProperty("url"));
        dataSourceBuilder.username(this.env.getProperty("username"));
        dataSourceBuilder.password(this.env.getProperty("password"));
        return dataSourceBuilder.build();
    }

}
