package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.kisel.Porechanka.model.AdvertStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@JsonRootName("advert")
public class AdvertDto {

    private long id;

    private String title;

    private String body;

    private BigDecimal price;

    private AdvertStatus advertStatus;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime openTime;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate openDate;

    private AdvertCategoryDto category;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate closeDate;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime closeTime;

    private UserModelDto seller;

    private UserModelDto buyer;

    private boolean isPaid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AdvertStatus getAdvertStatus() {
        return advertStatus;
    }

    public void setAdvertStatus(AdvertStatus advertStatus) {
        this.advertStatus = advertStatus;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public AdvertCategoryDto getCategory() {
        return category;
    }

    public void setCategory(AdvertCategoryDto category) {
        this.category = category;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public UserModelDto getSeller() {
        return seller;
    }

    public void setSeller(UserModelDto seller) {
        this.seller = seller;
    }

    public UserModelDto getBuyer() {
        return buyer;
    }

    public void setBuyer(UserModelDto buyer) {
        this.buyer = buyer;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvertDto advertDto = (AdvertDto) o;
        return id == advertDto.id &&
                isPaid == advertDto.isPaid &&
                title.equals(advertDto.title) &&
                body.equals(advertDto.body) &&
                price.equals(advertDto.price) &&
                advertStatus == advertDto.advertStatus &&
                openTime.equals(advertDto.openTime) &&
                openDate.equals(advertDto.openDate) &&
                category.equals(advertDto.category) &&
                Objects.equals(closeDate, advertDto.closeDate) &&
                Objects.equals(closeTime, advertDto.closeTime) &&
                seller.equals(advertDto.seller) &&
                Objects.equals(buyer, advertDto.buyer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, price, advertStatus, openTime, openDate, category, closeDate, closeTime, seller, buyer, isPaid);
    }

    @Override
    public String toString() {
        return "AdvertDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", price=" + price +
                ", advertStatus=" + advertStatus +
                ", openTime=" + openTime +
                ", openDate=" + openDate +
                ", category=" + category +
                ", closeDate=" + closeDate +
                ", closeTime=" + closeTime +
                ", seller=" + seller +
                ", buyer=" + buyer +
                ", isPaid=" + isPaid +
                '}';
    }
}
