package lk.ijse.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.service.BOFactory;
import lk.ijse.service.custom.DashboardBO;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@Slf4j
public class AdminDashboardFormController implements Initializable {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblProgramCount;

    @FXML
    private Label lblRegisterCount;

    @FXML
    private Label lblStudentCount;

    @FXML
    private Label lblTime;

    @FXML
    private Pane pagingPane;

    DashboardBO dashboardBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DashboardBO);

    private volatile boolean running = true;

    private void timeNow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM, dd, yyyy");
            while (running) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String timeNow = sdf.format(new Date());
                String timeNow1 = sdf1.format(new Date());

                Platform.runLater(() -> {
                    lblTime.setText(timeNow);
                    lblDate.setText(timeNow1);
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeNow();
        studentCount();
        programCount();
    }

    private void programCount() {
        lblProgramCount.setText(String.valueOf(dashboardBO.programCount()));
    }

    private void studentCount() {
        lblStudentCount.setText(String.valueOf(dashboardBO.studentCount()));
    }

    public void stop() {
        running = false; // Stop the loop
    }
}
