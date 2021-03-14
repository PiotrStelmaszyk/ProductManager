package org.raindog.shop;


import org.raindog.shop.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Shop {

    public static void main(String[] args) {
        ProductManager pm = new ProductManager();

        Product p2 = pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.FOUR_STAR);
        Product p3 = pm.createProduct(103, "Chicken", BigDecimal.valueOf(3.99), Rating.FIVE_STAR, LocalDate.now().plusDays(2));
        Product p5 = p3.applyRating(Rating.THREE_STAR);
        Product p6 = pm.createProduct(106, "Chocolate", BigDecimal.valueOf(2.99), Rating.THREE_STAR, LocalDate.now().plusDays(3));
        Product p7 = p2.applyRating(Rating.ONE_STAR);

        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p5);
        System.out.println(p6);
        System.out.println(p7);


    }
}
