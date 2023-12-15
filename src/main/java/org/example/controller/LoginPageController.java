package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.model.AdminModel;
import org.example.model.UserModel;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {
    @FXML
    public Button login;

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField txtUserPW;

    @FXML
    private TextField txtUserName;
    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("SignUp Form");
            stage.centerOnScreen();
            stage.show();
        }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

            String enteredUserName = txtUserName.getText();
            String enteredPassword = txtUserPW.getText();



        if(enteredUserName.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "username required..!!", ButtonType.OK).show();
        }else if(enteredPassword.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "password required..!!", ButtonType.OK).show();
        } else {
            try {
                ResultSet resultSet = AdminModel.checkCredentials(enteredUserName, enteredPassword);

                if (resultSet.next()) {
                    String name = resultSet.getString(1);
                    String pass = resultSet.getString(2);
                    String email = resultSet.getString(2);


                    if (pass.equals(enteredPassword) & name.equals(enteredUserName)) {

                        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/admin_navigationbar_form.fxml"));
                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) rootNode.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("Admin Form");
                        stage.centerOnScreen();
                        stage.show();


                    } else {
                        new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
                    }
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
}
}
          /*  try {
                boolean useIsExist = UserModel.isExistUser(enteredUserName,enteredPassword);
                if (useIsExist){

                    navigateToCashierNavigationFormWindow();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }*/
        }
    }

