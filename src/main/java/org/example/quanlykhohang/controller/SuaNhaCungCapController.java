/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.*;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class SuaNhaCungCapController implements Initializable {
    private NhaCungCapController nhaCungCapController;
    private NhaCungCap oldNcc;
    @FXML
    private TextField  idSupplierTxt;
    @FXML
    private TextField  nameSupplierTxt;
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
        Integer id = Integer.valueOf(idSupplierTxt.getText());
        String ten = nameSupplierTxt.getText();
        String sdt = phoneTxt.getText();
        String diaChi = addressTxt.getText();
        String email = emailTxt.getText();

        // Kiểm tra xem các trường bắt buộc đã được điền đầy đủ chưa
        if (idSupplierTxt.getText().isEmpty() || ten.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
        boolean isExistByEmail = nhaCungCapDAO.existByEmail(email);
        if(!email.equals(oldNcc.getEmail()) && isExistByEmail){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã có nhà cung cấp với email được nhập tồn tại trong hệ thống");
            alert.showAndWait();
            return;
        }
        boolean isExistBySDT = nhaCungCapDAO.existBySDT(sdt);
        if(!sdt.equals(oldNcc.getSdt()) && isExistBySDT){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã có nhà cung cấp với SĐT được nhập tồn tại trong hệ thống");
            alert.showAndWait();
            return;
        }
        boolean isExistByName = nhaCungCapDAO.existByTen(ten);
        if(!ten.equals(oldNcc.getTenNhaCungCap())&&isExistByName){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã có nhà cung cấp với tên được nhập tồn tại trong hệ thống");
            alert.showAndWait();
            return;
        }


        try {
            NhaCungCap nhaCungCap = new NhaCungCap(id, ten, sdt, diaChi, email);
            nhaCungCapDAO.update(nhaCungCap);
            nhaCungCapController.resetData();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật nhà cung cấp thành công.");
            alert.showAndWait();



            // Đóng cửa sổ
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void initData(NhaCungCap nhaCungCap) {
        oldNcc = nhaCungCap;
        idSupplierTxt.setText(String.valueOf(nhaCungCap.getMaNhaCungCap()));
        nameSupplierTxt.setText(nhaCungCap.getTenNhaCungCap());
        phoneTxt.setText(nhaCungCap.getSdt());
        addressTxt.setText(nhaCungCap.getDiaChi());
        emailTxt.setText(nhaCungCap.getEmail());
    }
    public void setNhaCungCapController(NhaCungCapController nhaCungCapController){
        this.nhaCungCapController = nhaCungCapController;
    }
    
}
