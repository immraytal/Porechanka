package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonRootName("payment")
public class AdvertPaymentDto {

    private Long id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate paidForDate;

    private AdvertDto advert;

    private String verifyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPaidForDate() {
        return paidForDate;
    }

    public void setPaidForDate(LocalDate paidForDate) {
        this.paidForDate = paidForDate;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public AdvertDto getAdvert() {
        return advert;
    }

    public void setAdvert(AdvertDto advert) {
        this.advert = advert;
    }

    @Override
    public String toString() {
        return "AdvertPaymentDto{" +
                "id=" + id +
                ", paidForDate=" + paidForDate +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
