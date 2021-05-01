package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kisel.Porechanka.util.validator.Unique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@JsonRootName("userCreate")
public class UserModelCreateDto {

    @Size(min = 4, max = 32)
    @NotBlank
    @Unique(fieldName = "login", serviceQualifier = "userServiceImpl")
    private String login;

    @Size(min = 6, max = 32)
    @NotBlank
    private String password;

    @Size(min = 7, max = 64)
    @NotBlank
    @Unique(fieldName = "email", serviceQualifier = "userServiceImpl")
    private String email;

    @Size(min = 12, max = 12)
    @NotBlank
    @Unique(fieldName = "phoneNumber", serviceQualifier = "userServiceImpl")
    private String phoneNumber;

    @NotBlank
    @Size(min = 2, max = 32)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32)
    private String surName;

    @Past
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birth;

    @Size(max = 7)
    @NotBlank
    private String sex;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
