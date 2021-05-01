package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.controller.config.filters.AuthenticationFilter;
import com.kisel.Porechanka.controller.config.filters.JwtCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilter(authenticationFilter())
                .addFilter(jwtCheckFilter(userDetailsService));
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/adverts/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/sign-up").permitAll()
                .antMatchers("/forgot").permitAll()
                .antMatchers(HttpMethod.GET, "/reset-pass").permitAll()
                .antMatchers(HttpMethod.POST, "/reset-pass").authenticated()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/api/**").permitAll();
              //  .anyRequest().access("hasAnyRole('USER', 'ADMIN')");

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

    }


    @Override
    protected AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public JwtCheckFilter jwtCheckFilter(UserDetailsService userDetailsService) {
        return new JwtCheckFilter(authenticationManager(), userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(authenticationManager());
    }
}
