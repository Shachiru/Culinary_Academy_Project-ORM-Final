package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.ProgramBO;
import lk.ijse.service.custom.RegisterBO;
import lk.ijse.service.custom.StudentBO;
import lk.ijse.tm.AddToCartTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
    private ObservableList<AddToCartTM> addToCartList = FXCollections.observableArrayList();

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
        /*try {
            String registrationId = txtRegistrationId.getText();
            String studentId = txtStudentId.getText();
            String courseId = txtCourseId.getText();
            String courseFee = txtCourseFee.getText();
            double advance = Double.parseDouble(txtAdvancePayment.getText());

            LocalDate paymentDate = dpDate.getValue();
            java.sql.Date registerDate = java.sql.Date.valueOf(paymentDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(registerDate);
            calendar.add(Calendar.MONTH, 3);
            java.sql.Date expiredDate = new java.sql.Date(calendar.getTimeInMillis());

            JFXButton btnRemove = new JFXButton("Remove");
            btnRemove.setCursor(Cursor.HAND);
            btnRemove.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14px;");

            btnRemove.setOnAction(e -> {
                int selectedIndex = tblAddToCart.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to remove this item?", yes, no).showAndWait();

                    if (result.orElse(no) == yes) {
                        addToCartList.remove(selectedIndex);
                        tblAddToCart.refresh();
                        calculateTotalAmount();
                        calculateCustomerPaymentBalanceAmount();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "No item selected to remove!").show();
                }
            });
            for (AddToCartTM item : addToCartList) {
                if (item.getProgram_id() != null && item.getProgram_id().equals(courseId)) {
                    new Alert(Alert.AlertType.WARNING, "This course is already added to the cart!").show();
                    return;
                }
            }
            AddToCartTM addToCartTM = new AddToCartTM(
                    registrationId,
                    studentId,
                    courseId,
                    courseFee,
                    advance,
                    Double.parseDouble(courseFee) - Double.parseDouble(advance),
                    registerDate,
                    btnRemove
            );

            addToCartList.add(addToCartTM);
            tblAddToCart.setItems(addToCartList);
            tblAddToCart.refresh();
            calculateTotalAmount();
            calculateCustomerPaymentBalanceAmount();
        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while adding to cart").show();
        }*/
    }

    /*private void calculateCustomerPaymentBalanceAmount() {
        String paymentText = txtCustomerPaymentAmount.getText();
        String totalText = txtTotalAmount.getText();

        if (!paymentText.isEmpty() && !totalText.isEmpty()) {
            double payment = Double.parseDouble(paymentText);
            double total = Double.parseDouble(totalText);
            double balance = payment - total;

            txtCustomerPaymentBalance.setText(String.format("%.2f", balance));
        }
    }*/

    /*private void calculateTotalAmount() {
        double total = 0.0;
        for (AddToCartTM course : addToCartList) {
            total += course.getAdvanceAmount();
        }
        txtTotalAmount.setText(String.format("%.2f", total));
    }*/

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
