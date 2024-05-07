package org.example.quanlykhohang.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.dao.DonXuatHangDAO;
import org.example.quanlykhohang.dao.KhachHangDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.entity.*;
import org.example.quanlykhohang.util.EditQuantityDialog;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TaoDonXuatController {
    private DienThoaiDAO dienThoaiDAO = new DienThoaiDAO();
    private KhachHangDAO khachHangDAO = new KhachHangDAO();
    private DonXuatHangDAO donXuatHangDAO = new DonXuatHangDAO();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
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
    private ComboBox<KhachHang> customerCbbox;
    @FXML
    private TableView<DienThoai> productTable;
    @FXML
    private TableView<SanPhamTrongDonHangDTO> exportFormTable;
    @FXML
    private Button exportBtn;
    @FXML
    private Label totalMoneyLabel;
    @FXML
    private Button backBtn;
    private ObservableList<SanPhamTrongDonHangDTO> exportList = FXCollections.observableArrayList();
    @FXML
    private void onSearchTxtAction(){
        productTable.setItems(searchDienThoai());
    }
    @FXML
    private void onResetBtnClick(){
        resetData();
    }
    @FXML
    private void onAddBtnClick(){
        try{
            DienThoai selectedProd = productTable.getSelectionModel().getSelectedItem();
            SanPhamTrongDonHangDTO convert = new SanPhamTrongDonHangDTO();
            convert.setMaDT(selectedProd.getMaDT());
            if(exportList.contains(convert)){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi ");
                alert1.setHeaderText(null);
                alert1.setContentText("Không thể thêm sản phẩm đã có trong đơn");
                alert1.showAndWait();
                return;
            }
            if(selectedProd == null){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi ");
                alert1.setHeaderText(null);
                alert1.setContentText("Chọn sản phẩm muốn thêm số lượng");
                alert1.showAndWait();
                return;
            }
            Integer soLuong  = Integer.valueOf(quantityTxt.getText());
            if(soLuong <= 0 ){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi ");
                alert1.setHeaderText(null);
                alert1.setContentText("Số lượng phải là số nguyên dương");
                alert1.showAndWait();
                return;
            }
            if(soLuong>selectedProd.getSoLuong()){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi ");
                alert1.setHeaderText(null);
                alert1.setContentText("Số lượng xuất không được lớn hơn tồn kho");
                alert1.showAndWait();
                return;
            }
            SanPhamTrongDonHangDTO sanPhamTrongDonHangDTO = new SanPhamTrongDonHangDTO(selectedProd.getMaDT(),selectedProd.getTenDT(),selectedProd.getGiaXuat(),soLuong, selectedProd.getSoLuong(),selectedProd);
            exportList.add(sanPhamTrongDonHangDTO);
            loadDataExport();
            setTotalMoneyLabel();
        } catch (Exception e){
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi ");
            alert1.setHeaderText(null);
            alert1.setContentText("Có lỗi xảy ra\nChọn lại sản phẩm hoặc điền số lượng đúng định dạng số nguyên duương");
            alert1.showAndWait();
        }

    }
    @FXML
    private void onCustomerCbboxAction(){

    }
    @FXML
    private void onImportExcelBtnClick(){}


    @FXML
    private void onEditQuantityBtnClick(){
        try{
            SanPhamTrongDonHangDTO selectedProd = exportFormTable.getSelectionModel().getSelectedItem();
            if(selectedProd == null){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi");
                alert1.setHeaderText(null);
                alert1.setContentText("Chọn sản phẩm muốn thay đổi số lượng");
                alert1.showAndWait();
                return;
            }
            Integer soLuong = EditQuantityDialog.display(selectedProd.getSoLuongTon(),selectedProd.getSoLuong());
            System.out.println("Edit quantity "+soLuong);
            for(SanPhamTrongDonHangDTO sp: exportList){
                if(sp.equals(selectedProd)){
                    sp.setSoLuong(soLuong);
                    break;
                }
            }
            loadDataExport();
            setTotalMoneyLabel();
        } catch (Exception e){
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi ");
            alert1.setHeaderText(null);
            alert1.setContentText("Số lượng xuất phải là số nguyên");
            alert1.showAndWait();
        }
    }
    @FXML
    private void onDeleteProductBtnClick(){
        SanPhamTrongDonHangDTO selectedProd = exportFormTable.getSelectionModel().getSelectedItem();
        if(selectedProd == null){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Chọn sản phẩm muốn thay đổi số lượng");
            alert1.showAndWait();
            return;
        }
        exportList.remove(selectedProd);
        setTotalMoneyLabel();
        loadDataExport();
    }
    @FXML
    private void onExportBtnClick(){
        try{
            if(exportList.isEmpty()){
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Lỗi");
                alert1.setHeaderText(null);
                alert1.setContentText("Thêm sản phẩm vào đơn trước khi tạo");
                alert1.showAndWait();
                return;
            }
            KhachHang kh  = customerCbbox.getValue();
            if(kh == null){
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Lỗi");
                alert1.setHeaderText(null);
                alert1.setContentText("Chọn khách hàng trước khi tạo");
                alert1.showAndWait();
                return;

            }
            NhanVien nv = nhanVienDAO.findById(UserSession.getInstance().getUserId());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String id = "DH_"+timestamp.getTime();
            DonXuatHang donXuatHang = new DonXuatHang(id,getTongTien(),timestamp,"pending",nv,kh);
            List<ChiTietDonXuatHang> chiTietDonXuatHangList = new ArrayList<ChiTietDonXuatHang>();
            for(SanPhamTrongDonHangDTO sp : exportList){
                ChiTietDonXuatHang chiTietDonXuatHang = new ChiTietDonXuatHang(donXuatHang,sp.getDienThoai(),sp.getSoLuong(),sp.getGiaXuat());
                chiTietDonXuatHangList.add(chiTietDonXuatHang);
            }
            donXuatHang.setChiTietDonXuatHangList(chiTietDonXuatHangList);

            donXuatHangDAO.create(donXuatHang);
            for(ChiTietDonXuatHang ct: chiTietDonXuatHangList){
                DienThoai dt = ct.getDienThoai();
                dt.setSoLuong(dt.getSoLuong()-ct.getSoLuong());
                dienThoaiDAO.update(dt);
            }

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Thành công");
            alert1.setHeaderText(null);
            alert1.setContentText("Tạo đơn hàng thành công");
            alert1.showAndWait();
            back();
        } catch (Exception e){
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Có lỗi xảy ra trong quá trình tạo đơn");
            alert1.showAndWait();
            return;
        }

    }
    @FXML
    private void onBackBtnClick(){
        back();
    }
    private void back(){
        try {
            Stage currentStage = (Stage) backBtn.getScene().getWindow();
            BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/don-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            borderPane.getLeft().setDisable(false);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize(){
        setUpProductTable();
        setUpExportFormTable();
        // setup cbbox
        customerCbbox.setConverter(new StringConverter<KhachHang>() {
            @Override
            public String toString(KhachHang khachHang) {
                if (khachHang != null) {
                    return khachHang.getTenKhachHang();
                }
                return "";
            }

            @Override
            public KhachHang fromString(String s) {
                return null;
            }
        });
        customerCbbox.setItems(getAllKhachHang());

        // setup creator text
        creatorTxt.setText(UserSession.getInstance().getUserName());
        creatorTxt.setDisable(true);

        // setup total label
        setTotalMoneyLabel();

    }
    private void setUpProductTable(){
        productTable.getColumns().clear();
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<DienThoai, String> idColumn = new TableColumn<>("Mã điện thoại");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maDT"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DienThoai, String> nameColumn = new TableColumn<>("Tên điện thoại");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tenDT"));
        nameColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DienThoai, String> visiblePrice = new TableColumn<>("Giá xuất");
        visiblePrice.setCellValueFactory(cellData->{
            Double value = cellData.getValue().getGiaXuat();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedNumber = decimalFormat.format(value);
            return new SimpleStringProperty(formattedNumber);
        });
        visiblePrice.setStyle("-fx-alignment: CENTER;");

        TableColumn<DienThoai, Integer> quantityColumn = new TableColumn<>("Số lượng trong kho");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        quantityColumn.setStyle("-fx-alignment: CENTER;");

        productTable.getColumns().addAll(idColumn, nameColumn, visiblePrice,quantityColumn);
        productTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                DienThoai selectedRow = (DienThoai) productTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getClass() + " " + selectedRow.toString() );
                }
            }
        });
        productTable.setItems(getAllDienThoai());
    }
    private void setUpExportFormTable(){
        exportFormTable.getColumns().clear();
        exportFormTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn numberColumn = new TableColumn<>("#");
        numberColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SanPhamTrongDonHangDTO, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<SanPhamTrongDonHangDTO, String> p) {
                return new ReadOnlyObjectWrapper((exportFormTable.getItems().indexOf(p.getValue()) +1) + "");
            }
        });
        numberColumn.setSortable(false);
        numberColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<SanPhamTrongDonHangDTO, String> idColumn = new TableColumn<>("Mã điện thoại");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maDT"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<SanPhamTrongDonHangDTO, String> nameColumn = new TableColumn<>("Tên điện thoại");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tenDT"));
        nameColumn.setStyle("-fx-alignment: CENTER;");


        TableColumn<SanPhamTrongDonHangDTO, String> visiblePrice = new TableColumn<>("Giá xuất");
        visiblePrice.setCellValueFactory(cellData->{
            Double value = cellData.getValue().getGiaXuat();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedNumber = decimalFormat.format(value);
            return new SimpleStringProperty(formattedNumber);
        });
        visiblePrice.setStyle("-fx-alignment: CENTER;");

        TableColumn<SanPhamTrongDonHangDTO, Integer> quantityColumn = new TableColumn<>("Số lượng xuất hàng");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        quantityColumn.setStyle("-fx-alignment: CENTER;");

        exportFormTable.getColumns().addAll(numberColumn,idColumn, nameColumn, visiblePrice,quantityColumn);
        exportFormTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                SanPhamTrongDonHangDTO selectedRow = exportFormTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getClass() + " " + selectedRow.toString() );
                }
            }
        });
        exportFormTable.setItems(exportList);
        loadDataExport();
        searchTxt.setPromptText("Nhập từ khoá ở đây...");
    }
    private void loadDataExport(){
        exportFormTable.refresh();
    }
    private ObservableList<DienThoai> getAllDienThoai() {
        ObservableList<DienThoai> data = FXCollections.observableArrayList();
        List<DienThoai> dienThoaiList = dienThoaiDAO.findAll();
        for (DienThoai dt : dienThoaiList) {
            data.add(dt);
        }
        return data;
    }
    private ObservableList<KhachHang> getAllKhachHang() {
        ObservableList<KhachHang> data = FXCollections.observableArrayList();
        List<KhachHang> khachHangList = khachHangDAO.findAll();
        System.out.println("get all kh");
        for (KhachHang kh : khachHangList) {
            System.out.println(kh.getTenKhachHang());
            data.add(kh);
        }
        return data;
    }
    private void resetData(){
        searchTxt.clear();
        productTable.setItems(getAllDienThoai());
    }
    private Double getTongTien(){
        Double result = 0.0;
        for(SanPhamTrongDonHangDTO sp : exportList){
            result += sp.getGiaXuat() * sp.getSoLuong();
        }
        return result;
    }
    private void setTotalMoneyLabel(){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedNumber = decimalFormat.format(getTongTien()) +" VND";
        totalMoneyLabel.setText(formattedNumber);
    }
    private ObservableList<DienThoai> searchDienThoai(){
        String keyword = searchTxt.getText();
        ObservableList<DienThoai> data = FXCollections.observableArrayList();
        List<DienThoai> dienThoaiList = dienThoaiDAO.findByKeyword(keyword);
        for(DienThoai dt: dienThoaiList){
            data.add(dt);
        }
        return data;
    }
}
