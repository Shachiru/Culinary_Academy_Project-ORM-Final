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
import lk.ijse.dto.ProgramDTO;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.ProgramBO;
import lk.ijse.tm.ProgramTM;
import lk.ijse.util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProgramFormController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSeats;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<ProgramTM> tblProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtProgramName;

    @FXML
    private TextField txtProgramSeats;

    @FXML
    private Label prDurationValidate;

    @FXML
    private Label prFeeValidate;

    @FXML
    private Label prIdValidate;

    @FXML
    private Label prNameValidate;

    @FXML
    private Label prSeatValidate;

    ProgramBO programBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ProgramBO);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hoverText();
        initUI();
        loadAllPrograms();
        generateNextProgramId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void generateNextProgramId() {
        try {
            txtProgramId.setText(programBO.generateNextProgramId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initUI() {
        txtProgramId.setEditable(false);
    }

    private void hoverText() {
        btnSave.setTooltip(new Tooltip("Save"));
        btnUpdate.setTooltip(new Tooltip("Update"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnClear.setTooltip(new Tooltip("Clear"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        if (ButtonType.OK == new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clear all fields?").showAndWait().get()) {
            clearFields();
        }
    }

    private void clearFields() {
        txtProgramId.clear();
        txtProgramName.clear();
        txtProgramSeats.clear();
        txtDuration.clear();
        txtFee.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String id = txtProgramId.getText();
        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setPId(id);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Program");
        alert.setHeaderText("Are you sure you want to delete this program?");
        alert.setContentText("Program ID: " + id);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            boolean deleted = programBO.deleteProgram(programDTO);
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully").show();
                loadAllPrograms();
                clearFields();
            }
        }
    }

    private void loadAllPrograms() {
        ObservableList<ProgramTM> obList = FXCollections.observableArrayList();
        tblProgram.getItems().clear();
        try {
            List<ProgramDTO> list = programBO.getAllPrograms();
            for (ProgramDTO dto : list) {
                ProgramTM programTM = new ProgramTM(
                        dto.getPId(),
                        dto.getName(),
                        dto.getSeats(),
                        dto.getDuration(),
                        dto.getFee()
                );
                obList.add(programTM);
            }
            tblProgram.setItems(obList);
            tblProgram.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        if (validateInputFields()) {
            String id = txtProgramId.getText();
            String name = txtProgramName.getText();
            String seats = txtProgramSeats.getText();
            String duration = txtDuration.getText();
            double fee = Double.parseDouble(txtFee.getText());

            ProgramDTO programDTO = new ProgramDTO(id, name, seats, duration, fee);
            boolean saved = programBO.saveProgram(programDTO);
            if (saved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully").show();
                loadAllPrograms();
                clearFields();
                generateNextProgramId();
            }
        }
    }

    private boolean validateInputFields() {
        boolean isValid = true;

        if (!Validation.programIdValidate(txtProgramId.getText())) {
            prIdValidate.setText("Invalid Program ID");
            isValid = false;
        } else {
            prIdValidate.setText("");
        }

        if (!Validation.nameValidate(txtProgramName.getText())) {
            prNameValidate.setText("Invalid Program Name");
            isValid = false;
        } else {
            prNameValidate.setText("");
        }

        if (!Validation.qtyValidate(txtProgramSeats.getText())) {
            prSeatValidate.setText("Invalid Seats");
            isValid = false;
        }else {
            prSeatValidate.setText("");
        }

        if (!Validation.durationValidate(txtDuration.getText())) {
            prDurationValidate.setText("Invalid Duration");
            isValid = false;
        } else {
            prDurationValidate.setText("");
        }

        if (!Validation.feeValidate(txtFee.getText())) {
            prFeeValidate.setText("Invalid Fee");
            isValid = false;
        } else {
            prFeeValidate.setText("");
        }
        return isValid;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String id = txtProgramId.getText();
        String name = txtProgramName.getText();
        String seats = txtProgramSeats.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtFee.getText());

        ProgramDTO programDTO = new ProgramDTO(id, name, seats, duration, fee);
        boolean updated = programBO.updateProgram(programDTO);
        if (updated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully").show();
            loadAllPrograms();
            clearFields();
        }
    }

    @FXML
    void tableClick(MouseEvent event) {
        TablePosition pos = tblProgram.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<ProgramTM, ?>> columns = tblProgram.getColumns();

        txtProgramId.setText(columns.get(0).getCellData(row).toString());
        txtProgramName.setText(columns.get(1).getCellData(row).toString());
        txtProgramSeats.setText(columns.get(2).getCellData(row).toString());
        txtDuration.setText(columns.get(3).getCellData(row).toString());
        txtFee.setText(columns.get(4).getCellData(row).toString());
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }
}
