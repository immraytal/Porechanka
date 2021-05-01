package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonRootName("updatePassword")
public class UpdatePasswordDto {

    @Size(min = 6, max = 32)
    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
