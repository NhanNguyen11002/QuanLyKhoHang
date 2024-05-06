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
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.Gender;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.TaiKhoan;
import org.example.quanlykhohang.entity.UserSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class SuaThongTinCaNhanController implements Initializable {
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
    private  DatePicker birthdayPicker;
    @FXML
    private  ChoiceBox genderChoiceBox;
    @FXML
    private  PasswordField currentPassTxt;
    @FXML
    private  PasswordField newPassTxt;
    @FXML
    private  PasswordField againNewPassTxt;
    @FXML
    private  Button saveButton;
    @FXML
    private  Button saveButton2;
    @FXML
    private  Label passCurEr;
    @FXML
    private  Label passAftEr;
    @FXML
    private  Label passAgainEr;
    @FXML
    private void onSaveButtonClick(){
        Integer id = UserSession.getInstance().getMaNhanVien();
        String ho = lastNameTxt.getText();
        String ten = firstNameTxt.getText();
        String sdt = phoneTxt.getText();
        String diaChi = addressTxt.getText();
        String email = emailTxt.getText();
        LocalDate ngaySinh = birthdayPicker.getValue();
        Gender gioiTinh = Gender.valueOf(genderChoiceBox.getValue().toString());
        if (ho.isEmpty() || ten.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || email.isEmpty() ||
                ngaySinh == null || gioiTinh == null) {
            showAlert("Vui lòng điền đầy đủ thông tin");
        }
        else {
            if (!isValid(email)){
                showAlert("Email không đúng định dạng");
            }
            else {
                NhanVien nhanVien = new NhanVien(id, ten, ho, ngaySinh, gioiTinh, sdt, diaChi, email);
                NhanVienDAO nhanVienDAO = new NhanVienDAO();
                nhanVienDAO.updatePrivateAccount(nhanVien);
                showSuccess("Cập nhật thông tin cá nhân thành công");
                // Đóng cửa sổ
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        }
    }
    @FXML
    private void onSaveButton2Click(){
        boolean check = true;
        String curPass = currentPassTxt.getText();
        String newPass = newPassTxt.getText();
        String againNewPass = againNewPassTxt.getText();
        Integer id = UserSession.getInstance().getUserId();

        if (curPass.length() == 0) {
            check = false;
            passCurEr.setText("Vui lòng nhập mật khẩu hiện tại");
        } else {
            passCurEr.setText("");
        }
        if (newPass.length() == 0) {
            check = false;
            passAftEr.setText("Vui lòng nhập mật khẩu mới");
        } else {
            passAftEr.setText("");
        }
        if (againNewPass.length() == 0) {
            check = false;
            passAgainEr.setText("Vui lòng xác nhận lại mật khẩu");
        } else {
            passAgainEr.setText("");
        }
        if (check == true) {
            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            TaiKhoan taiKhoan = taiKhoanDAO.findById(UserSession.getInstance().getUserId());
            if (BCrypt.checkpw(curPass, taiKhoan.getPassword())) {
//                BCrypt.hashpw(txtpassword.getText(), BCrypt.gensalt(12))
                if (newPass.length() < 6) {
                    showAlert("Vui lòng nhập mật khẩu mới lớn hơn hoặc bằng 6 kí tự");
                } else {
                    if (newPass.equals(againNewPass)) {
                        String pass = BCrypt.hashpw(newPass, BCrypt.gensalt());
                        TaiKhoan taiKhoan1 = new TaiKhoan(id,pass);
                        taiKhoanDAO.updatePassword(taiKhoan1);
                        showSuccess("Thay đổi mật khẩu thành công!");
                        // Đóng cửa sổ
                        Stage stage = (Stage) saveButton.getScene().getWindow();
                        stage.close();
                    } else {
                        showAlert("Mật khẩu mới không khớp");
                    }
                }
            } else {
                showAlert("Mật khẩu hiện tại không đúng ");
                currentPassTxt.setText("");
            }
        }
    }
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
    public void setUserInfo(NhanVien nhanVien) {
        firstNameTxt.setText(nhanVien.getHo());
        lastNameTxt.setText(nhanVien.getTen());
        birthdayPicker.setValue(nhanVien.getNgaySinh());
        genderChoiceBox.setValue(nhanVien.getGioiTinh().toString());
        phoneTxt.setText(nhanVien.getSdt());
        addressTxt.setText(nhanVien.getDiaChi());
        emailTxt.setText(nhanVien.getEmail());
    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        genderChoiceBox.getItems().addAll(Gender.Male, Gender.Female);

    }    
    
}
