package com.amorrecords.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.amorrecords.domain.enumeration.Condition;

import com.amorrecords.domain.enumeration.DiscoType;

/**
 * A Disco.
 */
@Entity
@Table(name = "disco")
public class Disco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "query")
    private String query;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "release_title")
    private String releaseTitle;

    @Column(name = "credit")
    private String credit;

    @Column(name = "artist")
    private String artist;

    @Column(name = "anv")
    private String anv;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "genre")
    private String genre;

    @Column(name = "style")
    private String style;

    @Column(name = "country")
    private String country;

    @Column(name = "jhi_year")
    private String year;

    @Column(name = "format")
    private String format;

    @Column(name = "catno")
    private String catno;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "track")
    private String track;

    @Column(name = "submitter")
    private String submitter;

    @Column(name = "contributor")
    private String contributor;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_condition")
    private Condition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "disco_type")
    private DiscoType discoType;

    @Column(name = "date_purchase")
    private Instant datePurchase;

    @Column(name = "date_sale")
    private Instant dateSale;

    @Column(name = "cover")
    private Double cover;

    @Column(name = "detail")
    private Double detail;

    @Column(name = "price_purchase")
    private Double pricePurchase;

    @Column(name = "price_sale")
    private Double priceSale;

    @Column(name = "price_sale_store")
    private Double priceSaleStore;

    @Column(name = "price_sale_third_party")
    private Double priceSaleThirdParty;

    @Column(name = "price_sale_in_real")
    private Double priceSaleInReal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public Disco query(String query) {
        this.query = query;
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public Disco type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Disco title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseTitle() {
        return releaseTitle;
    }

    public Disco releaseTitle(String releaseTitle) {
        this.releaseTitle = releaseTitle;
        return this;
    }

    public void setReleaseTitle(String releaseTitle) {
        this.releaseTitle = releaseTitle;
    }

    public String getCredit() {
        return credit;
    }

    public Disco credit(String credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getArtist() {
        return artist;
    }

    public Disco artist(String artist) {
        this.artist = artist;
        return this;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAnv() {
        return anv;
    }

    public Disco anv(String anv) {
        this.anv = anv;
        return this;
    }

    public void setAnv(String anv) {
        this.anv = anv;
    }

    public String getLabel() {
        return label;
    }

    public Disco label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGenre() {
        return genre;
    }

    public Disco genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStyle() {
        return style;
    }

    public Disco style(String style) {
        this.style = style;
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCountry() {
        return country;
    }

    public Disco country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public Disco year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFormat() {
        return format;
    }

    public Disco format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCatno() {
        return catno;
    }

    public Disco catno(String catno) {
        this.catno = catno;
        return this;
    }

    public void setCatno(String catno) {
        this.catno = catno;
    }

    public String getBarCode() {
        return barCode;
    }

    public Disco barCode(String barCode) {
        this.barCode = barCode;
        return this;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getTrack() {
        return track;
    }

    public Disco track(String track) {
        this.track = track;
        return this;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getSubmitter() {
        return submitter;
    }

    public Disco submitter(String submitter) {
        this.submitter = submitter;
        return this;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getContributor() {
        return contributor;
    }

    public Disco contributor(String contributor) {
        this.contributor = contributor;
        return this;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public Condition getCondition() {
        return condition;
    }

    public Disco condition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public DiscoType getDiscoType() {
        return discoType;
    }

    public Disco discoType(DiscoType discoType) {
        this.discoType = discoType;
        return this;
    }

    public void setDiscoType(DiscoType discoType) {
        this.discoType = discoType;
    }

    public Instant getDatePurchase() {
        return datePurchase;
    }

    public Disco datePurchase(Instant datePurchase) {
        this.datePurchase = datePurchase;
        return this;
    }

    public void setDatePurchase(Instant datePurchase) {
        this.datePurchase = datePurchase;
    }

    public Instant getDateSale() {
        return dateSale;
    }

    public Disco dateSale(Instant dateSale) {
        this.dateSale = dateSale;
        return this;
    }

    public void setDateSale(Instant dateSale) {
        this.dateSale = dateSale;
    }

    public Double getCover() {
        return cover;
    }

    public Disco cover(Double cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(Double cover) {
        this.cover = cover;
    }

    public Double getDetail() {
        return detail;
    }

    public Disco detail(Double detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(Double detail) {
        this.detail = detail;
    }

    public Double getPricePurchase() {
        return pricePurchase;
    }

    public Disco pricePurchase(Double pricePurchase) {
        this.pricePurchase = pricePurchase;
        return this;
    }

    public void setPricePurchase(Double pricePurchase) {
        this.pricePurchase = pricePurchase;
    }

    public Double getPriceSale() {
        return priceSale;
    }

    public Disco priceSale(Double priceSale) {
        this.priceSale = priceSale;
        return this;
    }

    public void setPriceSale(Double priceSale) {
        this.priceSale = priceSale;
    }

    public Double getPriceSaleStore() {
        return priceSaleStore;
    }

    public Disco priceSaleStore(Double priceSaleStore) {
        this.priceSaleStore = priceSaleStore;
        return this;
    }

    public void setPriceSaleStore(Double priceSaleStore) {
        this.priceSaleStore = priceSaleStore;
    }

    public Double getPriceSaleThirdParty() {
        return priceSaleThirdParty;
    }

    public Disco priceSaleThirdParty(Double priceSaleThirdParty) {
        this.priceSaleThirdParty = priceSaleThirdParty;
        return this;
    }

    public void setPriceSaleThirdParty(Double priceSaleThirdParty) {
        this.priceSaleThirdParty = priceSaleThirdParty;
    }

    public Double getPriceSaleInReal() {
        return priceSaleInReal;
    }

    public Disco priceSaleInReal(Double priceSaleInReal) {
        this.priceSaleInReal = priceSaleInReal;
        return this;
    }

    public void setPriceSaleInReal(Double priceSaleInReal) {
        this.priceSaleInReal = priceSaleInReal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disco disco = (Disco) o;
        if (disco.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Disco{" +
            "id=" + getId() +
            ", query='" + getQuery() + "'" +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", releaseTitle='" + getReleaseTitle() + "'" +
            ", credit='" + getCredit() + "'" +
            ", artist='" + getArtist() + "'" +
            ", anv='" + getAnv() + "'" +
            ", label='" + getLabel() + "'" +
            ", genre='" + getGenre() + "'" +
            ", style='" + getStyle() + "'" +
            ", country='" + getCountry() + "'" +
            ", year='" + getYear() + "'" +
            ", format='" + getFormat() + "'" +
            ", catno='" + getCatno() + "'" +
            ", barCode='" + getBarCode() + "'" +
            ", track='" + getTrack() + "'" +
            ", submitter='" + getSubmitter() + "'" +
            ", contributor='" + getContributor() + "'" +
            ", condition='" + getCondition() + "'" +
            ", discoType='" + getDiscoType() + "'" +
            ", datePurchase='" + getDatePurchase() + "'" +
            ", dateSale='" + getDateSale() + "'" +
            ", cover=" + getCover() +
            ", detail=" + getDetail() +
            ", pricePurchase=" + getPricePurchase() +
            ", priceSale=" + getPriceSale() +
            ", priceSaleStore=" + getPriceSaleStore() +
            ", priceSaleThirdParty=" + getPriceSaleThirdParty() +
            ", priceSaleInReal=" + getPriceSaleInReal() +
            "}";
    }
}
