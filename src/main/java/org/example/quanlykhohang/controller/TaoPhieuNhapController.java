package org.example.quanlykhohang.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.ChiTietPhieuNhapDAO;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.PhieuNhapDAO;
import org.example.quanlykhohang.entity.ChiTietPhieuNhap;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.NhaCungCap;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.PhieuNhap;
import org.example.quanlykhohang.entity.PhieuStatus;
import org.example.quanlykhohang.entity.UserSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import java.sql.Timestamp;

public class TaoPhieuNhapController {
	@FXML
	private Button addBtn;
	@FXML
	private Button resetBtn;
	@FXML
	private Button importExcelBtn;
	@FXML
	private Button editQuantityBtn;
	@FXML
	private Button deleteProductBtn;
	@FXML
	private TextField searchTxt;
	@FXML
	private TextField quantityTxt;
	@FXML
	private TextField formIdTxt;
	@FXML
	private TextField creatorTxt;
	@FXML
	private ComboBox<String> providerCbbox;
	@FXML
	private TableView<DienThoai> productTable;
	@FXML
	private TableColumn<DienThoai, String> idPhoneColumn;
	@FXML
	private TableColumn<DienThoai, String> namePhoneColumn;
	@FXML
	private TableColumn<DienThoai, Double> inputPriceColumn;
	@FXML
	private TableColumn<DienThoai, Integer> soLuongColumn;
	@FXML
	private TableView<ChiTietPhieuNhap> importFormTable;
	@FXML
	private TableColumn<ChiTietPhieuNhap, String> maDTColumn;
	@FXML
	private TableColumn<ChiTietPhieuNhap, String> tenDTColumn;
	@FXML
	private TableColumn<ChiTietPhieuNhap, Double> giaNhapColumn;
	@FXML
	private TableColumn<ChiTietPhieuNhap, Integer> quantityColumn;
	@FXML
	private Button importBtn;
	@FXML
	private Label totalMoneyLabel;
	@FXML
	private Button backBtn;

	private ObservableList<DienThoai> phoneList = FXCollections.observableArrayList();
	private Double totalMoney = 0.0;
	private final DecimalFormat format = new DecimalFormat("#,###.0");
	
