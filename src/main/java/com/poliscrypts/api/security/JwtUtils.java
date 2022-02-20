package com.poliscrypts.api.security;

import com.poliscrypts.api.exception.UserException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.audience}")
    private String TOKEN_AUD;

    @Value("${jwt.expiration}")
    private String TOKEN_EXP;

    @Value("${jwt.prefix}")
    private String TOKEN_PREFIX;

    @Value("${jwt.iss}")
    private String TOKEN_ISS;

    CacheManager cm = CacheManager.getInstance();
    Cache cache = cm.getCache("blacklistToken");

    public String generateToken(Authentication auth) {
        User userPrincipal = (User) auth.getPrincipal();
        Claims claims = Jwts.claims()
                .setSubject(userPrincipal.getUsername())
                .setIssuer(TOKEN_ISS)
                .setAudience(TOKEN_AUD)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + Long.valueOf(TOKEN_EXP)));
        claims.put("authorities", userPrincipal.getAuthorities());
        String token = TOKEN_PREFIX + Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .compact();
        return token;
    }

    public boolean validateJwtToken(String token) {
        try {
            isNotBlacklistedToken(TOKEN_PREFIX+token);
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new UserException(e.getMessage(), 401);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new UserException(e.getMessage(), 401);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new UserException(e.getMessage(), 401);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new UserException(e.getMessage(), 401);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new UserException(e.getMessage(), 401);
        }
    }

    public Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }

    public String getUsernameFromToken(String token) {
        try {
            return getClaimsFromToken(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public void invalidateToken(String token){
        cache.put(new Element(token, token));
    }

    public boolean isNotBlacklistedToken(String token) {
        if(!cache.isKeyInCache(token)){
            return true;
        }else {
            log.error("JWT token is blacklisted: {}", token);
            throw new UserException("Invalid Token", 401);
        }
    }
}
