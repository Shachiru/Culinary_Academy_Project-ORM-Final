package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.ProgramBO;
import lk.ijse.service.custom.RegisterBO;
import lk.ijse.service.custom.StudentBO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationFormController implements Initializable {

    ProgramBO programBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ProgramBO);
    RegisterBO registerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RegisterBO);

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnBuyCourse;

    @FXML
    private JFXButton btnClear;

    @FXML
    private Button btnStudentSearch;

    @FXML
    private ComboBox<String> cmbSelectCourse;

    @FXML
    private TableColumn<?, ?> colAdvance;

    @FXML
    private TableColumn<?, ?> colCourseFee;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colRegistrationId;

    @FXML
    private TableColumn<?, ?> colRemainingFee;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblAddToCart;

    @FXML
    private TextField txtAdvancePayment;

    @FXML
    private TextField txtAvailableSeats;

    @FXML
    private TextField txtCourseFee;

    @FXML
    private TextField txtCourseId;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtRegistrationId;

    @FXML
    private TextField txtStudentContact;

    @FXML
    private TextField txtStudentEmail;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    StudentBO studentBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.StudentBO);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllPrograms();
        generateNextRegistrationId();
        setCellValueFactory();
        cmbSelectCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            }
        });
    }

    private void setCellValueFactory() {
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("courseFee"));
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("advance"));
        colRemainingFee.setCellValueFactory(new PropertyValueFactory<>("remainingFee"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    @FXML
    void cmbSelectCourseOnAction(ActionEvent event) throws SQLException {
        String selectedCourse = cmbSelectCourse.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {

            ProgramDTO programDTO = programBO.searchProgramByName(selectedCourse);
            if (programDTO != null) {

                txtCourseId.setText(programDTO.getId());
                txtCourseFee.setText(String.valueOf(programDTO.getFee()));
                txtDuration.setText(programDTO.getDuration());
                txtAvailableSeats.setText(programDTO.getSeats());
            }
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnBuyCourseOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Fields");
        alert.setHeaderText("Are you sure you want to clear all fields?");
        alert.setContentText("This will remove all data from the form.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            txtCourseId.clear();
            txtCourseFee.clear();
            txtDuration.clear();
            txtAvailableSeats.clear();
            txtRegistrationId.clear();
            txtStudentContact.clear();
            txtStudentEmail.clear();
            txtStudentId.clear();
            txtStudentName.clear();
            cmbSelectCourse.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String studentId = txtStudentId.getText();
        if (!studentId.isEmpty()) {
            try {
                StudentDTO studentDTO = studentBO.searchStudentId(studentId);
                if (studentDTO != null) {
                    txtStudentName.setText(studentDTO.getName());
                    txtStudentEmail.setText(studentDTO.getEmail());
                    txtStudentContact.setText(studentDTO.getContact());
                } else {
                    new Alert(Alert.AlertType.WARNING, "No Student Found: " + studentId).show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error occurred while searching student").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter student ID").show();
        }
    }

    private void generateNextRegistrationId() {
        try{
            txtRegistrationId.setText(registerBO.generateNextRegId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllPrograms() {
        ObservableList<String> programs = FXCollections.observableArrayList();
        try {
            ArrayList<ProgramDTO> allPrograms = programBO.getAllPrograms();
            for (ProgramDTO program : allPrograms) {
                programs.add(program.getName());
            }
            cmbSelectCourse.setItems(programs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
