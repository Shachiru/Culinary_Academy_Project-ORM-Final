package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.util.Navigation;

import java.io.IOException;

public class AdminLoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnBack;

    @FXML
    private Hyperlink hyperFP;

    @FXML
    private Hyperlink hyperSignUp;

    @FXML
    private TextField txtAdminName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String userName = txtAdminName.getText();
        String password = txtPassword.getText();
        if (userName.equals("admin") && password.equals("admin")) {
            Navigation.switchNavigation("admin/adminGlobalForm.fxml", event);
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid User Name or Password").show();
        }
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
