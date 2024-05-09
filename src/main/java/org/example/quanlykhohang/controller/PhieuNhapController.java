package org.example.quanlykhohang.controller;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.ChiTietPhieuNhapDAO;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.PhieuNhapDAO;
import org.example.quanlykhohang.entity.ChiTietPhieuNhap;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.NhaCungCap;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.PhieuNhap;
import org.example.quanlykhohang.entity.PhieuStatus;
import org.example.quanlykhohang.entity.UserSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class PhieuNhapController {
	PhieuNhapDAO pnDAO = new PhieuNhapDAO();
	ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();
	DienThoaiDAO dtDAO = new DienThoaiDAO();
	
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
    private TableView<PhieuNhap> importTicketTable;
    @FXML
	private TableColumn<PhieuNhap, String> maPhieuColumn;
	@FXML
	private TableColumn<PhieuNhap, String> nguoiTaoColumn;
	@FXML
	private TableColumn<PhieuNhap, String> nhaCungCapColumn;
	@FXML
	private TableColumn<PhieuNhap, String> thoiGianTaoColumn;
	@FXML
	private TableColumn<PhieuNhap, Double> tongTienColumn;
	@FXML
	private TableColumn<PhieuNhap, String> trangThaiColumn;
	
	private ObservableList<PhieuNhap> pnList = FXCollections.observableArrayList();
	private final DecimalFormat format = new DecimalFormat("#,###.0");
	
	public TableView<PhieuNhap> getPNTable() {
		return this.importTicketTable;
	}
    
    @FXML
	private void initialize() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT+7")); // UTC+7
    	
    	// setup TableView
    	maPhieuColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieu"));
    	maPhieuColumn.setStyle("-fx-alignment: CENTER;");
    	nguoiTaoColumn.setCellValueFactory(cellData -> {
		    PhieuNhap pn = cellData.getValue();
		    NhanVien nv = pn.getNguoiTao();
		    if (nv != null) {
		        return new SimpleObjectProperty<>(nv.getHo()+" "+nv.getTen());
		    } else {
		        return new SimpleObjectProperty<>("");
		    }
		});
    	nguoiTaoColumn.setStyle("-fx-alignment: CENTER;");
    	nhaCungCapColumn.setCellValueFactory(cellData -> {
		    PhieuNhap pn = cellData.getValue();
		    NhaCungCap ncc = pn.getNhaCungCap();
		    if (ncc != null) {
		        return new SimpleObjectProperty<>(ncc.getTenNhaCungCap());
		    } else {
		        return new SimpleObjectProperty<>("");
		    }
		});
    	nhaCungCapColumn.setStyle("-fx-alignment: CENTER;");
    	thoiGianTaoColumn.setCellValueFactory(cellData -> {
    		PhieuNhap pn = cellData.getValue();
    	    Timestamp timestampValue = pn.getThoiGianTao();
    	    if (timestampValue != null) {
    	        return new SimpleStringProperty(sdf.format(timestampValue));
    	    } else {
    	        return new SimpleStringProperty("");
    	    }
    	});
    	thoiGianTaoColumn.setStyle("-fx-alignment: CENTER;");
    	tongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
    	tongTienColumn.setStyle("-fx-alignment: CENTER;");
    	tongTienColumn.setCellFactory(tc -> new TableCell<PhieuNhap, Double>() {
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
    	trangThaiColumn.setCellValueFactory(cellData->{
            PhieuStatus value = cellData.getValue().getStatus();
            String formatTrangThai = value.toString().equals("Done")?"Hoàn thành":"Đã xóa";
            return new SimpleStringProperty(formatTrangThai);
        });
    	trangThaiColumn.setStyle("-fx-alignment: CENTER;");
    	filterCbbox.setItems(FXCollections.observableArrayList("Hoàn thành", "Đã xóa"));
    	// add Data
    	pnList.addAll(pnDAO.findAll());
    	importTicketTable.getItems().addAll(pnList);
    	importTicketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    	importTicketTable.refresh();
	}
    
    @FXML
	public void handleSearchTextFieldKeyPress(ActionEvent event) {
		filterTable(searchTxt.getText());
	}

	private void filterTable(String keyword) {
		ObservableList<PhieuNhap> filteredList = FXCollections.observableArrayList(pnDAO.findByKeyword(keyword));
		importTicketTable.getItems().clear();
		importTicketTable.getItems().addAll(filteredList);
		importTicketTable.refresh();
	}
    
    @FXML
    private void onAddBtnClick(){
        try {
            Stage currentStage = (Stage) addBtn.getScene().getWindow();
            BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/tao-phieu-nhap-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onEditBtnClick(){}
    @FXML
    private void onDeleteBtnClick(){
    	try {
			PhieuNhap selectedPN = importTicketTable.getSelectionModel().getSelectedItem();
			if (selectedPN != null) {
				String maPhieu = selectedPN.getMaPhieu();
				// Đổi status thành Deleted
				selectedPN.setStatus(PhieuStatus.Deleted);
				pnDAO.update(selectedPN);
				// update số lượng sản phẩm
				List<ChiTietPhieuNhap> list = ctpnDAO.findByMaPhieu(maPhieu);
				for (ChiTietPhieuNhap item :list) {
					DienThoai dt = item.getDienThoai();
					dt.setSoLuong(dt.getSoLuong()-item.getSoLuong());
					dtDAO.update(dt);
				}
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Xóa phiếu nhập thành công");
				alert.showAndWait();
				reloadPNList();
			} else {
				throw new IOException("Không có phiếu nhập nào được chọn");
			}
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
    }
    @FXML
    private void onDetailBtnClick(){
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-chi-tiet-phieu-nhap-view.fxml"));
			Pane item = fxmlLoader.load();
			ChiTietPhieuNhapController controller = fxmlLoader.getController();
			controller.setPhieuNhapController(this);
			Scene scene = new Scene(item);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){}
    
    public void reloadPNList() {
		pnList.clear();
		pnList.addAll(pnDAO.findAll());
		importTicketTable.getItems().clear();
		importTicketTable.getItems().addAll(pnList);
		importTicketTable.refresh();
	}
    
    @FXML
    private void onResetBtnClick(){
    	reloadPNList();
    }
    @FXML
    private void onFilterCbboxAction(){
    	String status = filterCbbox.getValue();
    	pnList.clear();
		pnList.addAll(pnDAO.findByStatus(status));
		importTicketTable.getItems().clear();
		importTicketTable.getItems().addAll(pnList);
		importTicketTable.refresh();
    }
}

