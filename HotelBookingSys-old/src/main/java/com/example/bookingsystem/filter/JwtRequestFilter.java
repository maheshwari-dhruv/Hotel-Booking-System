package com.example.bookingsystem.filter;

import com.example.bookingsystem.service.impl.CustomUserDetailsServiceImpl;
import com.example.bookingsystem.util.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        String username;
        String requestHeaderToken = request.getHeader("Authorization");

        if (requestHeaderToken != null && requestHeaderToken.startsWith("Bearer")){
            String jwtToken = requestHeaderToken.substring(7);

            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (MalformedJwtException e){
                log.error(e.getMessage());
                throw new MalformedJwtException("Invalid Token");
            }

            UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(username);

            if (SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                log.error("Token is not validated..");
            }
        }

        filterChain.doFilter(request,response);
    }
}
