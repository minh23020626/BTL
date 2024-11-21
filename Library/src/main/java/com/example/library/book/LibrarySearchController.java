package com.example.library.book;

import com.example.library.AccountDB;
import com.example.library.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LibrarySearchController {

    private MainApplication mainApp = new MainApplication();

    @FXML
    private TextField searchField;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Button searchButton;

    private final BookManager bookManager = new BookManager();
    private DetailsBookController detailsBook = new DetailsBookController();

    private void loadImage(String imageUrl, ImageView imageView) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                Image image = new Image(input);
                imageView.setImage(image);
                if (imageView == null) System.out.println("null nay");
            } else {
                System.err.println("Error loading image: Server returned HTTP response code: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Errorrrrr loading image: " + e.getMessage());
        }
    }

    @FXML
    private void clickSearchButton(ActionEvent e) throws Exception {
        String keyWord = searchField.getText();
        searchBook(keyWord);
    }

    @FXML
    private void searchBook(String keyWord) throws Exception {
        List<Book> bookList = bookManager.searchBooks(keyWord);

        // Tải ảnh và thông tin sách
        setupBookDisplay(bookList.get(0), imageView1, label1);
        setupBookDisplay(bookList.get(1), imageView2, label2);
        setupBookDisplay(bookList.get(2), imageView3, label3);
        setupBookDisplay(bookList.get(3), imageView4, label4);
        setupBookDisplay(bookList.get(4), imageView5, label5);
        setupBookDisplay(bookList.get(5), imageView6, label6);

    }


    private void setupBookDisplay(Book book, ImageView imageView, Label label) {
        loadImage(book.getImageUrlM(), imageView); // Tải ảnh từ URL
        label.setText(book.getTitle()); // Hiển thị tiêu đề sách

        imageView.setOnMouseClicked(event -> {
            try {
                System.out.println("Book: " + book);
                System.out.println("ISBN: " + (book != null ? book.getIsbn() : "null"));

                if (book == null || book.getIsbn() == null || book.getIsbn().isEmpty()) {
                    throw new IllegalArgumentException("Invalid book or ISBN");
                }

                AccountDB.ISBN = book.getIsbn();
                mainApp.showDetailBook(book);
            } catch (Exception e) {
                System.err.println("Error in setUpDisplay: " + e.getMessage());
                e.printStackTrace(); // In ra stack trace để dễ dàng tìm lỗi
            }
        });

    }


    // Phương thức thiết lập MainApplication
    public void setMainApplication(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void returnToMainMenu() throws Exception {
        mainApp.showMainMenu();  // Sử dụng mainApp để chuyển giao diện
        }
}