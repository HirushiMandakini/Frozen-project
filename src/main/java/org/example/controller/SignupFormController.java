package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.dto.AdminDto;
import org.example.dto.UserDto;
import org.example.dto.driverDto;
import org.example.model.AdminModel;
import org.example.model.UserModel;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class SignupFormController {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtUserName;


    @FXML
    void hyperLoginHereOnAction(ActionEvent event) {

    }

    public void btnRegisterOnAction(javafx.event.ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String uname = txtUserName.getText();
        String password = txtPw.getText();

        var dto = new AdminDto(uname, password, email);

        try {
            boolean isSaved = AdminModel.saveUser(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "User Registered Successfully").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    public void hyperLoginHereOnAction(javafx.event.ActionEvent actionEvent) {

    }
}
