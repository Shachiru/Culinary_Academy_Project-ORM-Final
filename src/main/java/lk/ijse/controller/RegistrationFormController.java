package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.ProgramBO;
import lk.ijse.service.custom.RegisterBO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistrationFormController implements Initializable {

    ProgramBO programBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ProgramBO);
    RegisterBO registerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RegisterBO);

    ProgramDTO programDTO;

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

    @FXML
    void cmbSelectCourseOnAction(ActionEvent event) {
        try {
            programDTO = programBO.searchProgram(cmbSelectCourse.getValue());
            if (programDTO != null) {
                txtCourseId.setText(programDTO.getId());
                txtCourseFee.setText(String.valueOf(programDTO.getFee()));
                txtDuration.setText(String.valueOf(programDTO.getDuration()));
                txtAvailableSeats.setText(String.valueOf(programDTO.getSeats()));
            } else {
                System.out.println("Program not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllPrograms();
        generateNextRegistrationId();
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
