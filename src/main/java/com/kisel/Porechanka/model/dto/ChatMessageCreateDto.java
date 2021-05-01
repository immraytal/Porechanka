package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;

@JsonRootName("createChatMessage")
public class ChatMessageCreateDto {

    @NotBlank
    private String textMsg;

    public String getTextMsg() {
        return textMsg;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }
}
