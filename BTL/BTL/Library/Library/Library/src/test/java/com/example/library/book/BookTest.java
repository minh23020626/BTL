//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.library.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookTest {
    public BookTest() {
    }

    @Test
    public void testConstructorAndGetters() {
        Book book = new Book("0001046713", "Twopence to Cross the Mersey", "Helen Forrester", 1992, "HarperCollins Publishers", "http://images.amazon.com/images/P/0001046713.01.THUMBZZZ.jpg", "http://images.amazon.com/images/P/0001046713.01.MZZZZZZZ.jpg", "http://images.amazon.com/images/P/0001046713.01.LZZZZZZZ.jpg", 10);
        Assertions.assertEquals("0001046713", book.getIsbn());
        Assertions.assertEquals("Twopence to Cross the Mersey", book.getTitle());
        Assertions.assertEquals("Helen Forrester", book.getAuthor());
        Assertions.assertEquals(1992, book.getYearOfPublication());
        Assertions.assertEquals("HarperCollins Publishers", book.getPublisher());
        Assertions.assertEquals("http://images.amazon.com/images/P/0001046713.01.THUMBZZZ.jpg", book.getImageUrlS());
        Assertions.assertEquals("http://images.amazon.com/images/P/0001046713.01.MZZZZZZZ.jpg", book.getImageUrlM());
        Assertions.assertEquals("http://images.amazon.com/images/P/0001046713.01.LZZZZZZZ.jpg", book.getImageUrlL());
        Assertions.assertEquals(10, book.getNums());
    }

    @Test
    public void testSetters() {
        Book book = new Book();
        book.setIsbn("0987654321");
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setYearOfPublication(2023);
        book.setPublisher("New Publisher");
        book.setImageUrlS("newSmallUrl");
        book.setImageUrlM("newMediumUrl");
        book.setImageUrlL("newLargeUrl");
        Assertions.assertEquals("0987654321", book.getIsbn());
        Assertions.assertEquals("New Title", book.getTitle());
        Assertions.assertEquals("New Author", book.getAuthor());
        Assertions.assertEquals(2023, book.getYearOfPublication());
        Assertions.assertEquals("New Publisher", book.getPublisher());
        Assertions.assertEquals("newSmallUrl", book.getImageUrlS());
        Assertions.assertEquals("newMediumUrl", book.getImageUrlM());
        Assertions.assertEquals("newLargeUrl", book.getImageUrlL());
    }

    @Test
    public void testToString() {
        Book book = new Book("1234567890", "Title Example", "Author Example", 2022, "Publisher Example", "urlSmall", "urlMedium", "urlLarge", 10);
        String expectedString = "Book{ISBN='1234567890', Title='Title Example', Author='Author Example', Year Of Publication=2022, Publisher='Publisher Example', Image URL S='urlSmall', Image URL M='urlMedium', Image URL L='urlLarge'}";
        Assertions.assertEquals(expectedString, book.toString());
    }
}
