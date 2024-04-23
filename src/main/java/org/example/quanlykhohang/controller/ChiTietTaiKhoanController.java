/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.quanlykhohang.entity.TaiKhoanNhanVienDTO;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ChiTietTaiKhoanController implements Initializable {
    @FXML
    private TextField lastNameTxt;
    @FXML
    private TextField firstNameTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private DatePicker birthdayPicker;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField statusTxt;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private TextField idTxt;
    
    public void initData(TaiKhoanNhanVienDTO taiKhoan) {
        // Đặt giá trị cho các trường tương ứng từ đối tượng TaiKhoanNhanVienDTO
        idTxt.setText(String.valueOf(taiKhoan.getMaNhanVien()));
        lastNameTxt.setText(taiKhoan.getHo());
        firstNameTxt.setText(taiKhoan.getTen());
        roleChoiceBox.setValue(taiKhoan.getVaiTro().toString());
        phoneTxt.setText(taiKhoan.getSdt());
        addressTxt.setText(taiKhoan.getDiaChi());
        emailTxt.setText(taiKhoan.getEmail());
        startDatePicker.setValue(taiKhoan.getNgayBatDau());
        endDatePicker.setValue(taiKhoan.getNgayKetThuc());
        genderChoiceBox.setValue(taiKhoan.getGioiTinh().toString());
        birthdayPicker.setValue(taiKhoan.getNgaySinh());
        usernameTxt.setText(taiKhoan.getTenDangNhap());
        statusTxt.setText(taiKhoan.getTrangThai()? "Đang hoạt động" : "Đã khóa");
        // Tương tự cho các trường khác
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
