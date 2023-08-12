package com.example.ankan.BankingManagement.JwtUtils;

import com.example.ankan.BankingManagement.Dao.UserDao;
import com.example.ankan.BankingManagement.Entity.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class TokenGenerator {
    @Value("${jwt_signature_secret}")
    private String SECRET;

    @Value("${jwt_token_validity_time}")
    private long TOKEN_VALIDITY;
    @Autowired
    private UserDao userRepo;

    public static Roles extractRoleFromRoleSet(Set<Roles> rolesSet) {
        Iterator<Roles> iterator = rolesSet.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer("Ankan_tech_microservices")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        Set<Roles> rolesSet=userRepo.findByEmail(email).getRoles();
        claims.put("username", email);
        claims.put("roles", Objects.requireNonNull(extractRoleFromRoleSet(rolesSet)).getRole());
        String sub = userRepo.findByEmail(email).getName();
        return createToken(claims, sub);
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return (String) getAllClaims(token).get("username");
    }

    public String getRolesFromToken(String token) {
        return (String) getAllClaims(token).get("roles");
    }

    public Date getExpirationOfToken(String token) {
        return getAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        Date validity = getExpirationOfToken(token);
        return validity.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && (!isTokenExpired(token)));
    }
}
