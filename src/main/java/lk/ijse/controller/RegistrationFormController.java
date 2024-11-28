package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RegistrationFormController {

    @FXML
    private JFXButton btnAddNewCourse;

    @FXML
    private JFXButton btnAddNewStudent;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnBuyCourse;

    @FXML
    private JFXButton btnClear;

    @FXML
    private Button btnStudentSearch;

    @FXML
    private ComboBox<?> cmbSelectCourse;

    @FXML
    private TableColumn<?, ?> colAdvance;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colCourseFee;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPurchaseId;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblNeedMoney;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblAddToCart;

    @FXML
    private TextField txtAdvancePayment;

    @FXML
    private TextField txtAvailableSeats;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtCourseFee;

    @FXML
    private TextField txtCourseId;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtPaymentAmount;

    @FXML
    private TextField txtPaymentBalance;

    @FXML
    private TextField txtPaymentTotal;

    @FXML
    private TextField txtPurchaseId;

    @FXML
    private TextField txtStudentContact;

    @FXML
    private TextField txtStudentEmail;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    @FXML
    void btnAddNewCourseOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddNewStudentOnAction(ActionEvent event) {

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

    @FXML
    void lblNeedMoneyOnAction(MouseEvent event) {

    }

}
