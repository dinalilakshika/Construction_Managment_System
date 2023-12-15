package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Model.SupplierModel;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.SupplierTm;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;


public class SupplierFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;
    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> coldescription;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private JFXTextField txtcontactnumber;

    @FXML
    private JFXTextField txtdes;

    @FXML
    private JFXTextField txtsupplierId;

    @FXML
    private JFXTextField txtsupplierrname;

    @FXML
    private Label lblid;

    private SupplierModel supplierModel = new SupplierModel();

    public void initialize() {
       // generateNextSupplierId();
        setCellValueFactory();
        loadAllSupplier();
    }

    private void generateNextSupplierId() {
        try {
            String id = SupplierModel.generateNextSupplierId();
            txtsupplierId.setText(id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateSupplier() {
        boolean isValidate = true;
        boolean name = Pattern.matches("[A-Za-z]{5,}", txtsupplierrname.getText());
        if (!name){
            showErrorNotification("Invalid supplier Name", "The supplier name you entered is invalid");
            isValidate = false;
        }
        boolean des = Pattern.matches("[A-Za-z]{5,}",txtdes.getText());
        if(!des){
            showErrorNotification("invalid description","The description you entered is invalid");
            isValidate = false;
        }
        boolean tel = Pattern.matches("^([0-9]{9}|[0-9]{10})$",txtcontactnumber.getText());
        if (!tel){
            showErrorNotification("Invalid Contact Number", "The Contact Number you entered is invalid");
            isValidate = false;
        }
        return isValidate;
    }

    private void showErrorNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showError();
    }




    private void loadAllSupplier() {
        var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = model.getAllSupplier();

            for (SupplierDto dto : dtoList) {
                obList.add(
                        new SupplierTm(
                                dto.getId(),
                                dto.getTel(),
                                dto.getName(),
                                dto.getDescription()
                        )
                );
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("descrption"));
    }




    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtsupplierId.getText();
        String tel = txtcontactnumber.getText();
        String name = txtsupplierrname.getText();
        String descrption = txtdes.getText();

        var dto = new SupplierDto(id,tel,name,descrption);


        try {
            if (!validateSupplier()){
                return;
            }
            boolean isSaved = SupplierModel.saveSupplier(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        new Alert(Alert.AlertType.INFORMATION, "ID no not validate");


    }

    private void clearFields() {
        txtsupplierId.setText("");
        txtcontactnumber.setText("");
        txtsupplierrname.setText("");
        txtdes.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String id = txtsupplierId.getText();
        String tel = txtcontactnumber.getText();
        String name = txtsupplierrname.getText();
        String descrption = txtdes.getText();

        var dto = new SupplierDto(id,tel,name,descrption);


        try {
            boolean isUpdated = supplierModel.updateSupplier(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtsupplierId.getText();

        try {
            boolean isDeleted = supplierModel.deleteSupplier(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }


    public void seachsupplierOnAction(ActionEvent actionEvent) {
        String id = txtsupplierId.getText();

//        var model = new CustomerModel();
        try {
            SupplierDto supplierDto =supplierModel.searchSupplierr(id);
//            System.out.println(customerDto);
            if (supplierDto != null) {
                txtsupplierId.setText(supplierDto.getId());
                txtcontactnumber.setText(supplierDto.getTel());
                txtsupplierrname.setText(supplierDto.getName());
               txtdes.setText(supplierDto.getDescription());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Supplier not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    }

