package com.kisel.Porechanka.controller.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kisel.Porechanka.controller.config.security.exception.JsonExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        JsonExceptionResponse jsonExceptionResponse = new JsonExceptionResponse(403, "You haven't permissions to view this page");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        OutputStream outputStream = httpServletResponse.getOutputStream();
        objectMapper.writeValue(outputStream, jsonExceptionResponse);
        outputStream.flush();
    }
}
