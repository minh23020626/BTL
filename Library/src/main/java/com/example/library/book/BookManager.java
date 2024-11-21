package com.example.library.book;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private final String url = "jdbc:mysql://localhost:3306/book";
    private final String user = "root";
    private final String password = "21012005";

    // Kết nối đến cơ sở dữ liệu
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Thêm sách vào cơ sở dữ liệu
    public void addBook(Book book) {

        String sql = "INSERT INTO books (ISBN, `Book-Title`, `Book-Author`, `Year-Of-Publication`, Publisher, `Image-URL-S`, `Image-URL-M`, `Image-URL-L`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setInt(4, book.getYearOfPublication());
            pstmt.setString(5, book.getPublisher());
            pstmt.setString(6, book.getImageUrlS());
            pstmt.setString(7, book.getImageUrlM());
            pstmt.setString(8, book.getImageUrlL());
            pstmt.executeUpdate();
            System.out.println("Thêm sách thành công!");
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sách: " + e.getMessage());
        }
    }

    // Lấy tất cả sách từ cơ sở dữ liệu và trả về danh sách
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Book-Title"),
                        rs.getString("Book-Author"),
                        rs.getInt("Year-Of-Publication"),
                        rs.getString("Publisher"),
                        rs.getString("Image-URL-S"),
                        rs.getString("Image-URL-M"),
                        rs.getString("Image-URL-L")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sách: " + e.getMessage());
        }
        return books;
    }

    // Tìm kiếm sách theo từ khóa và trả về danh sách kết quả
    public List<Book> searchBooks(String keyword) {
        String sql = "SELECT * FROM books WHERE `Title` LIKE ? OR `Author` LIKE ?";
        List<Book> searchResults = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("PublishYear"),
                        rs.getString("Publisher"),
                        rs.getString("Image-URL-S"),
                        rs.getString("Image-URL-M"),
                        rs.getString("Image-URL-L")
                );
                searchResults.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sách: " + e.getMessage());
        }
        return searchResults;
    }

    public Book searchBookIsbn(String tmpIsbn) throws SQLException {
        String sql = "SELECT * FROM books WHERE `ISBN` = ?";
        Book book = null;

        try (Connection conn = connect(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            // Đặt giá trị ISBN vào PreparedStatement
            pstm.setString(1, tmpIsbn);
            try (ResultSet rs = pstm.executeQuery()) {
                // Kiểm tra nếu có dữ liệu trong ResultSet
                if (rs.next()) {
                    book = new Book();
                    book.setIsbn(rs.getString("ISBN"));
                    book.setTitle(rs.getString("Book-Title"));
                    book.setAuthor(rs.getString("Book-Author"));
                    book.setYearOfPublication(rs.getInt("Year-Of-Publication"));
                    book.setPublisher(rs.getString("Publisher"));
                    book.setImageUrlS(rs.getString("Image-Url-S"));
                    book.setImageUrlM(rs.getString("Image-Url-M"));
                    book.setImageUrlL(rs.getString("Image-Url-L"));
                } else {
                    System.err.println("No book found with ISBN: " + tmpIsbn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in searchBookIsbn: " + e.getMessage());
            throw e;
        }

        return book;
    }


    // Cập nhật sách trong cơ sở dữ liệu
    public void updateBook(Book book) {
        String sql = "UPDATE books SET `Book-Title` = ?, `Book-Author` = ?, `Year-Of-Publication` = ?, Publisher = ?, `Image-URL-S` = ?, `Image-URL-M` = ?, `Image-URL-L` = ? WHERE ISBN = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getYearOfPublication());
            pstmt.setString(4, book.getPublisher());
            pstmt.setString(5, book.getImageUrlS());
            pstmt.setString(6, book.getImageUrlM());
            pstmt.setString(7, book.getImageUrlL());
            pstmt.setString(8, book.getIsbn());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cập nhật sách thành công!");
            } else {
                System.out.println("Không tìm thấy sách với ISBN: " + book.getIsbn());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sách: " + e.getMessage());
        }
    }

    // Xóa sách khỏi cơ sở dữ liệu
    public void deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE ISBN = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Xóa sách thành công!");
            } else {
                System.out.println("Không tìm thấy sách với ISBN: " + isbn);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sách: " + e.getMessage());
        }
    }

    public void PostCommentForBook(String cmt, String ISBN) {}
}
