package com.kisel.Porechanka.controller.config.filters;

import com.kisel.Porechanka.model.dto.UserAuthDto;
import com.kisel.Porechanka.util.MyException;
import com.kisel.Porechanka.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@PropertySource("classpath:tokenKeys.properties")
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class);
    @Value("secret")
    private String authSecret;
    @Autowired
    private TokenUtil tokenUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserAuthDto authDto = new ObjectMapper().readValue(request.getInputStream(), UserAuthDto.class);
            AuthenticationManager manager = getAuthenticationManager();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword());
            return manager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            LOG.error(request.toString());
            LOG.error("IOException, can't read user from JSON");
            throw new MyException("Incorrect json", e);
        } catch (LockedException e) {
            LOG.error("Banned user try to auth");
            throw new MyException("Account is banned", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwtToken = tokenUtil.generateJwtToken(((User) authResult.getPrincipal()).getUsername(), authSecret);
        response.setHeader("Authorization", jwtToken);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.DATE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(jwtToken));
        response.flushBuffer();
    }
}

