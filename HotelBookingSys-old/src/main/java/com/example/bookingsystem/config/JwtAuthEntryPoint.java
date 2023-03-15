package com.example.bookingsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        StringBuilder stringBuilder = new StringBuilder();

        if (authException.getCause()!=null){
            stringBuilder.append(authException.getCause().toString()).append(" ").append(authException.getMessage());
        } else {
            stringBuilder.append(authException.getMessage());
        }

        byte[] body=new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", stringBuilder));
        response.getOutputStream().write(body);
    }
}
