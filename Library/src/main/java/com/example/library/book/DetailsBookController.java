package com.example.library.book;

import com.example.library.AccountDB;
import com.example.library.CoreDatabase;
import com.example.library.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DetailsBookController extends CoreDatabase {

    public DetailsBookController() {}

    private MainApplication mainApp = new MainApplication();

    @FXML
    private Label isbnLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private ImageView bookImage;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label labelIsbn;
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelAuthor;
    @FXML
    private Label labelYear;
    @FXML
    private Label labelPublisher;
    @FXML
    private ScrollPane scrollPaneComment;
    @FXML
    private TextField textFieldComment;
    @FXML
    private Button buttonPostComment;
    @FXML
    private GridPane formatComment = new GridPane();
    @FXML
    private ListView<String> commentListView;

    private Book mainBook = new Book();

    private BookManager bookManager = new BookManager();

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
            } else {
                System.err.println("Error loading image: Server returned HTTP response code: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Errrrrror loading image: " + e.getMessage());
        }
    }


    public void setBookDetails(Book book) throws Exception {
        if (book == null) System.out.println("loi ko co sach");
        mainBook = book;
        loadImage(book.getImageUrlM(), bookImage);
        isbnLabel.setText(book.getIsbn());
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        yearLabel.setText(String.valueOf(book.getYearOfPublication()));
        publisherLabel.setText(book.getPublisher());
    }

    public void initialize() {
        loadComment();
    }

    @FXML
    private void borrowBook() {
        String sql = "INSERT INTO book_borrowed (book_id, user_id)"
                + "VALUES (?, ?)";
        con = AccountDB.getCon();
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1,mainBook.getIsbn());
            pstm.setInt(2, AccountDB.id);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("mượn sách không thành công " + e.getMessage());;
        }
    }

    @FXML
    private void returnToMenu() throws Exception {
        mainApp.showSearchView();
    }

    @FXML
    public void addComment() {


        String sql = "INSERT INTO book_comments (book_id, user_id, content) "
                + "VALUES (?, ?, ?)";
        con = AccountDB.getCon();
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, mainBook.getIsbn());
            pstm.setInt(2, AccountDB.id);
            pstm.setString(3, textFieldComment.getText());

            int test = pstm.executeUpdate();
            if (test > 0) {
                System.out.println("co");
            } else {
                System.out.println("sai o excute");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComment() {

        List<Comment> commentList = Comment.getCommentList(AccountDB.ISBN);

        if (commentList == null || commentList.isEmpty()) {
            ObservableList<String> noComments = FXCollections.observableArrayList("No comments available.");
            commentListView.setItems(noComments);
            return;
        }

        // Tạo danh sách các chuỗi comment để hiển thị
        ObservableList<String> comments = FXCollections.observableArrayList();

        for (Comment comment : commentList) {
            String displayText = "User: " + comment.getUserId() + "\n" + comment.getContent();
            comments.add(displayText);
        }

        // Cập nhật ListView với danh sách comment
        commentListView.setItems(comments);

        // Thêm style nếu cần
        commentListView.setStyle("-fx-font-size: 14px; -fx-wrap-text: true;");
    }


    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }
}
