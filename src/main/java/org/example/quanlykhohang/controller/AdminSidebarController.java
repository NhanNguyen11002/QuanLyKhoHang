package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.example.quanlykhohang.Main;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.entity.UserSession;

public class AdminSidebarController {
    @FXML
    private Pane productPane;
    @FXML
    private Pane providerPane;
    @FXML
    private Pane customerPane;
    @FXML
    private Pane exportFormPane;
    @FXML
    private Pane importTicketPane;
    @FXML
    private Pane exportTicketPane;
    @FXML
    private Pane storagePane;
    @FXML
    private Pane accountPane;
    @FXML
    private Pane statisticPane;
    @FXML
    private Pane logoutPane;
    @FXML
    private Pane informationPane;
    @FXML
    private Pane mainPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label helloLabel;
    @FXML
    private void onProductPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/san-pham-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(productPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onCustomerPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/khach-hang-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(customerPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onProviderPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/nha-cung-cap-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(providerPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    private void onExportFormPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/don-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(exportFormPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onImportTicketPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-nhap-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(importTicketPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onExportTicketPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(exportTicketPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onStoragePaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/ton-kho-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(storagePane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onAccountPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/tai-khoan-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(accountPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onStatisticPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/thong-ke-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(statisticPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onLogoutPaneClick(){
        showConfirm("Bạn có chắc chắn muốn đăng xuất?", () -> {
            // Nếu người dùng chọn Yes (OK), thực hiện các hành động sau
            // Reset thông tin của UserSession
            UserSession.getInstance().clearSession();

            try {
                // Load FXML file for login page
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/dang-nhap-view.fxml"));
                Parent root = fxmlLoader.load();

                // Create a new scene with the loaded root
                Scene scene = new Scene(root);

                // Get the current stage and set the scene
                Stage stage = (Stage) logoutPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private void onInformationPaneClick(){
        try {
        // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-thong-tin-ca-nhan-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();
            SuaThongTinCaNhanController suaThongTinCaNhanController = fxmlLoader.getController();
            NhanVienDAO nhanVienDao = new NhanVienDAO();
            suaThongTinCaNhanController.setUserInfo(nhanVienDao.findById(UserSession.getInstance().getMaNhanVien()));
            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.show(); // Show the stage
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize() {
        helloLabel.setText("Hello, "+UserSession.getInstance().getUserName());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/san-pham-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(productPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void showConfirm(String message, Runnable onConfirmation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Nếu người dùng chọn OK (Yes), thực hiện hành động được định nghĩa trong hàm onConfirmation
            onConfirmation.run();
        } else {
            // Người dùng chọn Cancel (No), không thực hiện hành động nào
        }
    }
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void applyStyle(Pane pane){
        productPane.getStyleClass().clear();
        productPane.getStyleClass().add("pane-btn");
        providerPane.getStyleClass().clear();
        providerPane.getStyleClass().add("pane-btn");
        customerPane.getStyleClass().clear();
        customerPane.getStyleClass().add("pane-btn");
        exportFormPane.getStyleClass().clear();
        exportFormPane.getStyleClass().add("pane-btn");
        importTicketPane.getStyleClass().clear();
        importTicketPane.getStyleClass().add("pane-btn");
        exportTicketPane.getStyleClass().clear();
        exportTicketPane.getStyleClass().add("pane-btn");
        storagePane.getStyleClass().clear();
        storagePane.getStyleClass().add("pane-btn");
        accountPane.getStyleClass().clear();
        accountPane.getStyleClass().add("pane-btn");
        statisticPane.getStyleClass().clear();
        statisticPane.getStyleClass().add("pane-btn");
        logoutPane.getStyleClass().clear();
        logoutPane.getStyleClass().add("logout-btn");
        informationPane.getStyleClass().clear();
        informationPane.getStyleClass().add("pane-btn");
        pane.getStyleClass().add("bg-teal");
    }

}
