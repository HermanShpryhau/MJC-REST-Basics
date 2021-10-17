package com.epam.esm.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Gift_certificate")
public class GiftCertificate extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "duration")
    private int duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "Gift_certificate_has_Tag",
            joinColumns = @JoinColumn(name = "certificate"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    private Set<Tag> associatedTags = new HashSet<>();

    public GiftCertificate() {
    }

    public GiftCertificate(Long id, String name, String description, int price, int duration, LocalDateTime createDate,
                           LocalDateTime lastUpdateDate) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<Tag> getAssociatedTags() {
        return associatedTags;
    }

    public void setAssociatedTags(Set<Tag> associatedTags) {
        this.associatedTags = associatedTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (price != that.price) return false;
        if (duration != that.duration) return false;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        if (!createDate.equals(that.createDate)) return false;
        if (!lastUpdateDate.equals(that.lastUpdateDate)) return false;
        return associatedTags.equals(that.associatedTags);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price;
        result = 31 * result + duration;
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        result = 31 * result + associatedTags.hashCode();
        return result;
    }
}
