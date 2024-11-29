package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.ProgramBO;
import lk.ijse.service.custom.RegisterBO;
import lk.ijse.service.custom.StudentBO;
import lk.ijse.tm.AddToCartTM;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationFormController implements Initializable {

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
    private TableColumn<?, ?> colRegistrationId;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<AddToCartTM> tblAddToCart;

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

    @FXML
    private TextField txtRemainFee;

    ProgramBO programBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ProgramBO);
    RegisterBO registerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RegisterBO);
    StudentBO studentBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.StudentBO);

    private ObservableList<AddToCartTM> addToCartList = FXCollections.observableArrayList();
    private double netPayment = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllPrograms();
        generateNextRegistrationId();
        setCellValueFactory();

        cmbSelectCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            }
        });

        txtAdvancePayment.textProperty().addListener((observable, oldValue, newValue) -> calculateRemainingFee());
    }


    private void setCellValueFactory() {
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("courseFee"));
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("advance"));
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
        if (txtRegistrationId.getText().isEmpty() ||
                txtStudentId.getText().isEmpty() ||
                txtCourseId.getText().isEmpty() ||
                txtCourseFee.getText().isEmpty() ||
                txtAdvancePayment.getText().isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Please fill all the required fields.").show();
            return;
        }
        calculateRemainingFee();

        try {
            String registrationId = txtRegistrationId.getText();
            String studentId = txtStudentId.getText();
            String courseId = txtCourseId.getText();
            double courseFee = Double.parseDouble(txtCourseFee.getText());
            double advancePayment = Double.parseDouble(txtAdvancePayment.getText());

            JFXButton removeButton = new JFXButton("Remove");
            removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            removeButton.setCursor(Cursor.HAND);

            AddToCartTM addToCartItem = new AddToCartTM(
                    registrationId,
                    studentId,
                    courseId,
                    courseFee,
                    advancePayment,
                    removeButton
            );

            addToCartList.add(addToCartItem);

            tblAddToCart.setItems(addToCartList);

            removeButton.setOnAction(e -> {
                addToCartList.remove(addToCartItem);
                tblAddToCart.refresh();
            });
            clearInputFields();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format. Please check the input values.").show();
        }
    }


    private void clearInputFields() {
        txtCourseId.clear();
        txtCourseFee.clear();
        txtAdvancePayment.clear();
        txtRegistrationId.clear();
        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentEmail.clear();
        txtStudentContact.clear();
        txtDuration.clear();
        txtAvailableSeats.clear();
        cmbSelectCourse.getSelectionModel().clearSelection();
    }

    private void calculateRemainingFee() {
        try {
            if (!txtCourseFee.getText().isEmpty() && !txtAdvancePayment.getText().isEmpty()) {
                double courseFee = Double.parseDouble(txtCourseFee.getText());
                double advancePayment = Double.parseDouble(txtAdvancePayment.getText());
                double remainingFee = courseFee - advancePayment;

                if (remainingFee < 0) {
                    new Alert(Alert.AlertType.WARNING, "Advance payment cannot exceed course fee!").show();
                    txtAdvancePayment.clear();
                    txtRemainFee.clear();
                } else {
                    txtRemainFee.setText(String.format("%.2f", remainingFee));
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format. Please check the inputs.").show();
            txtRemainFee.clear();
        }
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
