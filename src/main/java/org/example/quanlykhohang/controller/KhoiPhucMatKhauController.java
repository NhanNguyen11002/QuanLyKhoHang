/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.TaiKhoan;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class KhoiPhucMatKhauController implements Initializable {
    private String otpNumber;
    private long otpCreationTime;

    @FXML
    private Button sendButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField emailRecoverPassTxt;
    @FXML
    private PasswordField confirmCodeTxt;
    @FXML
    private Button confirmButton;
    @FXML
    private PasswordField newPasswordTxt;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Pane pane1, pane2, pane3;
    @FXML
    private Label successLabel;
    @FXML
    private Button sendAgainButton;
    @FXML
    private void onSendButtonClick(){
        String email = emailRecoverPassTxt.getText();
        if (email.isEmpty()) {
            showAlert("Email không được để trống!");
        } else if (!isValid(email)) {
            showAlert("Vui lòng nhập đúng định dạng email!");
        } else {
            NhanVienDAO nhanVienDAO = new NhanVienDAO();
            NhanVien nhanVien = new NhanVien(email);
            if (nhanVienDAO.existsByEmail(nhanVien)) {
                otpCreationTime = System.currentTimeMillis();
                otpNumber = getOTP();
                SendEmailSMTP.sendOTP(email, otpNumber);
                // Hiển thị pane thứ hai và ẩn pane thứ nhất
                pane2.setVisible(true);
                pane1.setVisible(false);
            } else {
                showAlert("Email không tồn tại trên hệ thống!");
            }
        }
    }
    @FXML
    private void onCancelButtonClick(){
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void onConfirmButtonClick(){
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - otpCreationTime;
        long otpExpirationTime = 2 * 60 * 1000; // 1 phút, có thể thay đổi thời gian hết hạn theo ý muốn

        String otp = confirmCodeTxt.getText();
        if (otp.equals("")) {
            showAlert("Không được để trống !");
        } else if (otp.length() != 6) {
            showAlert("Vui lòng nhập đúng 6 chữ số !");
        } else if (timeElapsed > otpExpirationTime) {
            showAlert("Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới.");
        } else if (otp.equals(otpNumber)) {
            // Điều hướng đến pane3
            pane3.setVisible(true);
            pane2.setVisible(false);
        } else {
            showAlert("Mã xác nhận không chính xác !");
        }
    }
    @FXML
    private void onSendAgainButtonClick(){
        String email = emailRecoverPassTxt.getText();
        otpCreationTime = System.currentTimeMillis();
        otpNumber = getOTP();
        SendEmailSMTP.sendOTP(email, otpNumber);
        successLabel.setText("Mã xác nhận đã được gửi lại đến mail của bạn");
    }

    @FXML
    private void onChangePasswordButtonClick(){
        String password = newPasswordTxt.getText();
        if (password.equals("")) {
            showAlert("Mật khẩu không được để trống !");
        } else {
            String email = emailRecoverPassTxt.getText();
            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            TaiKhoan taiKhoan = taiKhoanDAO.findByEmail(email);
            TaiKhoan taiKhoan1 = new TaiKhoan(taiKhoan.getId(),BCrypt.hashpw(password, BCrypt.gensalt()));
            taiKhoanDAO.updatePassword(taiKhoan1);
            showSuccess("Đổi mật khẩu thành công !");
            // Đóng cửa sổ hoặc thực hiện hành động khác sau khi đổi mật khẩu thành công
            Stage stage = (Stage) changePasswordButton.getScene().getWindow();
            stage.close();
        }
    }
    private String getOTP() {
        int min = 100000;
        int max = 999999;
        return Integer.toString((int) ((Math.random() * (max - min)) + min));
    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
