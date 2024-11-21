package com.example.library.user;

import com.example.library.AccountDB;
import com.example.library.CoreDatabase;
import com.example.library.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserManagerSystem extends CoreDatabase {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colPassword;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colPhoneNumber;

    @FXML
    private TableColumn<User, String> colClass;

    @FXML
    private ImageView logoutView;

    @FXML
    private TextField password;

    @FXML
    private TextField tClass;

    @FXML
    private TextField tName;

    @FXML
    private TextField tPhoneNumber;

    @FXML
    private TableView<User> table;

    @FXML
    private ImageView userMView;

    @FXML
    private ImageView userView;

    @FXML
    private TextField username;
    private MainApplication mainApp;

    public void initialize(URL url, ResourceBundle rb) {
        Image userImage = new Image(getClass().getResource("image/user.png").toExternalForm());
        userView.setImage(userImage);

        Image userMImage = new Image(getClass().getResource("image/userM.png").toExternalForm());
        userMView.setImage(userMImage);

        Image logoutImage = new Image(getClass().getResource("image/logout.png").toExternalForm());
        logoutView.setImage(logoutImage);
    }


    public ObservableList<User> UserListData() {
        ObservableList<User> list = FXCollections.observableArrayList();
        String sql = "SELECT username, password, name, role, phone ,classname FROM userdata ";

        con = AccountDB.getCon();

        try {
            User user;
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("phone"),
                        rs.getString("classname"));

                if (rs.getString("role").equals("client")) {
                    list.add(user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private ObservableList<User> UserShowListD;
    public void UserShowListData() {
        UserShowListD = UserListData();

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("classname"));


        table.setItems(UserShowListD);
    }

    @FXML
    public void addU(ActionEvent event) {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        String enteredName = tName.getText();
        String enteredPhone = tPhoneNumber.getText();
        String enteredClass = tClass.getText();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredName.isEmpty() || enteredPhone.isEmpty() || enteredClass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
            return;
        }

        String checkUserExistenceSql = "SELECT * FROM userdata WHERE username = ?";
        con = AccountDB.getCon();

        try {
            st = con.prepareStatement(checkUserExistenceSql);
            st.setString(1, enteredUsername);
            rs = st.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists!");
                alert.showAndWait();
            } else {
                String sql = "INSERT INTO userdata (username, password, name, role, phone, classname) VALUES (?, ?, ?, ?, ?, ?)";
                st = con.prepareStatement(sql);
                st.setString(1, enteredUsername);
                st.setString(2, enteredPassword);
                st.setString(3, enteredName);
                st.setString(4, "client");
                st.setString(5, enteredPhone);
                st.setString(6, enteredClass);

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("User successfully added!");
                    alert.showAndWait();

                    UserShowListData();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while connecting to the database.");
            alert.showAndWait();
        }
    }


    @FXML
    public void updateUser(ActionEvent event) {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        String enteredName = tName.getText();
        String enteredPhone = tPhoneNumber.getText();
        String enteredClass = tClass.getText();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredName.isEmpty() || enteredPhone.isEmpty() || enteredClass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
            return;
        }

        String updateSql = "UPDATE userdata SET password = ?, name = ?, phone = ?, classname = ? WHERE username = ?";
        con = AccountDB.getCon();

        try {
            st = con.prepareStatement(updateSql);
            st.setString(1, enteredPassword);
            st.setString(2, enteredName);
            st.setString(3, enteredPhone);
            st.setString(4, enteredClass);
            st.setString(5, enteredUsername);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User updated successfully!");
                alert.showAndWait();

                UserShowListData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while connecting to the database.");
            alert.showAndWait();
        }
    }


    @FXML
    public void deleteUser(ActionEvent event) {
        String enteredUsername = username.getText();

        if (enteredUsername.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
            return;
        }

        String deleteSql = "DELETE FROM userdata WHERE username = ?";
        con = AccountDB.getCon();

        try {
            st = con.prepareStatement(deleteSql);
            st.setString(1, enteredUsername);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User deleted successfully!");
                alert.showAndWait();

                UserShowListData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while connecting to the database.");
            alert.showAndWait();
        }
    }



    @FXML
    public void clearUser(ActionEvent event) {
        username.clear();
        password.clear();
        tName.clear();
        tPhoneNumber.clear();
        tClass.clear();
        btnAdd.setDisable(false);
    }

    @FXML
    void getData(MouseEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        tName.setText(user.getName());
        tPhoneNumber.setText(user.getPhone());
        tClass.setText(user.getClassname());

        btnAdd.setDisable(true);
    }


    @FXML
    public void searchUser(ActionEvent event) {
        String searchUsername = username.getText().toLowerCase().trim();
        String searchPassword = password.getText().toLowerCase().trim();
        String searchName = tName.getText().toLowerCase().trim();
        String searchPhone = tPhoneNumber.getText().toLowerCase().trim();
        String searchAddress = tClass.getText().toLowerCase().trim();

        ObservableList<User> filteredList = FXCollections.observableArrayList();

        String sql = "SELECT username, password, name, phone, classname FROM userdata WHERE 1=1 and role = 'client'";

        if (!searchUsername.isEmpty()) {
            sql += " AND LOWER(username) LIKE ?";
        }
        if (!searchPassword.isEmpty()) {
            sql += " AND LOWER(password) LIKE ?";
        }
        if (!searchName.isEmpty()) {
            sql += " AND LOWER(name) LIKE ?";
        }
        if (!searchPhone.isEmpty()) {
            sql += " AND LOWER(phone) LIKE ?";
        }
        if (!searchAddress.isEmpty()) {
            sql += " AND LOWER(classname) LIKE ?";
        }

        try {
            con = AccountDB.getCon();
            st = con.prepareStatement(sql);

            int index = 1;
            if (!searchUsername.isEmpty()) st.setString(index++, "%" + searchUsername + "%");
            if (!searchPassword.isEmpty()) st.setString(index++, "%" + searchPassword + "%");
            if (!searchName.isEmpty()) st.setString(index++, "%" + searchName + "%");
            if (!searchPhone.isEmpty()) st.setString(index++, "%" + searchPhone + "%");
            if (!searchAddress.isEmpty()) st.setString(index++, "%" + searchAddress + "%");

            rs = st.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("classname")
                );
                filteredList.add(user);
            }

            table.setItems(filteredList);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error during search");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void openLoginClient() throws Exception {
        mainApp.showLoginClient();
    }

    @FXML
    public void initialize() {
        UserShowListData();
    }

    public int searchUserName(String username) {
        String sql = "SELECT id FROM userdata WHERE username = ?";
        int userId = -1; // -1 để chỉ không tìm thấy

        try (Connection con = AccountDB.getCon(); // Kết nối CSDL
             PreparedStatement st = con.prepareStatement(sql)) {

            // Gán giá trị cho tham số username
            st.setString(1, username);

            try (ResultSet rs = st.executeQuery()) {
                // Lấy id từ kết quả trả về
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm id người dùng", e);
        }

        return userId;
    }


    public void setMainApp(MainApplication mainApplication) {
        this.mainApp = mainApplication;
    }
}
