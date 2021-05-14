package com.kisel.Porechanka.controller.config.filters;

import com.kisel.Porechanka.util.MyException;
import com.kisel.Porechanka.util.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.persistence.NoResultException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@PropertySource("classpath:tokenKeys.properties")
public class JwtCheckFilter extends BasicAuthenticationFilter {

    private static final Logger LOG = Logger.getLogger(JwtCheckFilter.class);
    @Value("secret")
    private String authSecret;
    @Autowired
    private TokenUtil tokenUtil;
    private UserDetailsService userDetailsService;

    public JwtCheckFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        String jwtToken = request.getHeader("Authorization");
        UserDetails userDetails;
        if (jwtToken == null || jwtToken.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        try {
            userDetails = userDetailsService.loadUserByUsername(tokenUtil.getSubjectFromToken(getToken(jwtToken), authSecret));
            if (!userDetails.isEnabled() || !userDetails.isAccountNonLocked()) {
                LOG.error("Banned user " + userDetails.getUsername() + " try to auth");
                throw new MyException("User is banned");
            }
        } catch (NoResultException e) {
            LOG.error("User not found by login", e);
            response.sendError(401, "User not found in database");
            return;
        } catch (MyException e) {
            response.sendError(403, e.getMessage());
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities())
        );
        chain.doFilter(request, response);
    }

    private String getToken(String token) {
        return token;
    }

}
