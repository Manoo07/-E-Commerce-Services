package com.ecommerce.amazon.security.jwt;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(
                jwtSecret.getBytes()
        );
    }

    public String generateToken(String email){
         Date now = new Date();

         Date expiryDate = new Date(
                 now.getTime() + jwtExpiration
         );

         return Jwts.builder()
                 .subject(email)
                 .issuedAt(now)
                 .expiration(expiryDate)
                 .signWith(key, SignatureAlgorithm.HS256)
                 .compact();
    }

    public boolean validateToken(String token){
        try{

            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex){
            return false;
        }
    }


}
