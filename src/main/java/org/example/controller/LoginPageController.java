package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;
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

    private final String adminUserName = "admin";
    private final String adminPassword = "a1234";
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

            if(enteredUserName.equals(adminUserName) && enteredPassword.equals(adminPassword)){
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/admin_navigationbar_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) rootNode.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Admin Form");
                stage.centerOnScreen();
                stage.show();

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
        private void navigateToCashierNavigationFormWindow() throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/cashier_navigationbar_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Cashier Form");
            stage.centerOnScreen();
            stage.show();
}
    }

