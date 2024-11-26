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
import lk.ijse.service.custom.impl.StudentBOImpl;
import lk.ijse.tm.StudentTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    void btnClearOnAction(ActionEvent event) {
        if (new Alert(Alert.AlertType.CONFIRMATION, "Do you want to clear the fields?", ButtonType.OK, ButtonType.CANCEL).showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Cleared Successfully!").show();
        }
    }

    private void clearFields() {
        txtStudentName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        if (txtStudentId.getText().isEmpty() || txtStudentName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtContact.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields are required!").show();
        }else{
            String id = txtStudentId.getText();
            String name = txtStudentName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();

            StudentDTO studentDTO = new StudentDTO(id, name, address, contact, email);
            boolean saved = studentBO.saveStudent(studentDTO);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully!").show();
                loadAllStudents();
            }
        }
    }

    private void loadAllStudents() {
        ObservableList<StudentTM> obList = FXCollections.observableArrayList();
        tblStudent.getItems().clear();

        try {
            ArrayList<StudentDTO> list = studentBO.getAllStudents();
            for (StudentDTO dto : list) {
                StudentTM studentTM = new StudentTM(
                        Long.parseLong(dto.getId()),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getContact(),
                        dto.getEmail()
                );
                obList.add(studentTM);

            }
            tblStudent.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hoverText();
        setCellValue();
        loadAllStudents();
    }

    private void setCellValue() {
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