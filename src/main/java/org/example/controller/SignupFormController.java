package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.dto.AdminDto;
import org.example.model.AdminModel;
import org.example.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class SignupFormController {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane root;

    @FXML
    void hyperLoginHereOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_page.fxml"));

        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Login Form");
    }
    public void btnCreateAccountOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String u_name = txtUserName.getText();
        String u_password = txtPw.getText();
        String u_email= txtEmail.getText();

        // validate fields

        if (u_name == null){
            new Alert(Alert.AlertType.ERROR,"username can't empty").show();
            return;
        }

        AdminDto AdminDto = new AdminDto(
                u_name,
                u_password,
                u_email
        );

        if (AdminModel.saveUser(AdminDto)){
            new Alert(Alert.AlertType.INFORMATION,"User Account created..!").show();
            txtUserName.setText(null);
            txtPw.setText(null);
            txtEmail.setText(null);
        }else {
            new Alert(Alert.AlertType.WARNING,"User Account creation fail..!").show();
        }

    }
}

