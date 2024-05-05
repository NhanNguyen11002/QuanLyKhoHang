/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.entity.DienThoai;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class SuaSanPhamController implements Initializable {
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
		try {
			this.sanPhamController = sanPhamController;
			TableView<DienThoai> productTable = this.sanPhamController.getProductTable();
			DienThoai selectedPhone = productTable.getSelectionModel().getSelectedItem();
			if (selectedPhone != null) {
				String idPhone = selectedPhone.getMaDT();
				String namePhone = selectedPhone.getTenDT();
				double inputPrice = selectedPhone.getGiaNhap();
				double outputPrice = selectedPhone.getGiaXuat();
				idPhoneTxt.setText(idPhone);
				idPhoneTxt.setEditable(false);
				namePhoneTxt.setText(namePhone);
				inputPriceTxt.setText(String.valueOf((int)inputPrice));
				outputPriceTxt.setText(String.valueOf((int)outputPrice));
			} else {
				throw new IOException("Không có sản phẩm nào được chọn");
			}
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
			onCancelButtonClick();
		}

	}

	@FXML
	private void onSaveButtonClick() {
		try {
			DienThoaiDAO phoneDAO = new DienThoaiDAO();
			String id = idPhoneTxt.getText();
			String name = namePhoneTxt.getText();
			double inputPrice = Double.parseDouble(inputPriceTxt.getText());
			double outputPrice = Double.parseDouble(outputPriceTxt.getText());
			// check exist
			DienThoai phone = phoneDAO.findById(id);
			if (phone == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Lỗi");
				alert.setHeaderText("Mã điện thoại không tồn tại");
				alert.showAndWait();
			} else {
				phone.setTenDT(name);
				phone.setGiaNhap(inputPrice);
				phone.setGiaXuat(outputPrice);
				phoneDAO.update(phone);
			}
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

	}

}
