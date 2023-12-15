package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;


public class ManageAttendanceFormController {

    @FXML
    private JFXButton btnback;

    @FXML
    private JFXButton btnmark;

    @FXML
    private JFXButton btnmrkallattendance;

    @FXML
    private JFXComboBox<?> cmbemployeeId;

    @FXML
    private TableColumn<?, ?> colaction;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private Label lblemployeeName;

    @FXML
    private AnchorPane root;

    public void btnbackOnAction(ActionEvent actionEvent) {
    }

    public void btnmarkOnAction(ActionEvent actionEvent) {
    }

    public void cmbemployeeIdOnAction(ActionEvent actionEvent) {
    }

    public void btnmrkallattendanceOnAction(ActionEvent actionEvent) {
    }
}
