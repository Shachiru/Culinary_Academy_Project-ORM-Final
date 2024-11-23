package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import lk.ijse.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminGlobalFormController implements Initializable {

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnPrograms;

    @FXML
    private JFXButton btnRegistration;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnUsers;

    @FXML
    private Pane pagingPane;

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "admin/dashboardForm.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("mainForm.fxml", event);
    }

    @FXML
    void btnProgramsOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "admin/programsForm.fxml");
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "admin/registrationForm.fxml");
    }

    @FXML
    void btnSettingsOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "admin/settingForm.fxml");
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "admin/studentForm.fxml");
    }

    @FXML
    void btnUsersOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "admin/usersForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pagingPane.setVisible(true);
        try {
            Navigation.switchPaging(pagingPane, "admin/dashboardForm.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
