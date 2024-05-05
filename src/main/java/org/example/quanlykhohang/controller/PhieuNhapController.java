package org.example.quanlykhohang.controller;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.PhieuNhapDAO;
import org.example.quanlykhohang.entity.ChiTietPhieuNhap;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.NhaCungCap;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.PhieuNhap;
import org.example.quanlykhohang.entity.UserSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class PhieuNhapController {
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
	
	private ObservableList<PhieuNhap> pnList = FXCollections.observableArrayList();
	private final DecimalFormat format = new DecimalFormat("#,###.0");
	
	public TableView<PhieuNhap> getPNTable() {
		return this.importTicketTable;
	}
    
    @FXML
	private void initialize() {
    	PhieuNhapDAO pnDAO = new PhieuNhapDAO();
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
    	tongTienColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
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
		PhieuNhapDAO pnDAO = new PhieuNhapDAO();
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
		PhieuNhapDAO pnDAO = new PhieuNhapDAO();
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
    private void onFilterCbboxAction(){}
}

