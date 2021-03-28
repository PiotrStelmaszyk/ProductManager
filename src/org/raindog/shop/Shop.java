package org.raindog.shop;


import org.raindog.shop.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Shop {

    public static void main(String[] args) {
        ProductManager pm = new ProductManager(Locale.UK);

        pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.printProductReport(102);
        pm.reviewProduct(102, Rating.FOUR_STAR, "Very nice cup of coffee");
        pm.reviewProduct(102, Rating.FOUR_STAR, "Very nice cup of coffee");
        pm.reviewProduct(102, Rating.ONE_STAR, "Very nice cup of coffee");
        pm.reviewProduct(102, Rating.THREE_STAR, "Ok cup of coffee");
        pm.reviewProduct(102, Rating.FOUR_STAR, "Very nice cup of coffee");
        pm.reviewProduct(102, Rating.ONE_STAR, "Very bad cup of coffee");
        pm.printProductReport(102);

        pm.createProduct(101, "Tee", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.printProductReport(101);
        pm.reviewProduct(101, Rating.FOUR_STAR, "Very nice cup of Tea");
        pm.reviewProduct(101, Rating.FOUR_STAR, "Very nice cup of tea");
        pm.reviewProduct(101, Rating.ONE_STAR, "Very nice cup of tea");
        pm.reviewProduct(101, Rating.THREE_STAR, "Ok cup of tea");
        pm.reviewProduct(101, Rating.FOUR_STAR, "Very nice cup of tea");
        pm.reviewProduct(101, Rating.ONE_STAR, "Very bad cup of tea");
        pm.printProductReport(101);


        pm.createProduct(103, "Chicken", BigDecimal.valueOf(3.99), Rating.FIVE_STAR, LocalDate.now().plusDays(2));
        pm.reviewProduct(103, Rating.FOUR_STAR, "Very nice cup of Tea");
        pm.reviewProduct(103, Rating.FOUR_STAR, "Very nice cup of tea");
        pm.reviewProduct(103, Rating.ONE_STAR, "Very nice cup of tea");
        pm.printProductReport(103);

//        Product p5 = p3.applyRating(Rating.THREE_STAR);
//        Product p6 = pm.createProduct(106, "Chocolate", BigDecimal.valueOf(2.99), Rating.THREE_STAR, LocalDate.now().plusDays(3));
//        Product p7 = p2.applyRating(Rating.ONE_STAR);
//
//        System.out.println("********   Welcome to the shop!   **********");
//        System.out.println(p2);
//        System.out.println(p3);
//        System.out.println(p5);
//        System.out.println(p6);
//        System.out.println(p7);

    }
}
