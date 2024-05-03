package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.quanlykhohang.dao.KhachHangDAO;
import org.example.quanlykhohang.entity.KhachHang;

import java.net.URL;
import java.util.ResourceBundle;

public class SuaKhachHangController {
    private KhachHangController khachHangController;
    private KhachHang oldKhachHang;
    @FXML
    private TextField idCustomerTxt;
    @FXML
    private TextField nameCustomerTxt;
    @FXML
    private  TextField phoneTxt;
    @FXML
    private  TextField emailTxt;
    @FXML
    private  TextField addressTxt;
    @FXML
    private Button saveButton;
    @FXML
    private  Button cancelButton;
    @FXML
    private void onSaveButtonClick(){
        Integer id = Integer.valueOf(idCustomerTxt.getText());
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
        if(!email.equals(oldKhachHang.getEmail()) && isExistByEmail ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã có khách hàng với email được nhập tồn tại trong hệ thống");
            alert.showAndWait();
            return;
        }
        boolean isExistBySDT = khachHangDAO.existsBySDT(sdt);
        if(!sdt.equals(oldKhachHang.getSdt()) && isExistBySDT){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã có khách hàng với SĐT được nhập tồn tại trong hệ thống");
            alert.showAndWait();
            return;
        }

        try {
            KhachHang khachHang = new KhachHang( id,ten, sdt, diaChi, email);

            khachHangDAO.update(khachHang);
            khachHangController.resetData();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Sửa khách hàng thành công.");
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
    public void initData(KhachHang khachHang) {
        oldKhachHang = khachHang;
        idCustomerTxt.setText(String.valueOf(khachHang.getMaKhachHang()));
        nameCustomerTxt.setText(khachHang.getTenKhachHang());
        phoneTxt.setText(khachHang.getSdt());
        addressTxt.setText(khachHang.getDiaChi());
        emailTxt.setText(khachHang.getEmail());
    }
    public void setKhachHangController(KhachHangController khachHangController){
        this.khachHangController = khachHangController;
    }}
