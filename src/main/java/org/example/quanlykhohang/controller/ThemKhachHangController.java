/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.KhachHangDAO;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.*;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ThemKhachHangController {
    private KhachHangController khachHangController;
    @FXML
    private TextField  nameCustomerTxt;
    @FXML
    private  TextField phoneTxt;
    @FXML
    private  TextField emailTxt;
    @FXML
    private  TextField addressTxt;
    @FXML
    private  Button saveButton;
    @FXML
    private  Button cancelButton;
    @FXML
    private void onSaveButtonClick(){
        String ten = nameCustomerTxt.getText();
        String sdt = phoneTxt.getText();
        String diaChi = addressTxt.getText();
        String email = emailTxt.getText();

        // Kiểm tra xem các trường bắt buộc đã được điền đầy đủ chưa
        if ( ten.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }
        KhachHangDAO khachHangDAO = new KhachHangDAO();

        boolean isExistByEmail = khachHangDAO.existsByEmail(email);
        if(isExistByEmail){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã có khách hàng với email được nhập tồn tại trong hệ thống");
            alert.showAndWait();
            return;
        }
        boolean isExistBySDT = khachHangDAO.existsBySDT(sdt);
        if(isExistBySDT){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã có khách hàng với SĐT được nhập tồn tại trong hệ thống");
            alert.showAndWait();
            return;
        }
        try {
            KhachHang khachHang = new KhachHang( ten, sdt, diaChi, email);

            khachHangDAO.create(khachHang);
            khachHangController.resetData();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Tạo khách hàng thành công.");
            alert.showAndWait();

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

    }
    @FXML
    private void onCancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // Đóng cửa sổ
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void setKhachHangController(KhachHangController khachHangController){
        this.khachHangController = khachHangController;
    }

}
