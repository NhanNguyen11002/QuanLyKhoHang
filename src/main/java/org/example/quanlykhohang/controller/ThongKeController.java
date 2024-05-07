package org.example.quanlykhohang.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.*;
import org.example.quanlykhohang.dto.ProductDTO;
import org.example.quanlykhohang.entity.ChiTietPhieuNhap;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.KhachHang;
import org.example.quanlykhohang.entity.PhieuNhap;
import org.example.quanlykhohang.entity.PhieuStatus;
import org.example.quanlykhohang.entity.PhieuXuat;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

public class ThongKeController {
    KhachHangDAO khachHangDAO = new KhachHangDAO();
    NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    DienThoaiDAO dienThoaiDAO = new DienThoaiDAO();
    TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    ThongKeDAO thongKeDAO  = new ThongKeDAO();
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.0");
    @FXML
    private Label numProductLabel;
    @FXML
    private Label numProviderLabel;
    @FXML
    private Label numCustomerLabel;
    @FXML
    private Label numAccountLabel;
    @FXML
    private void initialize(){
        setUp4Label();
        setUpTicketTable();
        setUpProductTable();
        searchAllProduct();
    }
    private void setUp4Label(){
        Long numProduct = dienThoaiDAO.count();
        Long numProvider = nhaCungCapDAO.count();
        Long numCustomer = khachHangDAO.count();
        Long numAccount = taiKhoanDAO.count();
        numProductLabel.setText(String.valueOf(numProduct));
        numProviderLabel.setText(String.valueOf(numProvider));
        numCustomerLabel.setText(String.valueOf(numCustomer));
        numAccountLabel.setText(String.valueOf(numAccount));
    }
    private void setUpTicketTable(){
        ticketTable.getColumns().clear();
        ticketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Object, String> idColumn = new TableColumn<>("Mã phiếu");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieu"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> nameColumn = new TableColumn<>("Người tạo đơn");
        nameColumn.setCellValueFactory(cellData->{
            Object obj = cellData.getValue();
            if(obj instanceof PhieuNhap){
                PhieuNhap pn = (PhieuNhap) obj;
                String nguoiTaoDon = pn.getNguoiTao().getHo() + " " + pn.getNguoiTao().getTen();
                return new SimpleStringProperty(nguoiTaoDon);
            }else{
                PhieuXuat pn = (PhieuXuat) obj;
                String nguoiTaoDon = pn.getNguoiTao().getHo() + " " + pn.getNguoiTao().getTen();
                return new SimpleStringProperty(nguoiTaoDon);
            }
        });
        nameColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> timeColumn = new TableColumn<>("Thời gian tạo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("thoiGianTao"));
        timeColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> totalColumn = new TableColumn<>("Tổng tiền");
        totalColumn.setCellValueFactory(cellData->{
            Object obj = cellData.getValue();
            if(obj instanceof PhieuNhap){
                PhieuNhap pn = (PhieuNhap) obj;
                return new SimpleStringProperty(decimalFormat.format(pn.getTongTien()));
            } else {
                PhieuXuat px = (PhieuXuat) obj;
                return new SimpleStringProperty(decimalFormat.format(px.getDonXuatHang().getTongTien()));
            }
        });
        totalColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> statusColumn = new TableColumn<>("Trạng thái");
        statusColumn.setCellValueFactory(cellData->{
            Object obj = cellData.getValue();
            if ( obj instanceof PhieuNhap){
                PhieuStatus value = ((PhieuNhap) obj).getStatus();
                String formatTrangThai = value.equals(PhieuStatus.Done)?"Hoàn thành":"Đã xoá";
                return new SimpleStringProperty(formatTrangThai);
            }else {
                PhieuStatus value = ((PhieuXuat) obj).getStatus();
                String formatTrangThai = value.equals(PhieuStatus.Done)?"Hoàn thành":"Đã xoá";
                return new SimpleStringProperty(formatTrangThai);
            }
        });
        statusColumn.setStyle("-fx-alignment: CENTER;");

        ticketTable.getColumns().addAll(idColumn, nameColumn, timeColumn, totalColumn, statusColumn);
    }


    // product tab
    @FXML
    private TextField searchTxt;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private Button resetBtn;
    @FXML
    private TableView<ProductDTO> productTable;
    @FXML
	private TableColumn<ProductDTO, String> maDTColumn;
	@FXML
	private TableColumn<ProductDTO, String> tenDTColumn;
	@FXML
	private TableColumn<ProductDTO, Long> soLuongNhapColumn;
	@FXML
	private TableColumn<ProductDTO, Long> soLuongXuatColumn;
	
	private ObservableList<ProductDTO> productList = FXCollections.observableArrayList();
    
    private void setUpProductTable(){
    	productTable.getColumns().clear();
    	productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    	maDTColumn.setCellValueFactory(new PropertyValueFactory<>("maDT"));
    	maDTColumn.setStyle("-fx-alignment: CENTER;");
    	tenDTColumn.setCellValueFactory(new PropertyValueFactory<>("tenDT"));
    	tenDTColumn.setStyle("-fx-alignment: CENTER;");
    	soLuongNhapColumn.setCellValueFactory(new PropertyValueFactory<>("soLuongNhap"));
    	soLuongNhapColumn.setStyle("-fx-alignment: CENTER;");
    	soLuongXuatColumn.setCellValueFactory(new PropertyValueFactory<>("soLuongXuat"));
    	soLuongXuatColumn.setStyle("-fx-alignment: CENTER;");
    	productTable.getColumns().addAll(maDTColumn, tenDTColumn, soLuongNhapColumn, soLuongXuatColumn);
    }
    
    @FXML
    private void onProductTabChange(){
    	searchAllProduct();
    	for (ProductDTO p : productList) {
    		System.out.println(p.toString());
    	}
    }
    
    private void searchAllProduct(){
    	productList.clear();
        List<Object> objectList;
        String keyword = searchTxt.getText();
        objectList = thongKeDAO.searchAllProduct(keyword);
        System.out.println("List ne ==========================================");
        for(Object obj : objectList){
        	Object[] row = (Object[]) obj;
        	String maDT = (String) row[0];
            String tenDT = (String) row[1];
            Long soLuongNhap = (Long) row[2];
            Long soLuongXuat = (Long) row[3];
            System.out.println(maDT + " " + tenDT + " " + soLuongNhap + " " + soLuongXuat);
            ProductDTO productDTO = new ProductDTO(maDT, tenDT, soLuongNhap, soLuongXuat);
            productList.add(productDTO);
        }
        productTable.getItems().clear();
    	productTable.getItems().addAll(productList);
        productTable.refresh();
    }
    
    @FXML
    private void onSearchProductTxtAction(){
    	productList.clear();
    	String keyword = searchTxt.getText();
    	List<Object> objectList;
        objectList = thongKeDAO.searchAllProduct(keyword);
        for(Object obj : objectList){
        	Object[] row = (Object[]) obj;
        	String maDT = (String) row[0];
            String tenDT = (String) row[1];
            Long soLuongNhap = (Long) row[2];
            Long soLuongXuat = (Long) row[3];
            ProductDTO productDTO = new ProductDTO(maDT, tenDT, soLuongNhap, soLuongXuat);
            productList.add(productDTO);
        }
        productTable.getItems().clear();
    	productTable.getItems().addAll(productList);
        productTable.refresh();
    }

    @FXML
    private void onResetBtnClick(){
    	productList.clear();
    	List<Object> objectList;
        objectList = thongKeDAO.searchAllProduct("");
        for(Object obj : objectList){
        	Object[] row = (Object[]) obj;
        	String maDT = (String) row[0];
            String tenDT = (String) row[1];
            Long soLuongNhap = (Long) row[2];
            Long soLuongXuat = (Long) row[3];
            ProductDTO productDTO = new ProductDTO(maDT, tenDT, soLuongNhap, soLuongXuat);
            productList.add(productDTO);
        }
        productTable.getItems().clear();
    	productTable.getItems().addAll(productList);
        productTable.refresh();
    }


    // ticket tab
    @FXML
    private TextField searchTicketTxt;
    @FXML
    private DatePicker ticketFromDatePicker;
    @FXML
    private DatePicker ticketToDatePicker;
    @FXML
    private Button resetTicketBtn;
    @FXML
    private Button detailBtn;
    @FXML
    private TableView<Object> ticketTable;
    @FXML
    private Label numTicketLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private TextField fromTotalTxt;
    @FXML
    private TextField toTotalTxt;
    @FXML
    private void onSearchTicketTxtAction(){
        ticketTable.setItems(searchAllPhieu());
    }
    @FXML
    private void onTicketFromDatePickerAction(){
        if(ticketToDatePicker.getValue() == null) return;
        ticketTable.setItems(searchAllPhieu());
    }

    @FXML
    private void onTicketToDatePickerAction(){
        if(ticketFromDatePicker.getValue() == null) return;
        ticketTable.setItems(searchAllPhieu());
    }

    @FXML
    private void onResetTicketBtnClick(){
        fromTotalTxt.clear();
        toTotalTxt.clear();
        searchTicketTxt.clear();
        ticketFromDatePicker.setValue(null);
        ticketToDatePicker.setValue(null);
        ticketTable.setItems(searchAllPhieu());
    }

    @FXML
    private void onDetailBtnClick(){
        Object obj = ticketTable.getSelectionModel().getSelectedItem();
        if(obj == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Vui lòng chọn phiếu để xem chi tiết");
            alert1.showAndWait();
            return;
        }
        if(obj instanceof PhieuXuat) detailPhieuXuat((PhieuXuat)obj);
        //pn
    }
    private void detailPhieuXuat(PhieuXuat phieuXuat){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-chi-tiet-phieu-xuat-view.fxml"));

            Pane item = fxmlLoader.load();


            ChiTietPhieuXuatController controller = fxmlLoader.getController();
            controller.initData(phieuXuat);


            Scene scene = new Scene(item);


            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show(); // Show the stage
        } catch (Exception e){
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Có lỗi xảy ra khi xem chi tiết phiếu xuất");
            alert1.showAndWait();
            return;
        }
    }
    private void detailPhieuNhap(PhieuNhap phieuNhap){

    }
    @FXML
    private void onTicketTabChange(){

        ticketTable.setItems(searchAllPhieu());
        double number = thongKeDAO.getTotal();
        String formattedNumber = decimalFormat.format(number);
        totalLabel.setText(formattedNumber);

        numTicketLabel.setText(String.valueOf(thongKeDAO.count()));
    }
    @FXML
    private void onFromTotalTxtAction(){
        try{
            if(toTotalTxt.getText().equals("")) return;
            double parse = Double.parseDouble(fromTotalTxt.getText());
            ticketTable.setItems(searchAllPhieu());

        } catch (Exception e){
            e.printStackTrace();
            String message = e instanceof NumberFormatException?"Vui lòng nhập số tiền thích hợp":"Có lỗi xảy ra khi lọc";
            fromTotalTxt.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi ");
            alert1.setHeaderText(null);
            alert1.setContentText(message);
            alert1.showAndWait();
        }
    }
    @FXML
    private void onToTotalTxtAction(){
        try{
            if(fromTotalTxt.getText().equals("")) return;
            double parse = Double.parseDouble(toTotalTxt.getText());
            ticketTable.setItems(searchAllPhieu());
        } catch (Exception e){
            e.printStackTrace();
            String message = e instanceof NumberFormatException?"Vui lòng nhập số tiền thích hợp":"Có lỗi xảy ra khi lọc";
            toTotalTxt.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi ");
            alert1.setHeaderText(null);
            alert1.setContentText(message);
            alert1.showAndWait();
        }
    }


