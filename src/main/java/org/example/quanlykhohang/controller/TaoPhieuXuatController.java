package org.example.quanlykhohang.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.DonXuatHangDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.PhieuXuatDAO;
import org.example.quanlykhohang.entity.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

public class TaoPhieuXuatController {
    private DonXuatHangDAO donXuatHangDAO  = new DonXuatHangDAO();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private PhieuXuatDAO  phieuXuatDAO = new PhieuXuatDAO();
    @FXML
    private TextField searchTxt;
    @FXML
    private Button resetBtn;
    @FXML
    private Button backBtn;
    @FXML
    private TableView<DonXuatHang> exportFormTable;
    @FXML
    private TextField creatorTxt;
    @FXML
    private Button exportBtn;
    @FXML
    private TableView exportFormDetailTable;
    @FXML
    private TextField vehicleTxt;
    @FXML
    private ObservableList<ChiTietDonXuatHang> exportList = FXCollections.observableArrayList();

    @FXML
    private void onResetBtnClick(){
        reset();
    }
    private void reset(){
        searchTxt.clear();
        exportFormTable.setItems(searchDonXuat());
        exportFormTable.getSelectionModel().clearSelection();
        exportList.clear();
        exportFormDetailTable.refresh();
    }
    @FXML
    private void onBackBtnClick(){
        back();
    }
    private void back(){
        try {
            Stage currentStage = (Stage) backBtn.getScene().getWindow();
            BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            borderPane.getLeft().setDisable(false);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onSearchTxtAction(){
        exportFormDetailTable.setItems(searchDonXuat());
    }

    @FXML
    private void initialize(){
        setUpExportFormTable();
        setUpExportDetailTable();
        creatorTxt.setText(UserSession.getInstance().getUserName());
        creatorTxt.setDisable(true);
        searchTxt.setPromptText("Nhập từ khoá ở đây...");
    }
    private void setUpExportFormTable(){
        exportFormTable.getColumns().clear();
        exportFormTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<DonXuatHang, String> idColumn = new TableColumn<>("Mã đơn xuất");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maDon"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DonXuatHang, String> authorColumn = new TableColumn<>("Người tạo đơn");
        authorColumn.setCellValueFactory(cellData  -> {
            String nguoiTaoDon = cellData.getValue().getNhanVien().getHo() + " " + cellData.getValue().getNhanVien().getTen();
            return new SimpleStringProperty(nguoiTaoDon);
        });
        authorColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DonXuatHang, String> customerColumn = new TableColumn<>("Khách hàng");
        customerColumn.setCellValueFactory(cellData  -> {
            String khachHang = cellData.getValue().getKhachHang().getTenKhachHang();
            return new SimpleStringProperty(khachHang);
        });
        customerColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DonXuatHang, String> timeColumn = new TableColumn<>("Thời gian tạo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("thoiGianTao"));
        timeColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DonXuatHang, String> totalColumn = new TableColumn<>("Tổng tiền");
        totalColumn.setCellValueFactory(cellData->{
            Double value = cellData.getValue().getTongTien();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedNumber = decimalFormat.format(value);
            return new SimpleStringProperty(formattedNumber);
        });
        totalColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DonXuatHang, String> statusColumn = new TableColumn<>("Trạng thái");
        statusColumn.setCellValueFactory(cellData->{
            String value = cellData.getValue().getTrangThai();
            String formatTrangThai = value.equals("done")?"Hoàn thành":"Đang chờ";
            return new SimpleStringProperty(formatTrangThai);
        });
        statusColumn.setStyle("-fx-alignment: CENTER;");

        exportFormTable.getColumns().addAll(idColumn, authorColumn,customerColumn, timeColumn, totalColumn,statusColumn);
        exportFormTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                DonXuatHang donXuatHang = exportFormTable.getSelectionModel().getSelectedItem();
                if(donXuatHang!= null){
                    System.out.println("Đã chọn đơn "+ donXuatHang.getMaDon());
                    exportList.clear();
                    List<ChiTietDonXuatHang> chiTietDonXuatHangList = donXuatHang.getChiTietDonXuatHangList();
                    for(ChiTietDonXuatHang ct: chiTietDonXuatHangList){
                        System.out.println("chi tiet: "+ct.getDienThoai().getTenDT());
                        exportList.add(ct);
                    }
                    exportFormDetailTable.refresh();
                }
            }
        });
        exportFormTable.setItems(searchDonXuat());
    }
    private void setUpExportDetailTable(){
        exportFormDetailTable.getColumns().clear();
        exportFormDetailTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn numberColumn = new TableColumn<>("#");
        numberColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SanPhamTrongDonHangDTO, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<SanPhamTrongDonHangDTO, String> p) {
                return new ReadOnlyObjectWrapper((exportFormTable.getItems().indexOf(p.getValue()) +1) + "");
            }
        });
        numberColumn.setSortable(false);
        numberColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietDonXuatHang, String> idColumn = new TableColumn<>("Mã điện thoại");
        idColumn.setCellValueFactory(cellData->{
            String maDT = cellData.getValue().getDienThoai().getMaDT();
            return new SimpleStringProperty(maDT);
        });
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietDonXuatHang, String> nameColumn = new TableColumn<>("Tên điện thoại");
        nameColumn.setCellValueFactory(cellData->{
            String tenDT = cellData.getValue().getDienThoai().getTenDT();
            return new SimpleStringProperty(tenDT);
        });
        nameColumn.setStyle("-fx-alignment: CENTER;");


        TableColumn<ChiTietDonXuatHang, String> visiblePrice = new TableColumn<>("Giá xuất");
        visiblePrice.setCellValueFactory(cellData->{
            Double value = cellData.getValue().getDonGia();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedNumber = decimalFormat.format(value);
            return new SimpleStringProperty(formattedNumber);
        });
        visiblePrice.setStyle("-fx-alignment: CENTER;");


        TableColumn<ChiTietDonXuatHang, Integer> quantityColumn = new TableColumn<>("Số lượng xuất hàng");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        quantityColumn.setStyle("-fx-alignment: CENTER;");

        exportFormDetailTable.getColumns().addAll(numberColumn,idColumn, nameColumn, visiblePrice,quantityColumn);
        exportFormDetailTable.setItems(exportList);
    }
    @FXML
    private void onExportBtnClick(){
        String bienSoXe = vehicleTxt.getText();
        DonXuatHang donXuatHang = exportFormTable.getSelectionModel().getSelectedItem();
        if(bienSoXe == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Vui lòng nhập biển số xe");
            alert1.showAndWait();
            return;
        }
        if(donXuatHang == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Vui lòng chọn đơn xuất hàng để tạo phiếu xuất");
            alert1.showAndWait();
            return;
        }
        if(donXuatHang.getTrangThai().equals("done")){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Không thể tạo phiếu xuất cho đơn xuất đã hoàn thành");
            alert1.showAndWait();
            return;
        }
        try {

            // create
            NhanVien nv = nhanVienDAO.findById(UserSession.getInstance().getUserId());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            PhieuXuat phieuXuat  = new PhieuXuat(nv,donXuatHang,bienSoXe,timestamp,PhieuStatus.Done);
            phieuXuatDAO.create(phieuXuat);
            donXuatHang.setTrangThai("done");
            donXuatHangDAO.update(donXuatHang);
            back();

        } catch (Exception e){
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Có lỗi xảy ra trong quá trình tạo phiếu xuất");
            alert1.showAndWait();
            return;
        }
    }

    private ObservableList<DonXuatHang> searchDonXuat(){
        ObservableList<DonXuatHang> data = FXCollections.observableArrayList();
        String keyword = searchTxt.getText();
        List<DonXuatHang> donXuatHangList  = donXuatHangDAO.searchByKeyword(keyword,"pending");
        for (DonXuatHang dh : donXuatHangList) {
            data.add(dh);
        }
        return data;
    }

}
