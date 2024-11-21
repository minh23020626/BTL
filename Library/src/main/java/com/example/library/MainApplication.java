package com.example.library;

import com.example.library.book.Book;
import com.example.library.book.DetailsBookController;
import com.example.library.book.LibraryManageController;
import com.example.library.book.LibrarySearchController;
import com.example.library.user.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLoginClient();  // Hiển thị mặc định giao diện tìm kiếm sách
    }


    public void showLoginClient() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginClient.fxml"));
        Parent root = fxmlLoader.load();

        LoginClientForm loginClientForm = fxmlLoader.getController();
        loginClientForm.setMainApp(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("Đăng nhập");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showLoginAdmin() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginAdmin.fxml"));
        Parent root = fxmlLoader.load();

        LoginAdminForm loginAdminForm = fxmlLoader.getController();
        loginAdminForm.setMainApp(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showDetailBook(Book book) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DetailsBook.fxml"));
        Parent root = fxmlLoader.load();

        DetailsBookController detailsBookController = fxmlLoader.getController();
        detailsBookController.setMainApp(this);
        detailsBookController.setBookDetails(book);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("sach");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showMainMenu() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Parent root = fxmlLoader.load();

        MainMenuController mainMenuController = fxmlLoader.getController();
        mainMenuController.setMainApp(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("Menu chính");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showSearchView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LibrarySearch.fxml"));
        Parent root = fxmlLoader.load();

        // Truyền đối tượng MainApplication vào controller
        LibrarySearchController searchController = fxmlLoader.getController();
        searchController.setMainApplication(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("Tìm Kiếm Sách");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showManageView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LibraryManage.fxml"));
        Parent root = fxmlLoader.load();

        // Truyền đối tượng MainApplication vào controller
        LibraryManageController manageController = fxmlLoader.getController();
        manageController.setMainApplication(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("Thêm/Xóa Sách");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showUser() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("User.fxml"));
        Parent root = fxmlLoader.load();

        UserManagerSystem userManagerSystem = fxmlLoader.getController();
        userManagerSystem.setMainApp(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("User Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showSignUp() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SignUp.fxml"));
        Parent root = fxmlLoader.load();

        SignupForm signupForm = fxmlLoader.getController();
        signupForm.setMainApp(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
