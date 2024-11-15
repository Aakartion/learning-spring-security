package edu.miu.cs489.securitydemo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jjt.secreteKey}")
    public String SECRETE_KEY;

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .issuedAt(new Date())
                .issuer("edu.miu")
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .subject(userDetails.getUsername())
                .claim("authorities", populateAuthorities(userDetails))
                .signWith(sigInKey())
                .compact();
    }

    private SecretKey sigInKey() {
//        Hash based message authentication code
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRETE_KEY));
    }

    public String populateAuthorities(UserDetails userDetails){
        return userDetails.getAuthorities()
                .stream()
                .map(authority-> authority.getAuthority())
                .collect(Collectors.joining(","));
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(sigInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
