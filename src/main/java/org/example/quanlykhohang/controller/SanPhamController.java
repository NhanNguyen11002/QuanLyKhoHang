package org.example.quanlykhohang.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.entity.DienThoai;

public class SanPhamController {
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button importExcelBtn;
	@FXML
	private Button exportExcelBtn;
	@FXML
	private Button resetBtn;
	@FXML
	private Button detailBtn;
	@FXML
	private ComboBox<String> filterCbbox;
	@FXML
	private TextField searchTxt;
	@FXML
	private TableView<DienThoai> productTable;
	@FXML
	private TableColumn<DienThoai, String> idPhoneColumn;
	@FXML
	private TableColumn<DienThoai, String> namePhoneColumn;
	@FXML
	private TableColumn<DienThoai, Double> inputPriceColumn;
	@FXML
	private TableColumn<DienThoai, Double> outputPriceColumn;

	private ObservableList<DienThoai> phoneList = FXCollections.observableArrayList();

	public TableView<DienThoai> getProductTable() {
		return this.productTable;
	}

	public void reloadPhoneList() {
		DienThoaiDAO phoneDAO = new DienThoaiDAO();
		phoneList.clear();
		phoneList.addAll(phoneDAO.findAll());
		productTable.getItems().clear();
		productTable.getItems().addAll(phoneList);
		productTable.refresh();
	}

	@FXML
	private void initialize() {
		idPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("maDT"));
		idPhoneColumn.setStyle("-fx-alignment: CENTER;");
		namePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("tenDT"));
		namePhoneColumn.setStyle("-fx-alignment: CENTER;");
		inputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
		inputPriceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		inputPriceColumn.setCellFactory(tc -> new TableCell<DienThoai, Double>() {
			private final DecimalFormat format = new DecimalFormat("#,###.0");
			
		    @Override
		    protected void updateItem(Double item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		        } else {
		            setText(format.format(item));
		        }
		    }
		});
		outputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("giaXuat"));
		outputPriceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		outputPriceColumn.setCellFactory(tc -> new TableCell<DienThoai, Double>() {
			private final DecimalFormat format = new DecimalFormat("#,###.0");
			
		    @Override
		    protected void updateItem(Double item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		        } else {
		            setText(format.format(item));
		        }
		    }
		});
		productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
		DienThoaiDAO phoneDAO = new DienThoaiDAO();
		phoneList.clear();
		phoneList.addAll(phoneDAO.findAll());
		productTable.getItems().addAll(phoneList);
		productTable.refresh();
	}

	@FXML
	private void onAddBtnClick() {
		try {
			// Load the FXML file
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-them-san-pham-view.fxml"));
			// Load the root pane
			Pane item = fxmlLoader.load();
			ThemSanPhamController controller = fxmlLoader.getController();
			controller.setSanPhamController(this);
			// Create a new scene with the loaded pane
			Scene scene = new Scene(item);

			// Create a new stage and set the scene
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); // Show the stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onEditBtnClick() {
		try {
			// Load the FXML file
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-san-pham-view.fxml"));
			// Load the root pane
			Pane item = fxmlLoader.load();
			SuaSanPhamController controller = fxmlLoader.getController();
			controller.setSanPhamController(this);
			// Create a new scene with the loaded pane
			Scene scene = new Scene(item);

			// Create a new stage and set the scene
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); // Show the stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onDeleteBtnClick() {
		try {
			DienThoai selectedPhone = productTable.getSelectionModel().getSelectedItem();
			if (selectedPhone != null) {
				String idPhone = selectedPhone.getMaDT();
				DienThoaiDAO phoneDAO = new DienThoaiDAO();
				phoneDAO.delete(idPhone);
				reloadPhoneList();
			} else {
				throw new IOException("Không có sản phẩm nào được chọn");
			}
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void onDetailBtnClick() {
		try {
			// Load the FXML file
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-chi-tiet-san-pham-view.fxml"));
			// Load the root pane
			Pane item = fxmlLoader.load();
			ChiTietSanPhamController controller = fxmlLoader.getController();
			controller.setSanPhamController(this);
			// Create a new scene with the loaded pane
			Scene scene = new Scene(item);

			// Create a new stage and set the scene
			Stage stage = new Stage();
			stage.setScene(scene);
//            stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); // Show the stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleSearchTextFieldKeyPress(ActionEvent event) {
		filterTable(searchTxt.getText());
	}

	private void filterTable(String keyword) {
		DienThoaiDAO dtDAO = new DienThoaiDAO();
		ObservableList<DienThoai> filteredList = FXCollections.observableArrayList(dtDAO.findByKeyword(keyword));
		productTable.getItems().clear();
		productTable.getItems().addAll(filteredList);
		productTable.refresh();
	}

	@FXML
	private void onImportExcelBtnClick() {
	}

	@FXML
	private void onExportExcelBtnClick() {
	}

	@FXML
	private void onResetBtnClick() {
		reloadPhoneList();
	}

	@FXML
	private void onFilterCbboxAction() {
	}
}
