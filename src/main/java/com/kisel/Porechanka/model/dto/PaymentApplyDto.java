package com.kisel.Porechanka.model.dto;

import javax.validation.constraints.NotBlank;

public class PaymentApplyDto {

    private long advertId;
    @NotBlank
    private String code;

    public long getAdvertId() {
        return advertId;
    }

    public void setAdvertId(long advertId) {
        this.advertId = advertId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
