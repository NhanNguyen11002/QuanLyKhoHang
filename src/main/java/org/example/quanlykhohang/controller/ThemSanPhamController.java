/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.entity.DienThoai;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ThemSanPhamController implements Initializable {
	@FXML
	private TextField idPhoneTxt;
	@FXML
	private TextField namePhoneTxt;
	@FXML
	private TextField inputPriceTxt;
	@FXML
	private TextField outputPriceTxt;
	@FXML
	private ChoiceBox supplierChoiceBox;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	private SanPhamController sanPhamController;

	public void setSanPhamController(SanPhamController sanPhamController) {
		this.sanPhamController = sanPhamController;
	}

	@FXML
	private void onSaveButtonClick() {
		try {
			DienThoaiDAO phoneDAO = new DienThoaiDAO();
			String id = idPhoneTxt.getText();
			String name = namePhoneTxt.getText();
			String inputPrice = inputPriceTxt.getText();
			String outputPrice = outputPriceTxt.getText();
			if (id.isEmpty() || name.isEmpty() || inputPrice.isEmpty() || outputPrice.isEmpty()) {
				throw new IllegalArgumentException("Vui lòng điền đủ thông tin");
			}
			// check exist
			boolean exist = phoneDAO.existsById(id);
			if (exist == true) {
				throw new IllegalArgumentException("Mã điện thoại đã tồn tại");
			}
			DienThoai dt = new DienThoai();
			dt.setMaDT(id);
			dt.setTenDT(name);
			dt.setGiaNhap(Double.parseDouble(inputPrice));
			dt.setGiaXuat(Double.parseDouble(outputPrice));
			dt.setSoLuong(0);
			phoneDAO.create(dt);
			// Đóng cửa sổ
			Stage stage = (Stage) saveButton.getScene().getWindow();
			stage.close();
			// cập nhật tableview sau khi thêm
			sanPhamController.reloadPhoneList();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText("Dữ liệu không hợp lệ");
			alert.setContentText("Giá nhập và xuất phải là số dương");
			alert.showAndWait();
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}

	@FXML
	private void onCancelButtonClick() {
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

}
