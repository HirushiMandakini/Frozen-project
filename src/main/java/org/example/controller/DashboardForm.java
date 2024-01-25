package org.example.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.model.CustomerFormModel;
import org.example.model.EmployeeFormModel;
import org.example.model.ProductFormModel;
import org.example.model.SupplierFormModel;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardForm implements Initializable {
    public AnchorPane subRoot;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblCusCount;
    @FXML
    public Label lblEmpCount;
    @FXML
   public AnchorPane subRoot1;
    @FXML
    private Label lblProductCount;

    @FXML
    private Label lblSupCount;

    private void setValues() throws SQLException {
        lblCusCount.setText(String.valueOf(CustomerFormModel.getAllCustomer().size()));
       // lblEmpCount.setText(String.valueOf(EmployeeFormModel.getAllEmployee().size()));
        lblSupCount.setText(String.valueOf(SupplierFormModel.getAllSupplier().size()));
     //   lblEmpCount.setText(String.valueOf(EmployeeFormModel.getAllEmployee().size()));
        lblProductCount.setText(String.valueOf(ProductFormModel.loadAllProduct().size()));

    }

    private void loadDateandTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(format.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalTime.now().format(format2));
        }), new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());
        loadDateandTime();
        try {
            setValues();
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}





