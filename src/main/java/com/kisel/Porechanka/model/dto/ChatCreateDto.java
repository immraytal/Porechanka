package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonRootName("chatCreateDto")
public class ChatCreateDto {

    @NotNull
    @Min(value = 1L)
    private long targetUserId;

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

}
