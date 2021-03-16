package org.raindog.shop.data;

@FunctionalInterface
public interface Rateable<T> {
    Rating DEFAULT_RATING = Rating.NOT_RATED;

    T applyRating(Rating rating);

    default T applyRating(int rating) {
        return applyRating(convert(rating));
    }

    default Rating getRating() {
        return DEFAULT_RATING;
    }

    static Rating convert(int rating) {
            return (rating >= 0 && rating <= 5) ? Rating.values()[rating] : DEFAULT_RATING;
    }
}
