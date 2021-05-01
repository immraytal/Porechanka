package com.kisel.Porechanka.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ForgotEmailDto {

    @Email
    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