    private ObservableList<Object> searchAllPhieu(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        List<Object> objectList;
        String keyword = searchTicketTxt.getText();
        System.out.println("key"+keyword);
        boolean time = ticketFromDatePicker.getValue() != null && ticketToDatePicker.getValue() != null;
        System.out.println("time"+time);
        boolean total = !fromTotalTxt.getText().equals("") && !toTotalTxt.getText().equals("");
        System.out.println("time"+total);
        if(time && total){
            Timestamp timestampFrom = Timestamp.valueOf(ticketFromDatePicker.getValue().atStartOfDay());
            Timestamp timestampTo = Timestamp.valueOf(ticketToDatePicker.getValue().atStartOfDay());
            double totalFrom = Double.parseDouble(fromTotalTxt.getText());
            double totalTo = Double.parseDouble(toTotalTxt.getText());
            objectList = thongKeDAO.searchAllPhieu(keyword,timestampFrom,timestampTo,totalFrom,totalTo);
        } else if(time){
            Timestamp timestampFrom = Timestamp.valueOf(ticketFromDatePicker.getValue().atStartOfDay());
            Timestamp timestampTo = Timestamp.valueOf(ticketToDatePicker.getValue().atStartOfDay());
            objectList = thongKeDAO.searchAllPhieu(keyword,timestampFrom,timestampTo);
        } else if(total){
            double totalFrom = Double.parseDouble(fromTotalTxt.getText());
            double totalTo = Double.parseDouble(toTotalTxt.getText());
            objectList = thongKeDAO.searchAllPhieu(keyword,totalFrom,totalTo);
        } else {
            objectList = thongKeDAO.searchAllPhieu(keyword);
        }
        data.addAll(objectList);
        System.out.println("List ne ==========================================");
        for(Object obj:objectList){
            System.out.println(obj.toString());
        }
        return data;

    }

}
