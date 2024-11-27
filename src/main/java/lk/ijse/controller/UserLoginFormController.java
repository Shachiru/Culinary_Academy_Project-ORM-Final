package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.util.Navigation;

public class UserLoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnBack;

    @FXML
    private Hyperlink hyperFP;

    @FXML
    private Hyperlink hyperSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) {

    }

    @FXML
    void hlinkForgotPw(ActionEvent event) {

    }

    @FXML
    void hlinkSignUp(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        btnBack.getScene().getWindow().hide();
        Navigation.changeStage("/view/mainForm.fxml", "Main Form");
    }
}
