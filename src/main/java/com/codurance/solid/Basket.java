package com.codurance.solid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codurance.solid.BookType.*;
import static com.codurance.solid.BookType.IT;
import static com.codurance.solid.BookType.TRAVEL;
import static java.lang.Math.round;
import static java.util.Collections.unmodifiableList;

public class Basket {

    private List<Book> books = new ArrayList<>();

    public void add(Book book) {
        books.add(book);
    }

    List<Book> books() {
        return unmodifiableList(books);
    }


    double priceWithDiscount() {

        double number_of_it_books = 0;
        double number_of_travel_books = 0;

        Map<BookType, Double> discountPerBookType = new HashMap<>();
        for (BookType bookType: BookType.values()) {
            discountPerBookType.put(bookType, 1.);
        }
        for (Book book: this.books) {
            if (IT.equals(book.type())) {
                number_of_it_books += 1;
            } else if (TRAVEL.equals(book.type())) {
                number_of_travel_books += 1;
            }
        }
        if (number_of_it_books > 2) {
            discountPerBookType.put(IT, 0.7);
        } else if (number_of_it_books > 0) {
            discountPerBookType.put(IT, 0.9);
        }

        if (number_of_travel_books > 3) {
            discountPerBookType.put(TRAVEL, 0.6);
        }


        Map<BookType, Double> pricePerBookType = getPricePerBookType();

        return computeTotalPriceWithDiscount(discountPerBookType, pricePerBookType);
    }

    private double computeTotalPriceWithDiscount(Map<BookType, Double> discountPerBookType, Map<BookType, Double> pricePerBookType) {
        double totalPrice = 0d;
        for (BookType bookType: BookType.values()) {
            totalPrice += pricePerBookType.get(bookType) * discountPerBookType.get(bookType);
        }
        return toDecimal(totalPrice);
    }

    private Map<BookType, Double> getPricePerBookType() {
        Map<BookType, Double> pricePerBookType = new HashMap<>();
        for (BookType bookType : values()) {
            pricePerBookType.put(bookType, 0.);
        }
        for (Book book : this.books) {
            pricePerBookType.put(book.type(), pricePerBookType.get(book.type()) + book.price());
        }
        return pricePerBookType;
    }

    double fullPrice() {
        double price = 0;
        for (Book book : books) {
            price += book.price();
        }
        return toDecimal(price);
    }

    private double toDecimal(double number) {
        return round(number * 100) / 100.0;
    }
}
