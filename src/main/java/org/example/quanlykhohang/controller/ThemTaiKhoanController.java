/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.*;
import org.mindrot.jbcrypt.BCrypt;

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
//    @FXML
//    private  DatePicker endDatePicker;
    @FXML
    private  DatePicker birthdayPicker;
    @FXML
    private  ChoiceBox genderChoiceBox;
    @FXML
    private  TextField usernameTxt;
    @FXML
    private  PasswordField passwordTxt;
    @FXML
    private  ChoiceBox roleChoiceBox;
    @FXML
    private  Button saveButton;
    @FXML
    private  Button cancelButton;

    private ObservableList<TaiKhoanNhanVienDTO> data;

    public void setData(ObservableList<TaiKhoanNhanVienDTO> data) {
        this.data = data;
    }
    private TaiKhoanController taiKhoanController;
    // Tạo setter để thiết lập đối tượng TaiKhoanController
    public void setTaiKhoanController(TaiKhoanController taiKhoanController) {
        this.taiKhoanController = taiKhoanController;
    }

    @FXML
    private void onSaveButtonClick() throws IOException {
        String ho = lastNameTxt.getText();
        String ten = firstNameTxt.getText();
        String sdt = phoneTxt.getText();
        String diaChi = addressTxt.getText();
        String email = emailTxt.getText();
        LocalDate ngayBatDau = startDatePicker.getValue();
//        LocalDate ngayKetThuc = endDatePicker.getValue();
        LocalDate ngaySinh = birthdayPicker.getValue();
        Gender gioiTinh = (Gender) genderChoiceBox.getValue();
        String tenTaiKhoan = usernameTxt.getText();
        String matKhau = BCrypt.hashpw(passwordTxt.getText(), BCrypt.gensalt());  
        Role vaiTro = (Role)roleChoiceBox.getValue();

        // Kiểm tra xem các trường bắt buộc đã được điền đầy đủ chưa
        if (ho.isEmpty() || ten.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || email.isEmpty() ||
                ngayBatDau == null  || ngaySinh == null || gioiTinh == null ||
                tenTaiKhoan.isEmpty() || matKhau.isEmpty() || vaiTro == null) {
            showAlert("Vui lòng điền đủ thông tin");
            return;
        }
        else {
            if (!isValid(email))
            {
                showAlert("Email không đúng định dạng");
                return;
            }
            else {
                // Tạo một đối tượng Nhân viên
                NhanVienDAO nhanVienDAO = new NhanVienDAO();
                NhanVien nhanVien = new NhanVien(ten, ho, ngaySinh, gioiTinh, ngayBatDau, null, sdt, diaChi, email);

//                System.out.println("Ket qua:");
//                System.out.printf("Ket qua day:", nhanVienDAO.existsByEmail(nhanVien));

                if (nhanVienDAO.existsByEmail(nhanVien))
                {
                    showAlert("Email đã tồn tại trên hệ thống, hãy nhập email khác");
                }
                else{
                    nhanVienDAO.create(nhanVien);
                    // Tạo một đối tượng TaiKhoan
                    TaiKhoan taiKhoan = new TaiKhoan(tenTaiKhoan, matKhau, vaiTro, true, nhanVien);
                    // Thiết lập quan hệ giữa nhân viên và tài khoản
                    taiKhoan.setNhanVien(nhanVien);
                    TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

                    // Lưu tài khoản và nhân viên vào cơ sở dữ liệu
                    taiKhoanDAO.create(taiKhoan);
                    showSuccess("Tạo tài khoản và nhân viên thành công");

                    // Đóng cửa sổ
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                    taiKhoanController.updateTable();
                }

            }
        }
    }
    @FXML
    private void onCancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // Đóng cửa sổ
        stage.close();
    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
//    // Hàm tạo salt ngẫu nhiên
//    private String generateSalt() {
//        SecureRandom random = new SecureRandom();
//        byte[] saltBytes = new byte[16];
//        random.nextBytes(saltBytes);
//        return Base64.getEncoder().encodeToString(saltBytes);
//    }
//
//    // Hàm kết hợp mật khẩu với salt và mã hóa kết quả
//    private String hashPassword(String password, String salt) {
//        String saltedPassword = password + salt;
//        return DigestUtils.sha256Hex(saltedPassword);
//    }

    // Hàm hiển thị cảnh báo
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
