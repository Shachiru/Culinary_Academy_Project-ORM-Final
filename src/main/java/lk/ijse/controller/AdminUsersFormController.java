package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.dto.UserDTO;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.UserBO;
import lk.ijse.tm.ProgramTM;
import lk.ijse.tm.UserTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminUsersFormController implements Initializable {

    @FXML
    private Label UserMobileValidate;

    @FXML
    private Label UserNameValidate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMobile;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label userAddressValidate;

    @FXML
    private Label userEmailValidate;

    @FXML
    private Label userIdValidate;

    @FXML
    private Label userPasswordValidate;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    UserBO userBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.UserBO);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hoverText();
        initUI();
        loadAllUsers();
        generateNextUserId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void generateNextUserId() {
        try {
            txtUserId.setText(userBO.generateNextUserId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllUsers() {
        ObservableList<UserTM> obList = FXCollections.observableArrayList();
        tblUser.getItems().clear();
        try {
            List<UserDTO> list = userBO.getAllUsers();
            for (UserDTO dto : list) {
                UserTM userTM = new UserTM(
                        dto.getId(),
                        dto.getName(),
                        dto.getPassword(),
                        dto.getAddress(),
                        dto.getMobile(),
                        dto.getEmail()
                );
                obList.add(userTM);
            }
            tblUser.setItems(obList);
            tblUser.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initUI() {
        txtUserId.setEditable(false);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        if (ButtonType.OK == new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clear all fields?").showAndWait().get()) {
            clearFields();
        }
    }

    private void clearFields() {
        txtUserId.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtAddress.clear();
        txtMobile.clear();
        txtEmail.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String id = txtUserId.getText();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        boolean deleted = userBO.deleteUser(userDTO);
        if (deleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully").show();
            loadAllUsers();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        if (txtUserId.getText().isEmpty()
                || txtUsername.getText().isEmpty()
                || txtPassword.getText().isEmpty()
                || txtAddress.getText().isEmpty()
                || txtMobile.getText().isEmpty()
                || txtEmail.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all the fields").show();
        } else {
            String id = txtUserId.getText();
            String name = txtUsername.getText();
            String password = txtPassword.getText();
            String address = txtAddress.getText();
            String mobile = txtMobile.getText();
            String email = txtEmail.getText();

            UserDTO userDTO = new UserDTO(id, name, password, address, mobile, email);
            boolean saved = userBO.saveUser(userDTO);
            if (saved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully").show();
                loadAllUsers();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String id = txtUserId.getText();
        String name = txtUsername.getText();
        String password = txtPassword.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();

        UserDTO userDTO = new UserDTO(id, name, password, address, mobile, email);
        boolean updated = userBO.updateUser(userDTO);
        if (updated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully").show();
            loadAllUsers();
        }
    }

    @FXML
    void tblClick(MouseEvent event) {
        TablePosition pos = tblUser.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<UserTM, ?>> columns = tblUser.getColumns();

        txtUserId.setText(columns.get(0).getCellData(row).toString());
        txtUsername.setText(columns.get(1).getCellData(row).toString());
        txtPassword.setText(columns.get(2).getCellData(row).toString());
        txtAddress.setText(columns.get(3).getCellData(row).toString());
        txtMobile.setText(columns.get(4).getCellData(row).toString());
        txtEmail.setText(columns.get(5).getCellData(row).toString());
    }

    private void hoverText() {
        btnSave.setTooltip(new Tooltip("Save"));
        btnUpdate.setTooltip(new Tooltip("Update"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnClear.setTooltip(new Tooltip("Clear"));
    }
}