	@FXML
	private void initialize() {
		// đổ tên người tạo phiếu
		creatorTxt.setText(UserSession.getInstance().getUserName());
		creatorTxt.setDisable(true);
		creatorTxt.setStyle("-fx-text-fill: blue; -fx-font-size: 16px; -fx-font-family: Arial; "
				+ "-fx-border-style: none; -fx-font-style: italic; -fx-font-weight: bold;");
		// đổ list ncc
		NhaCungCapDAO nccDAO = new NhaCungCapDAO();
		List<NhaCungCap> nccList = nccDAO.findAll();
		List<String> nccNames = nccList.stream().map(NhaCungCap::getTenNhaCungCap).collect(Collectors.toList());
		providerCbbox.setItems(FXCollections.observableArrayList(nccNames));
		// đổ list sản phẩm
		idPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("maDT"));
		idPhoneColumn.setStyle("-fx-alignment: CENTER;");
		namePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("tenDT"));
		namePhoneColumn.setStyle("-fx-alignment: CENTER;");
		inputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
		inputPriceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		inputPriceColumn.setCellFactory(tc -> new TableCell<DienThoai, Double>() {
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
		soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		soLuongColumn.setStyle("-fx-alignment: CENTER;");
		DienThoaiDAO phoneDAO = new DienThoaiDAO();
		phoneList.clear();
		phoneList.addAll(phoneDAO.findAll());
		productTable.getItems().addAll(phoneList);
		productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
		productTable.refresh();
		// set TableView importFormTable
		maDTColumn.setCellValueFactory(cellData -> {
		    ChiTietPhieuNhap chiTiet = cellData.getValue();
		    DienThoai dienThoai = chiTiet.getDienThoai();
		    if (dienThoai != null) {
		        return new SimpleObjectProperty<>(dienThoai.getMaDT());
		    } else {
		        return new SimpleObjectProperty<>("");
		    }
		});
		maDTColumn.setStyle("-fx-alignment: CENTER;");
		tenDTColumn.setCellValueFactory(cellData -> {
		    ChiTietPhieuNhap chiTiet = cellData.getValue();
		    DienThoai dienThoai = chiTiet.getDienThoai();
		    if (dienThoai != null) {
		        return new SimpleObjectProperty<>(dienThoai.getTenDT());
		    } else {
		        return new SimpleObjectProperty<>("");
		    }
		});
		tenDTColumn.setStyle("-fx-alignment: CENTER;");
		giaNhapColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
		giaNhapColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		giaNhapColumn.setCellFactory(tc -> new TableCell<ChiTietPhieuNhap, Double>() {
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
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		quantityColumn.setStyle("-fx-alignment: CENTER;");
		importFormTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
		totalMoneyLabel.setText(format.format(totalMoney)+" đ");
	}

	@FXML
	private void onResetBtnClick() {
		providerCbbox.getSelectionModel().clearSelection();
		quantityTxt.clear();
		importFormTable.getItems().clear();
		totalMoney = 0.0;
		totalMoneyLabel.setText(format.format(totalMoney)+" đ");
	}

	@FXML
	private void onAddBtnClick() {
		try {
			DienThoai selectedPhone = productTable.getSelectionModel().getSelectedItem();
			if (selectedPhone != null) {
				double inputPrice = selectedPhone.getGiaNhap();
				if (quantityTxt.getText().isEmpty()) {
					throw new IOException("Vui lòng nhập số lượng");
				}
				Integer soLuong  = Integer.valueOf(quantityTxt.getText());
	            if(soLuong <= 0 ){
	            	throw new IOException("Số lượng phải là số nguyên dương");
	            }
				// thêm vào table form nhập
	            boolean isProductExist = false;
	            for (ChiTietPhieuNhap item : importFormTable.getItems()) {
	                if (item.getDienThoai().getMaDT().equals(selectedPhone.getMaDT())) {
	                    item.setSoLuong(item.getSoLuong() + soLuong);
	                    isProductExist = true;
	                    break;
	                }
	            }
	            if (!isProductExist) {
	                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
	                ctpn.setDienThoai(selectedPhone);
	                ctpn.setDonGia(inputPrice);
	                ctpn.setSoLuong(soLuong);
	                importFormTable.getItems().add(ctpn);
	            }
				importFormTable.refresh();
				totalMoney = totalMoney + inputPrice*soLuong;
				totalMoneyLabel.setText(format.format(totalMoney)+" đ");
			} else {
				throw new IOException("Vui lòng chọn sản phẩm");
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText("Số lượng không hợp lệ");
			alert.showAndWait();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
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
	private void onProviderCbboxAction() {
	}

	@FXML
	private void onImportExcelBtnClick() {
	}

	@FXML
	private void onEditQuantityBtnClick() {
		try {
			if (quantityTxt.getText().isEmpty()) {
				throw new IOException("Vui lòng nhập số lượng");
			}
			Integer soLuong  = Integer.valueOf(quantityTxt.getText());
            if(soLuong <= 0 ){
            	throw new IOException("Số lượng phải là số nguyên dương");
            }
            ChiTietPhieuNhap selectedCTPN = importFormTable.getSelectionModel().getSelectedItem();
            if (selectedCTPN != null) {
                selectedCTPN.setSoLuong(soLuong);
                totalMoney = calculateTotalMoney(importFormTable.getItems());
                totalMoneyLabel.setText(format.format(totalMoney) + " đ");
                importFormTable.refresh();
            } else {
                throw new IOException("Vui lòng chọn sản phẩm cần sửa số lượng");
            }
		} catch (NumberFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText("Số lượng không hợp lệ");
			alert.showAndWait(); 
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	private double calculateTotalMoney(List<ChiTietPhieuNhap> list) {
	    double total = 0.0;
	    for (ChiTietPhieuNhap item : list) {
	        total += item.getDonGia() * item.getSoLuong();
	    }
	    return total;
	}

	@FXML
	private void onDeleteProductBtnClick() {
		try {
			ChiTietPhieuNhap selectedCTPN = importFormTable.getSelectionModel().getSelectedItem();
			if (selectedCTPN != null) {
				importFormTable.getItems().remove(selectedCTPN);
				totalMoney = totalMoney - selectedCTPN.getDonGia() * selectedCTPN.getSoLuong();
	            totalMoneyLabel.setText(format.format(totalMoney) + " đ");
			} else {
				throw new IOException("Vui lòng chọn sản phẩm cần xóa");
			}
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void onImportBtnClick() {
		try {
			NhanVienDAO nvDAO = new NhanVienDAO();
			NhaCungCapDAO nccDAO = new NhaCungCapDAO();
			PhieuNhapDAO pnDAO = new PhieuNhapDAO();
			ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();
			DienThoaiDAO dtDAO = new DienThoaiDAO();
			NhanVien nguoiTao = nvDAO.findById(UserSession.getInstance().getUserId());
			if (importFormTable.getItems().size() == 0) {
				throw new IOException("Vui lòng chọn sản phẩm");
			}
			String selectedProvider = providerCbbox.getValue();
			if (selectedProvider == null) {
				throw new IOException("Vui lòng chọn nhà cung cấp");
			}
			NhaCungCap ncc = nccDAO.findByName(selectedProvider);
			Timestamp thoiGianTao = new Timestamp(System.currentTimeMillis());
			PhieuNhap pn = new PhieuNhap(thoiGianTao, nguoiTao, ncc, totalMoney, PhieuStatus.Done);
			pnDAO.create(pn);
			for (ChiTietPhieuNhap item : importFormTable.getItems()) {
                item.setPhieuNhap(pn);
                ctpnDAO.create(item);
                int soLuong = item.getSoLuong();
                // cap nhat so luong ton kho
                DienThoai dt = item.getDienThoai();
                dt.setSoLuong(dt.getSoLuong() + soLuong);
                dtDAO.update(dt);
            }
			// thông báo thành công
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Thành công");
			alert.setHeaderText("Tạo phiếu nhập thành công");
			alert.showAndWait();
			// quay về trang phiếu nhập
			Stage currentStage = (Stage) backBtn.getScene().getWindow();
			BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-nhap-view.fxml"));
			Pane item = fxmlLoader.load();
			borderPane.setRight(item);
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void onBackBtnClick() {
		try {
			Stage currentStage = (Stage) backBtn.getScene().getWindow();
			BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-nhap-view.fxml"));
			Pane item = fxmlLoader.load();
			borderPane.setRight(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
