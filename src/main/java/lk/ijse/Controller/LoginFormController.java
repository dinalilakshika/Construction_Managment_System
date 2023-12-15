package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Model.UserModel;
import lk.ijse.dto.UserDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginFormController {

    public AnchorPane root;
    public JFXButton btnLogin;

    @FXML
    private TextField txtusername;

    @FXML
    private Label lbltime;

    @FXML
    private PasswordField btnpassword;
    private UserModel userModel = new UserModel();

    UserDto userDto = new UserDto();

    public void initialize() {
        setDate();
    }

    private void setDate() {
        lbltime.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
       // String username = txtusername.getText();
        userDto.setUsername(txtusername.getText());
        userDto.setPassword(btnpassword.getText());

        try {
            boolean isUpdate  = userModel.searchUser(userDto);

            if(isUpdate){

                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"))));
                stage.show();
                stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

