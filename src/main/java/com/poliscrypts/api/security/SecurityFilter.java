package com.poliscrypts.api.security;

import com.poliscrypts.api.exception.UserException;
import com.poliscrypts.api.utility.CustomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = httpRequest.getHeader("Authorization");
            if (authorization != null) {
                try {
                    String token = authorization.split(" ")[1];
                    jwtUtils.validateJwtToken(token);
                    String username = jwtUtils.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }catch (UserException e) {
                    logger.warn("Failure authorization: "+ e.getMessage());
                    CustomHelper.populateJsonResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.warn("Failure authentication: "+ e.getMessage());
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
