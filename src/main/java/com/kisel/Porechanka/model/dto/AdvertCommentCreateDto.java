package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;

@JsonRootName("advertCommentCreateDto")
public class AdvertCommentCreateDto {

    private long parentCommentId;

    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
