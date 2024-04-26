package org.example.quanlykhohang.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.*;

public class NhaCungCapController {
    NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button importExcelBtn;
    @FXML
    private Button exportExcelBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private TextField searchTxt;

    @FXML
    private TableView<NhaCungCap> providerTable;
    @FXML
    private void onAddBtnClick(){
        try {
        // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-them-nha-cung-cap-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();

            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);
            ThemNhaCungCapController controller = fxmlLoader.getController();
            controller.setNhaCungCapController(this);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show(); // Show the stage
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onEditBtnClick(){
        NhaCungCap selectedRow = providerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-nha-cung-cap-view.fxml"));
                // Load the root pane
                Pane item = fxmlLoader.load();

                // Get the controller of the loaded FXML file
                SuaNhaCungCapController controller = fxmlLoader.getController();
                controller.initData(selectedRow);
                controller.setNhaCungCapController(this);

                // Create a new scene with the loaded pane
                Scene scene = new Scene(item);

                // Create a new stage and set the scene
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show(); // Show the stage
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Hiển thị thông báo nếu không có hàng nào được chọn
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một nhà cung cấp để sửa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onDeleteBtnClick(){
        NhaCungCap selectedRow = (NhaCungCap)providerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa nhà cung cấp này?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                nhaCungCapDAO.delete(selectedRow.getMaNhaCungCap());
                providerTable.getItems().remove(selectedRow);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một nhà cung cấp để xóa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){}
    @FXML
    private void onResetBtnClick(){
        resetData();
    }
    @FXML
    private void onSearchTxtAction(){
        providerTable.setItems(searchNhaCungCap(searchTxt.getText()));
    }
    @FXML
    private void onFilterCbboxAction(){}
    @FXML
    private void initialize(){
        providerTable.getColumns().clear();
        TableColumn<NhaCungCap, String> idColumn = new TableColumn<>("Mã nhà cung cấp");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maNhaCungCap"));

        TableColumn<NhaCungCap, String> nameColumn = new TableColumn<>("Tên nhà cung cấp");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tenNhaCungCap"));

        TableColumn<NhaCungCap, String> sdtColumn = new TableColumn<>("Số điện thoại");
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));

        TableColumn<NhaCungCap, String> diaChiColumn = new TableColumn<>("Địa chỉ");
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        TableColumn<NhaCungCap, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        providerTable.getColumns().addAll(idColumn, nameColumn, sdtColumn, diaChiColumn, emailColumn);
        providerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                NhaCungCap selectedRow = (NhaCungCap)providerTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getMaNhaCungCap());
                }
            }
        });

        providerTable.setItems(getAllNhaCungCap());

    }
    public void resetData(){
        System.out.println("Reset");
        providerTable.setItems(getAllNhaCungCap());
        searchTxt.clear();
    }

    private ObservableList<NhaCungCap> getAllNhaCungCap() {
        ObservableList<NhaCungCap> data = FXCollections.observableArrayList();

        List<NhaCungCap> nhaCungCapList = nhaCungCapDAO.findAll();

        for (NhaCungCap ncc : nhaCungCapList) {
            // Set other fields accordingly
            data.add(ncc);
        }

        return data;
    }
    private ObservableList<NhaCungCap> searchNhaCungCap(String keyword) {
        ObservableList<NhaCungCap> data = FXCollections.observableArrayList();
        List<NhaCungCap> nhaCungCapList = nhaCungCapDAO.findByKeyword(keyword);
        for (NhaCungCap ncc : nhaCungCapList) {
            data.add(ncc);
        }

        return data;
    }
}
