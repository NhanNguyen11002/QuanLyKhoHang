/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.example.quanlykhohang.entity.DienThoai;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ChiTietSanPhamController implements Initializable {
	@FXML
	private TextField idPhoneTxt;
	@FXML
	private TextField namePhoneTxt;
	@FXML
	private TextField inputPriceTxt;
	@FXML
	private TextField outputPriceTxt;
	@FXML
	private AnchorPane anchorPane;

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
				namePhoneTxt.setEditable(false);
				inputPriceTxt.setText(String.valueOf(inputPrice));
				inputPriceTxt.setEditable(false);
				outputPriceTxt.setText(String.valueOf(outputPrice));
				outputPriceTxt.setEditable(false);
			} else {
				throw new IOException("Không có sản phẩm nào được chọn");
			}
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.setOnCloseRequest(event -> {
	            stage.close();
	        });
		}

	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
