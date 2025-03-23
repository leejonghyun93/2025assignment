package examination;

import java.util.Scanner;

public class Book {

    private final String name;  // 책 이름
    private final int price;    // 책 가격

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }

}


