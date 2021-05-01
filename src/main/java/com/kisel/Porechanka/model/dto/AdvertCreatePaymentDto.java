package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AdvertCreatePaymentDto {

    @Future
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate paidForDate;

    public LocalDate getPaidForDate() {
        return paidForDate;
    }

    public void setPaidForDate(LocalDate paidForDate) {
        this.paidForDate = paidForDate;
    }
}
