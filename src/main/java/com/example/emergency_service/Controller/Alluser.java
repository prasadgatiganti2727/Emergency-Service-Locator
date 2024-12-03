package com.example.emergency_service.Controller;

import com.example.emergency_service.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Alluser {
    @FXML
    private TextField u_username;

    @FXML
    private TextField u_password;

    @FXML
    private TableColumn<Users, Integer> su_id;

    @FXML
    private TableColumn<Users, String> su_username1;

    @FXML
    private TableColumn<Users, String> su_password1;

    @FXML
    private TableView<Users> userTableView;

    private Connection con;
    private PreparedStatement pst;

    // Connect to the database
    private void Connect() {
        if (con == null) { // Only connect if con is null
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/loginschema", "root", "123456");
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Adding user to the database
    @FXML
    void addUser(ActionEvent event) {
        String susername = u_username.getText();
        String spassword = u_password.getText();

        Connect(); // Ensure connection is established before proceeding

        try {
            if (con != null) {
                pst = con.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)");
                pst.setString(1, susername);
                pst.setString(2, spassword);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User added successfully.");
                alert.showAndWait();

                // Call table method to refresh the TableView
                refreshTableView();

                // Clear the text fields
                u_username.setText("");
                u_password.setText("");
                u_username.requestFocus();
                System.out.println(con); // This should print the connection object

            } else {
                showError("Database connection error.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alluser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Refresh the TableView to display the updated list of users
    public void refreshTableView() {
        ObservableList<Users> usersList = FXCollections.observableArrayList();
        try {
            if (con != null) {
                pst = con.prepareStatement("SELECT idnew_table, username, password FROM users");
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Users user = new Users();
                    user.setId(rs.getInt("idnew_table"));
                    user.setU_name(rs.getString("username"));
                    user.setU_pass(rs.getString("password"));
                    usersList.add(user);
                }

                // Set the usersList to the TableView
                userTableView.setItems(usersList);

                // Set cell value factories for each column
                su_id.setCellValueFactory(f -> f.getValue().idProperty().asObject());
                su_username1.setCellValueFactory(f -> f.getValue().userProperty());
                su_password1.setCellValueFactory(f -> f.getValue().passProperty());
            } else {
                showError("Database connection error.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alluser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void updateUser(ActionEvent event) {
        Users selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String newUsername = u_username.getText();
            String newPassword = u_password.getText();

            Connect();

            try {
                if (con != null) {
                    pst = con.prepareStatement("UPDATE users SET username = ?, password = ? WHERE idnew_table = ?");
                    pst.setString(1, newUsername);
                    pst.setString(2, newPassword);
                    pst.setInt(3, selectedUser.getId());
                    pst.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("User updated successfully.");
                    alert.showAndWait();

                    refreshTableView();

                    u_username.setText("");
                    u_password.setText("");
                    u_username.requestFocus();
                } else {
                    showError("Database connection error.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Alluser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showError("No user selected. Please select a user to update.");
        }
    }

    // Delete the selected user
    @FXML
    public void deleteUser(ActionEvent event) {
        Users selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Connect();

            try {
                if (con != null) {
                    pst = con.prepareStatement("DELETE FROM users WHERE idnew_table = ?");
                    pst.setInt(1, selectedUser.getId());
                    pst.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("User deleted successfully.");
                    alert.showAndWait();

                    refreshTableView();

                    u_username.setText("");
                    u_password.setText("");
                    u_username.requestFocus();
                } else {
                    showError("Database connection error.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Alluser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showError("No user selected. Please select a user to delete.");
        }
    }


    @FXML
    private void handleHomeButtonClick() {
        try {
            // Load the FXML file
            URL resource = getClass().getResource("/com/example/emergency_service/Views/homepage.fxml");
            if (resource == null) {
                throw new RuntimeException("FXML file not found at specified path: /com/example/emergency_service/Views/homepage.fxml");
            }
            Parent root = FXMLLoader.load(resource);

            // Get the current stage from any component in the scene (like the button)
            Stage currentStage = (Stage) userTableView.getScene().getWindow(); // Or any other component in your FXML

            // Set the scene and show the stage
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load the Home page. Please check the log for details.");
        }
    }



}
