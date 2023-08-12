package com.example.ankan.BankingManagement.config;

import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.RegisteredUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao dao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisteredUsers users=this.dao.findByEmail(username);
        if(users==null){
            throw new UsernameNotFoundException("invalid username/password");
        }
        return new User(users.getUsername(),users.getPassword(),users.getIsEnabled(),
                true,true,true,users.getAuthorities());
    }
}
