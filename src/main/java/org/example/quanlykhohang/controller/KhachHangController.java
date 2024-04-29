package org.example.quanlykhohang.controller;


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
import org.example.quanlykhohang.dao.KhachHangDAO;
import org.example.quanlykhohang.entity.KhachHang;
import org.example.quanlykhohang.entity.NhaCungCap;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class KhachHangController {
    KhachHangDAO khachHangDAO = new KhachHangDAO();
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
    private TableView<KhachHang> customerTable;
    @FXML
    private void onAddBtnClick(){
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-them-khach-hang-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();

            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);
            ThemKhachHangController controller = fxmlLoader.getController();
            controller.setKhachHangController(this);

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
        KhachHang selectedRow = customerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-khach-hang-view.fxml"));
                // Load the root pane
                Pane item = fxmlLoader.load();

                // Get the controller of the loaded FXML file
                SuaKhachHangController controller = fxmlLoader.getController();
                controller.initData(selectedRow);
                controller.setKhachHangController(this);

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
            alert.setContentText("Vui lòng chọn một khách hàng để sửa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onDeleteBtnClick(){
        KhachHang selectedRow = customerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa khách hàng này?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                khachHangDAO.delete(selectedRow.getMaKhachHang());
                customerTable.getItems().remove(selectedRow);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Thông báo");
                alert1.setHeaderText(null);
                alert1.setContentText("Xoá khách hàng thành công.");
                alert1.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một khách hàng để xóa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){}
    @FXML
    private void onResetBtnClick(){ resetData();}
    @FXML
    private void onSearchTxtAction(){
        customerTable.setItems(searchKhachHang(searchTxt.getText()));
    }
    @FXML
    private void initialize(){
        customerTable.getColumns().clear();
        TableColumn<KhachHang, String> idColumn = new TableColumn<>("Mã khách hàng");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));

        TableColumn<KhachHang, String> nameColumn = new TableColumn<>("Tên khách hàng");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));

        TableColumn<KhachHang, String> sdtColumn = new TableColumn<>("Số điện thoại");
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));

        TableColumn<KhachHang, String> diaChiColumn = new TableColumn<>("Địa chỉ");
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        TableColumn<KhachHang, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        customerTable.getColumns().addAll(idColumn, nameColumn, sdtColumn, diaChiColumn, emailColumn);
        customerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                KhachHang selectedRow = (KhachHang)customerTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getMaKhachHang());
                }
            }
        });

        customerTable.setItems(getAllKhachHang());

    }
    public void resetData(){
        System.out.println("Reset");
        customerTable.setItems(getAllKhachHang());
        searchTxt.clear();
    }

    private ObservableList<KhachHang> getAllKhachHang() {
        ObservableList<KhachHang> data = FXCollections.observableArrayList();

        List<KhachHang> khachHangList = khachHangDAO.findAll();

        for (KhachHang kh : khachHangList) {
            // Set other fields accordingly
            data.add(kh);
        }

        return data;
    }
    private ObservableList<KhachHang> searchKhachHang(String keyword) {
        ObservableList<KhachHang> data = FXCollections.observableArrayList();
        List<KhachHang> khachHangList = khachHangDAO.findByKeyword(keyword);
        for (KhachHang kh : khachHangList) {
            data.add(kh);
        }

        return data;
    }
}

