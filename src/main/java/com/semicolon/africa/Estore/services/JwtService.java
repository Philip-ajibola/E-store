package com.semicolon.africa.Estore.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private  String  SECRET_KEY = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
    private final long EXPIRATION_TIME = 864_000_000; // 10 days
    private final long refreshExpiration = 604800000;

    public String extractUsername(String username){
        return extractClaims(username, Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function< Claims,T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getKey(SECRET_KEY)).build().parseClaimsJws(token).getBody();
    }
    private SecretKey getKey(String token){
        byte[] bytes = Decoders.BASE64.decode(token);
        return Keys.hmacShaKeyFor(bytes);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails,EXPIRATION_TIME);
    }

    private String generateToken(Map<String,Object> claims,UserDetails userDetails,Long EXPIRATION_TIME){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = authorities.stream()
                .map((grantedAuthority)->grantedAuthority.getAuthority())
                .collect(Collectors.toList());
        return Jwts.builder()
               .setClaims(claims)
               .setSubject(userDetails.getUsername())
                .claim("role",roles)
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(getKey(SECRET_KEY), SignatureAlgorithm.HS256)
               .compact();
    }
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<>(), userDetails, refreshExpiration);
    }    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

}
