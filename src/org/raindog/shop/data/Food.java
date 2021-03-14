package org.raindog.shop.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Food extends Product {

    LocalDate bestBefore;

    @Override
    public LocalDate getBestBefore() {
        return bestBefore;
    }


    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    @Override
    public String toString() {
        return super.toString() + "and it\'s a Food";
    }

    @Override
    public BigDecimal getDiscount() {
        return (bestBefore.isEqual(LocalDate.now())) ? super.getDiscount() : BigDecimal.ZERO;
    }

    @Override
    public Product applyRating(Rating newRating) {
        return new Food(getId(), getName(), getPrice(), newRating, getBestBefore());
    }
}
