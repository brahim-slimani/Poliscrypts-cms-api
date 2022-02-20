package com.poliscrypts.api.security;

import com.poliscrypts.api.exception.TokenException;
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
    Cache cache = cm.getCache("tokenBlacklist");

    /**
     * this method generates JWT token
     * @param auth success authentication returned by Authentication Manager of spring security
     * @return bearer token
     */
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

    /**
     * validate the JWT token by checking out that it's not blacklisted, verify the signature, expiration,..
     * @param token to be validated
     * @return true if it is valid JWT token
     */
    public boolean validateJwtToken(String token) {
        try {
            isNotBlacklistedToken(TOKEN_PREFIX+token);
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new TokenException(e.getMessage(), 401);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new TokenException(e.getMessage(), 401);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new TokenException(e.getMessage(), 401);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new TokenException(e.getMessage(), 401);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new TokenException(e.getMessage(), 401);
        }
    }

    /**
     * parse the data claims injected in JWT token
     * @param token bearer token to be parsed
     * @return Data claims
     */
    public Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }

    /**
     * retrieve the username (subject) data issued in JWT token
     * @param token token to be parsed
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getBody().getSubject();
    }

    /**
     * this method blacklist the token by putting it in the cache
     * the period of the cache is equal to the token expiry date
     * @param token token to be blacklisted
     */
    public void invalidateToken(String token){
        cache.put(new Element(token, token));
    }

    /**
     * verify if the token is not figured in the blacklist cache
     * @param token token to be checked
     * @return true if it does not exist in the cache
     */
    public boolean isNotBlacklistedToken(String token) {
        if(!cache.isKeyInCache(token)){
            return true;
        }else {
            log.error("JWT token is blacklisted: {}", token);
            throw new TokenException("Invalid Token", 401);
        }
    }
}
