package com.kisel.Porechanka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "adverts")
@NamedEntityGraph(name = "Advert.category",
        attributeNodes = @NamedAttributeNode(value = "category", subgraph = "category-subgraph"),
        subgraphs = {
        @NamedSubgraph(
                name = "category-subgraph",
                attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("name"),
                        @NamedAttributeNode("description")
                }
        )
        }
)
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "advert_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdvertStatus advertStatus;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "category_id")
    private AdvertCategory category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserModel seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private UserModel buyer;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "advert"
    )
    private List<AdvertComment> advertComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdvertCategory getCategory() {
        return category;
    }

    public void setCategory(AdvertCategory category) {
        this.category = category;
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

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public UserModel getSeller() {
        return seller;
    }

    public void setSeller(UserModel seller) {
        this.seller = seller;
    }

    public UserModel getBuyer() {
        return buyer;
    }

    public void setBuyer(UserModel buyer) {
        this.buyer = buyer;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public List<AdvertComment> getAdvertComments() {
        return advertComments;
    }

    public void setAdvertComments(List<AdvertComment> advertComments) {
        this.advertComments = advertComments;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
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
        Advert advert = (Advert) o;
        return isPaid == advert.isPaid &&
                id.equals(advert.id) &&
                title.equals(advert.title) &&
                Objects.equals(body, advert.body) &&
                Objects.equals(price, advert.price) &&
                advertStatus == advert.advertStatus &&
                openDate.equals(advert.openDate) &&
                Objects.equals(closeDate, advert.closeDate) &&
                openTime.equals(advert.openTime) &&
                Objects.equals(closeTime, advert.closeTime) &&
                category.equals(advert.category) &&
                seller.equals(advert.seller) &&
                Objects.equals(buyer, advert.buyer) &&
                Objects.equals(advertComments, advert.advertComments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, price, advertStatus, openDate, closeDate, openTime, closeTime, category, seller, buyer, isPaid, advertComments);
    }
}
