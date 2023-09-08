package ru.gb.catalogservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="prices")
@Data
@RequiredArgsConstructor
public class Price extends GenericEntity {
    @Column(name="price_rent")
    private int priceRent;

    @Column(name="price_sale")
    private int priceSale;

    @ManyToOne
    private Film film;

    @Override
    public String toString() {
        return "Price{" +
                "priceRent=" + priceRent +
                ", priceSale=" + priceSale +
                ", film=" + film.getId() +
                ", deleted=" + isDeleted()+
                '}';
    }
}
