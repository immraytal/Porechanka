package com.kisel.Porechanka.controller.config.security;

import com.kisel.Porechanka.api.service.UserService;
import com.kisel.Porechanka.model.dto.UserModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    private UserService userService;

    public UserModelDto getCurrentUser() {
        return userService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
