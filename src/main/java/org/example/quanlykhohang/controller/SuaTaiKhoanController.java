/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
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
import org.example.quanlykhohang.entity.TaiKhoanNhanVienDTO;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class SuaTaiKhoanController implements Initializable {
    @FXML
    private TextField  idTxt;
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
    private  ChoiceBox statusChoiceBox;
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
    private void onSaveButtonClick(){
        Integer id = Integer.valueOf(idTxt.getText());
        String ho = lastNameTxt.getText();
        String ten = firstNameTxt.getText();
        String sdt = phoneTxt.getText();
        String diaChi = addressTxt.getText();
        String email = emailTxt.getText();
        LocalDate ngayBatDau = startDatePicker.getValue();
        LocalDate ngayKetThuc = endDatePicker.getValue();
        LocalDate ngaySinh = birthdayPicker.getValue();
        Gender gioiTinh = Gender.valueOf(genderChoiceBox.getValue().toString());
        String tenTaiKhoan = usernameTxt.getText();
        String trangThaiCb = statusChoiceBox.getValue().toString();
        boolean trangThai = trangThaiCb.equals("Đang hoạt động");
        Role vaiTro = Role.valueOf(roleChoiceBox.getValue().toString());

        // Kiểm tra xem các trường bắt buộc đã được điền đầy đủ chưa
        if (ho.isEmpty() || ten.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || email.isEmpty() ||
                ngayBatDau == null || ngayKetThuc == null || ngaySinh == null || gioiTinh == null ||
                tenTaiKhoan.isEmpty() || vaiTro == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }
        // Tạo một đối tượng Nhân viên
        NhanVien nhanVien = new NhanVien(id, ten, ho, ngaySinh, gioiTinh, ngayBatDau, ngayKetThuc, sdt, diaChi, email);
        NhanVienDAO nhanVienDAO = new NhanVienDAO();
//        NhanVien existingNhanVien = nhanVienDAO.findById(id); // Cần triển khai phương thức findById() trong NhanVienDAO
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
        TaiKhoan existingTaiKhoan = taiKhoanDAO.findById(id);
        
        nhanVienDAO.update(nhanVien);
        System.out.println(nhanVien.toString());
        // Tạo một đối tượng TaiKhoan
        TaiKhoan taiKhoan = new TaiKhoan(tenTaiKhoan, vaiTro, trangThai, nhanVien);
        // Thiết lập quan hệ giữa nhân viên và tài khoản
        taiKhoan.setNhanVien(nhanVien);

        // Lưu tài khoản và nhân viên vào cơ sở dữ liệu
        taiKhoanDAO.update(taiKhoan);

        // Hiển thị thông báo thành công
        showSuccess("Cập nật tài khoản và nhân viên thành công");

        // Đóng cửa sổ
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        taiKhoanController.updateTable();

    }
    @FXML
    private void onCancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // Đóng cửa sổ
        stage.close();
    }

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
        statusChoiceBox.setValue(taiKhoan.getTrangThai()? "Đang hoạt động" : "Đã khóa");
        // Tương tự cho các trường khác
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
        // Khởi tạo choice box giới tính
        genderChoiceBox.getItems().addAll(Gender.Male, Gender.Female);

        // Khởi tạo choice box vai trò
        roleChoiceBox.getItems().addAll(Role.Admin, Role.Staff);
        statusChoiceBox.getItems().addAll("Đang hoạt động", "Đã khóa");
        // TODO
    }    
    
}
