package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.dto.StudentDTO;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.StudentBO;
import lk.ijse.tm.StudentTM;
import lk.ijse.util.Validation;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TextField txtStudentId;

    @FXML
    private Label stAddressValidate;

    @FXML
    private Label stContactValidate;

    @FXML
    private Label stEmailValidate;

    @FXML
    private Label stIdValidate;

    @FXML
    private Label stNameValidate;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<StudentTM> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtStudentName;

    StudentBO studentBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.StudentBO);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hoverText();
        setCellValueFactory();
        loadAllStudents();
        generateNextStudentId();
        initUI();
    }

    private void initUI() {
        txtStudentId.setEditable(false);
    }

    private void generateNextStudentId() {
        try {
            txtStudentId.setText(studentBO.generateNextStudentId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        if (ButtonType.OK == new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to clear all fields?")
                .showAndWait().get()) {
            clearFields();
        }
    }

    private void clearFields() {
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String id = txtStudentId.getText();
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("Are you sure you want to delete this student?");
        alert.setContentText("Student ID: " + id);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            boolean deleted = studentBO.deleteStudent(studentDTO);
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully").show();
                loadAllStudents();
                clearFields();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        if (ValidateInputFields()) {
            String id = txtStudentId.getText();
            String name = txtStudentName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();

            StudentDTO studentDTO = new StudentDTO(id, name, address, contact, email);
            boolean saved = studentBO.saveStudent(studentDTO);
            if (saved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully").show();
                loadAllStudents();
                clearFields();
                generateNextStudentId();
            }
        }
    }

    private boolean ValidateInputFields() {
        boolean isValid = true;

        if (!Validation.studentIdValidate(txtStudentId.getText())) {
            stIdValidate.setText("Invalid Student ID");
            isValid = false;
        } else {
            stIdValidate.setText("");
        }

        if (!Validation.nameValidate(txtStudentName.getText())) {
            stNameValidate.setText("Invalid Student Name");
            isValid = false;
        } else {
            stNameValidate.setText("");
        }

        if (!Validation.addressValidate(txtAddress.getText())) {
            stAddressValidate.setText("Invalid Address");
            isValid = false;
        } else {
            stAddressValidate.setText("");
        }

        if (!Validation.mobileValidate(txtContact.getText())) {
            stContactValidate.setText("Invalid Contact");
            isValid = false;
        } else {
            stContactValidate.setText("");
        }

        if (!Validation.emailCheck(txtEmail.getText())) {
            stEmailValidate.setText("Invalid Email");
            isValid = false;
        }
        return isValid;
    }

    private void loadAllStudents() {
        ObservableList<StudentTM> obList = FXCollections.observableArrayList();
        tblStudent.getItems().clear();
        try {
            List<StudentDTO> list = studentBO.getAllStudents();
            for (StudentDTO dto : list) {
                StudentTM studentTM = new StudentTM(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getContact(),
                        dto.getEmail()
                );
                obList.add(studentTM);
            }
            tblStudent.setItems(obList);
            tblStudent.refresh();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        StudentDTO studentDTO = new StudentDTO(id, name, address, contact, email);
        boolean updated = studentBO.updateStudent(studentDTO);
        if (updated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully").show();
            loadAllStudents();
            clearFields();
        }
    }

    @FXML
    void tableClick(MouseEvent event) {
        TablePosition pos = tblStudent.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<StudentTM, ?>> columns = tblStudent.getColumns();

        txtStudentId.setText(columns.get(0).getCellData(row).toString());
        txtStudentName.setText(columns.get(1).getCellData(row).toString());
        txtAddress.setText(columns.get(2).getCellData(row).toString());
        txtContact.setText(columns.get(3).getCellData(row).toString());
        txtEmail.setText(columns.get(4).getCellData(row).toString());
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void hoverText() {
        btnSave.setTooltip(new Tooltip("Save"));
        btnUpdate.setTooltip(new Tooltip("Update"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnClear.setTooltip(new Tooltip("Clear"));
    }
}