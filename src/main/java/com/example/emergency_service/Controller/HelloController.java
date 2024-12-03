package com.example.emergency_service.Controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.emergency_service.JDBC;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;


public class HelloController {

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private PasswordField su_password;

    @FXML
    private Button su_signupBtn;

    @FXML
    private Button su_signupBtn1;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;
    @FXML
    private Label si_message;

    @FXML
    void backToLoginForm(ActionEvent event) {

    }

    @FXML
    void backToQuestionForm(ActionEvent event) {

    }

    @FXML
    void changePassBtn(ActionEvent event) {

    }

    @FXML
    void loginBtn(ActionEvent event) {

    }

    @FXML
    void proceedBtn(ActionEvent event) {

    }

    @FXML
    void regBtn(ActionEvent event) {

    }

    @FXML
    void switchForgotPass(ActionEvent event) {

    }



    @FXML
    void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);
            });
            slider.play();

        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);
            });
            slider.play();
        }
    }

    @FXML
    public void validateLogin() {
        JDBC connectNow = new JDBC();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM USERS WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, si_username.getText());
            preparedStatement.setString(2, si_password.getText());


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 1) {
                si_message.setText("User authenticated successfully.");

//                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("com/example/emergency_service/Views/alluser.fxml")));



                URL resource = getClass().getResource("/com/example/emergency_service/Views/alluser.fxml");
                if (resource == null) {
                    throw new RuntimeException("FXML file not found at specified path");
                }
                Parent root = FXMLLoader.load(resource);
                Stage stage = new Stage();
                Scene scene = new Scene(root);



                stage.setTitle("Emergency Service Locator");

                stage.setScene(scene);
                stage.show();

//                System.out.println(getClass().getResource("/com/example/emergency_service/Views/alluser.fxml"));


            } else {
                si_message.setText("Invalid login, please retry.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            si_message.setText("An error occurred during login.");
        }
    }

}





