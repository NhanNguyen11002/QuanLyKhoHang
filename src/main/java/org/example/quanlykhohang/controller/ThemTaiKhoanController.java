/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.Gender;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.Role;
import org.example.quanlykhohang.entity.TaiKhoan;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ThemTaiKhoanController implements Initializable {
    @FXML
    private TextField  lastNameTxt;
    @FXML
    private  TextField firstNameTxt;
    @FXML
    private  TextField phoneTxt;
    @FXML
    private  TextField addressTxt;
    @FXML
    private  TextField emailTxt;
    @FXML
    private  DatePicker startDatePicker;
    @FXML
    private  DatePicker endDatePicker;
    @FXML
    private  DatePicker birthdayPicker;
    @FXML
    private  ChoiceBox genderChoiceBox;
    @FXML
    private  TextField usernameTxt;
    @FXML
    private  TextField passwordTxt;
    @FXML
    private  ChoiceBox roleChoiceBox;
    @FXML
    private  Button saveButton;
    @FXML
    private  Button cancelButton;
    @FXML
    private void onSaveButtonClick(){
        String ho = lastNameTxt.getText();
        String ten = firstNameTxt.getText();
        String sdt = phoneTxt.getText();
        String diaChi = addressTxt.getText();
        String email = emailTxt.getText();
        LocalDate ngayBatDau = startDatePicker.getValue();
        LocalDate ngayKetThuc = endDatePicker.getValue();
        LocalDate ngaySinh = birthdayPicker.getValue();
        Gender gioiTinh = (Gender) genderChoiceBox.getValue();
        String tenTaiKhoan = usernameTxt.getText();
        String matKhau = passwordTxt.getText();
        Role vaiTro = (Role)roleChoiceBox.getValue();

        // Kiểm tra xem các trường bắt buộc đã được điền đầy đủ chưa
        if (ho.isEmpty() || ten.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || email.isEmpty() ||
                ngayBatDau == null || ngayKetThuc == null || ngaySinh == null || gioiTinh == null ||
                tenTaiKhoan.isEmpty() || matKhau.isEmpty() || vaiTro == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }
        // Tạo một đối tượng Nhân viên
        NhanVien nhanVien = new NhanVien(ten, ho, ngaySinh, gioiTinh, ngayBatDau, ngayKetThuc, sdt, diaChi, email);
        NhanVienDAO nhanVienDAO = new NhanVienDAO();
        nhanVienDAO.create(nhanVien);
        // Tạo một đối tượng TaiKhoan
        TaiKhoan taiKhoan = new TaiKhoan(tenTaiKhoan, matKhau, vaiTro, true, nhanVien);
        // Thiết lập quan hệ giữa nhân viên và tài khoản
        taiKhoan.setNhanVien(nhanVien);
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

        // Lưu tài khoản và nhân viên vào cơ sở dữ liệu
        taiKhoanDAO.create(taiKhoan);

        // Hiển thị thông báo thành công
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Tạo tài khoản và nhân viên thành công.");
        alert.showAndWait();

        // Đóng cửa sổ
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Khởi tạo choice box giới tính
        genderChoiceBox.getItems().addAll(Gender.Male, Gender.Female);

        // Khởi tạo choice box vai trò
        roleChoiceBox.getItems().addAll(Role.Admin, Role.Staff);
    }    
    
}
