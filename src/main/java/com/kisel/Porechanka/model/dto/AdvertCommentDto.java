package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonRootName("comment")
public class AdvertCommentDto {

    private Long id;

    private UserModelDto user;

    private Long parentAdvertCommentId;

    private String text;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime createdTime;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModelDto getUser() {
        return user;
    }

    public void setUser(UserModelDto user) {
        this.user = user;
    }

    public Long getParentAdvertCommentId() {
        return parentAdvertCommentId;
    }

    public void setParentAdvertCommentId(Long parentAdvertCommentId) {
        this.parentAdvertCommentId = parentAdvertCommentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "AdvertCommentDto{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", createdTime=" + createdTime +
                ", createdDate=" + createdDate +
                '}';
    }
}
