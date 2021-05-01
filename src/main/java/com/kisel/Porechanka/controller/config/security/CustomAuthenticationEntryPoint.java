package com.kisel.Porechanka.controller.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kisel.Porechanka.controller.config.security.exception.JsonExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JsonExceptionResponse jsonExceptionResponse = new JsonExceptionResponse(401, "You are not authenticated");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream outputStream = httpServletResponse.getOutputStream();
        objectMapper.writeValue(outputStream, jsonExceptionResponse);
        outputStream.flush();
    }
}
