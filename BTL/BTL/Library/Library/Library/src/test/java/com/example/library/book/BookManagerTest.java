//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.library.book;

import com.google.zxing.WriterException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookManagerTest {
    private BookManager bookManager;

    public BookManagerTest() {
    }

    @BeforeEach
    public void setUp() {
        this.bookManager = new BookManager();
    }

    @Test
    public void testAddBook_withQRCode() throws IOException, WriterException {
        Book book = new Book("00120471XX", "Test Book", "Test Author", 2024, "Test Publisher", "http://example.com/small.jpg", "http://example.com/medium.jpg", "http://example.com/large.jpg");
        List<String> initialBooks = this.bookManager.getAllBooksTitle();
        int initialSize = initialBooks.size();
        this.bookManager.addBook(book);
        List<String> booksAfterAdding = this.bookManager.getAllBooksTitle();
        Assertions.assertEquals(initialSize + 1, booksAfterAdding.size(), "Book should be added to the database");
        Blob qrCodeBlob = BookManager.getQrcodeBlob(book.getIsbn());
        Assertions.assertNotNull(qrCodeBlob, "QR Code should be generated and stored in the database");
        String var10000 = book.getTitle();
        String searchQuery = var10000 + " " + book.getAuthor() + " " + book.getPublisher();
        String expectedSearchUrl = "https://www.google.com/search?q=" + searchQuery.replace(" ", "+");
        byte[] expectedQrCodeBytes = QRCodeGenerator.generateQRCodeBytes(expectedSearchUrl);

        try {
            byte[] qrCodeBytes = qrCodeBlob.getBytes(1L, (int)qrCodeBlob.length());
            Assertions.assertArrayEquals(expectedQrCodeBytes, qrCodeBytes, "Generated QR code should match the expected QR code");
        } catch (SQLException var10) {
            SQLException e = var10;
            Assertions.fail("Failed to retrieve QR code bytes from Blob: " + e.getMessage());
        }

        this.bookManager.deleteBook("00120471XX");
    }

    @Test
    public void testSearchBooks() {
        Book book1 = new Book("0002046713", "Test Book 1", "Test Author 1", 2023, "Publisher 1", "http://example.com/small1.jpg", "http://example.com/medium1.jpg", "http://example.com/large1.jpg");
        Book book2 = new Book("0002046715", "Test Book 2", "Test Author 2", 2022, "Publisher 2", "http://example.com/small2.jpg", "http://example.com/medium2.jpg", "http://example.com/large2.jpg");
        this.bookManager.addBook(book1);
        this.bookManager.addBook(book2);
        List<Book> result = this.bookManager.searchBooks("Test Book");
        Assertions.assertFalse(result.isEmpty(), "Search result should not be empty");
        Assertions.assertTrue(result.stream().anyMatch((b) -> {
            return b.getTitle().equals("Test Book 1");
        }), "Book 1 should be found");
        Assertions.assertTrue(result.stream().anyMatch((b) -> {
            return b.getTitle().equals("Test Book 2");
        }), "Book 2 should be found");
        this.bookManager.deleteBook("0002046713");
        this.bookManager.deleteBook("0002046715");
    }

    @Test
    public void testSearchBookIsbn() throws SQLException {
        Book book = new Book("00020471XX", "Test Book", "Test Author", 2024, "Test Publisher", "http://example.com/small.jpg", "http://example.com/medium.jpg", "http://example.com/large.jpg");
        this.bookManager.addBook(book);
        Book foundBook = this.bookManager.searchBookIsbn("00020471XX");
        Assertions.assertNotNull(foundBook, "Book should be found by ISBN");
        Assertions.assertEquals("Test Book", foundBook.getTitle(), "Book title should match");
        this.bookManager.deleteBook("00020471XX");
    }

    @Test
    public void testUpdateBook() throws SQLException {
        Book book = new Book("00120471XX", "Old Title", "Test Author", 2024, "Test Publisher", "http://example.com/small.jpg", "http://example.com/medium.jpg", "http://example.com/large.jpg");
        this.bookManager.addBook(book);
        book.setTitle("Updated Title");
        this.bookManager.updateBook(book);
        Book updatedBook = this.bookManager.searchBookIsbn("00120471XX");
        Assertions.assertNotNull(updatedBook, "Book should be found by ISBN");
        Assertions.assertEquals("Updated Title", updatedBook.getTitle(), "Book title should be updated");
        this.bookManager.deleteBook("00120471XX");
    }

    @Test
    public void testDeleteBook() throws SQLException {
        Book book = new Book("00120471XX", "Test Book", "Test Author", 2024, "Test Publisher", "http://example.com/small.jpg", "http://example.com/medium.jpg", "http://example.com/large.jpg");
        this.bookManager.addBook(book);
        List<String> booksBeforeDeletion = this.bookManager.getAllBooksTitle();
        Assertions.assertTrue(booksBeforeDeletion.contains("Test Book"), "Book should exist before deletion");
        this.bookManager.deleteBook("00120471XX");
        List<String> booksAfterDeletion = this.bookManager.getAllBooksTitle();
        Assertions.assertFalse(booksAfterDeletion.contains("Test Book"), "Book should not exist after deletion");
    }
}
