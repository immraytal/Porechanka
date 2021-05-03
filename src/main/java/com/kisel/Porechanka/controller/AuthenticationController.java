package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.api.service.UserService;
import com.kisel.Porechanka.controller.config.security.SecurityUtils;
import com.kisel.Porechanka.model.dto.ForgotEmailDto;
import com.kisel.Porechanka.model.dto.UpdatePasswordDto;
import com.kisel.Porechanka.model.dto.UserModelCreateDto;
import com.kisel.Porechanka.model.dto.UserModelDto;
import com.kisel.Porechanka.util.validator.ValidationUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

@RestController
public class AuthenticationController {

    private static Logger LOG = Logger.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public UserModelDto saveUser(@RequestBody @Validated UserModelCreateDto userModelCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOG.error("Incorrect input data " + ValidationUtils.getValidationErrorsAsString(bindingResult));
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(bindingResult));
        } else {
            LOG.info("Request " + userModelCreateDto);
            UserModelDto user = userService.save(userModelCreateDto);
            LOG.info("User #" + user.getId() + " has been create account");
            return user;
        }
    }

    @PostMapping("/forgot")
    @ResponseStatus(HttpStatus.OK)
    public String forgotPassword(@RequestBody @Validated ForgotEmailDto forgotEmailDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            String email = forgotEmailDto.getEmail();
            LOG.info("Password reset request " + email);
            userService.generateAndSendResetToken(email);
            return "Email was send on " + email;
        } else {
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(bindingResult));
        }
    }

    @GetMapping("/reset-pass")
    public String resetPassword(@RequestParam("token") String token, HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader("Authorization", userService.validateResetToken(token));
        return "Enter new password";
    }

    @PostMapping("/reset-pass")
    @ResponseStatus(HttpStatus.OK)
    public String setNewPassword(@RequestBody @Validated UpdatePasswordDto updatePasswordDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            UserModelDto currentUser = securityUtils.getCurrentUser();
            UserModelCreateDto userModelCreateDto = modelMapper.map(currentUser, UserModelCreateDto.class);
            userModelCreateDto.setPassword(updatePasswordDto.getPassword());
            userService.update(userModelCreateDto, currentUser.getId());
            return "Password changed successful";
        } else {
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(bindingResult));
        }
    }

}
