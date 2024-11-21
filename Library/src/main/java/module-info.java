module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires java.desktop;


    opens com.example.library to javafx.fxml;
    exports com.example.library;
    exports com.example.library.book;
    opens com.example.library.book to javafx.fxml;
    exports com.example.library.user;
    opens com.example.library.user to javafx.fxml;
}