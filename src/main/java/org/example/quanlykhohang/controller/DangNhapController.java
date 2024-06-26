package org.example.quanlykhohang.controller;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.Role;
import org.example.quanlykhohang.entity.UserSession;
import org.example.quanlykhohang.entity.TaiKhoan;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class DangNhapController {
    @FXML
    private TextField  usernameTxt;
    @FXML
    private  PasswordField passwordTxt;
    @FXML
    private  Button loginBtn;
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private void onLoginBtnClick(){
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ thông tin!");
        } else {
            // Thực hiện kiểm tra đăng nhập
            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            TaiKhoan taiKhoan = taiKhoanDAO.checkLogin(username, password);

            if (taiKhoan == null) {
                showAlert("Tên đăng nhập hoặc mật khẩu không đúng!");
            } else {
                if (taiKhoan.isDangHoatDong() == true) { // Kiểm tra tài khoản có bị khóa không
                    UserSession.getInstance().setCurrentUser(taiKhoan.getId(), taiKhoan.getUsername(), taiKhoan.getNhanVien().getMaNhanVien());
                    Role role = taiKhoan.getVaiTro();
                    if (role.equals(Role.Admin)) {
                        showSuccess("Đăng nhập thành công!");
                        openAdminSidebar();
                        System.out.println(UserSession.getInstance().getUserId());
                        System.out.println(UserSession.getInstance().getUserName());
                    } else if (role.equals(Role.Staff)) {
                        showSuccess("Đăng nhập thành công!");
                        openStaffSidebar();
                        System.out.println(UserSession.getInstance().getUserId());
                        System.out.println(UserSession.getInstance().getUserName());
                    }
                    Stage currentStage = (Stage) usernameTxt.getScene().getWindow();
                    currentStage.close();
                    // Điều hướng đến giao diện tương ứng dựa trên vai trò của người dùng

                } else {
                    showAlert("Tài khoản của bạn đã bị khóa!");
                }
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
    private void openAdminSidebar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/admin-sidebar.fxml"));
            Parent root = fxmlLoader.load();
            Stage adminStage = new Stage();
            adminStage.setTitle("Quản lý kho hàng");
            adminStage.setScene(new Scene(root));
            adminStage.setResizable(false);
            adminStage.show();
        } catch (IOException e) {
            System.out.println("Error loading admin-sidebar.fxml: " + e.getMessage()); // In ra thông báo lỗi
            e.printStackTrace();
            // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi
        }
    }
    private void openStaffSidebar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/staff-sidebar.fxml"));
            Parent root = fxmlLoader.load();
            Stage adminStage = new Stage();
            adminStage.setTitle("Quản lý kho hàng");
            adminStage.setScene(new Scene(root));
            adminStage.setResizable(false);
            adminStage.show();
        } catch (IOException e) {
            System.out.println("Error loading staff-sidebar.fxml: " + e.getMessage()); // In ra thông báo lỗi
            e.printStackTrace();
            // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi
        }
    }
    @FXML
    private void onForgotLabelClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-khoi-phuc-mat-khau-view.fxml"));
            Pane item = fxmlLoader.load();
//            KhoiPhucMatKhauController controller = fxmlLoader.getController();
            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show(); // Show the stage
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
