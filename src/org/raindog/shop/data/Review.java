package org.raindog.shop.data;

public class Review {
    private Rating rating;
    private String comment;

    public Review(Rating rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Rating getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}