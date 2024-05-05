package org.example.quanlykhohang.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.dao.DonXuatHangDAO;
import org.example.quanlykhohang.entity.ChiTietDonXuatHang;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.DonXuatHang;
import org.example.quanlykhohang.entity.KhachHang;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class DonXuatController {
    private DonXuatHangDAO donXuatHangDAO = new DonXuatHangDAO();
    private DienThoaiDAO dienThoaiDAO = new DienThoaiDAO();
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
    private TableView<DonXuatHang> exportFormTable;
    @FXML
    private void onAddBtnClick(){
        try {
            Stage currentStage = (Stage) addBtn.getScene().getWindow();
            BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/tao-don-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            borderPane.getLeft().setDisable(true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onEditBtnClick(){
        DonXuatHang donXuatHang = exportFormTable.getSelectionModel().getSelectedItem();
        if(donXuatHang !=null){
            if(donXuatHang.getTrangThai().equals("done")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Bạn không thể sửa đơn hàng đã giải quyết");
                alert.showAndWait();
            }
            try {
                Stage currentStage = (Stage) editBtn.getScene().getWindow();
                BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/sua-don-xuat-view.fxml"));
                Pane item = fxmlLoader.load();

                SuaDonXuatController controller = fxmlLoader.getController();
                controller.setDonXuatHang(donXuatHang);

                borderPane.setRight(item);
                borderPane.getLeft().setDisable(true);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một đơn hàng để sửa.");
            alert.showAndWait();
        }

    }
    @FXML
    private void onDeleteBtnClick(){
        DonXuatHang donXuatHang = exportFormTable.getSelectionModel().getSelectedItem();

        if (donXuatHang != null) {
            if(donXuatHang.getTrangThai().equals("done")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Bạn không thể xoá đơn hàng đã giải quyết");
                alert.showAndWait();
            }

            List<ChiTietDonXuatHang> chiTietDonXuatHangList = donXuatHang.getChiTietDonXuatHangList();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa đơn hàng này?");


            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                donXuatHangDAO.delete(donXuatHang.getMaDon());
                exportFormTable.getItems().remove(donXuatHang);

                for(ChiTietDonXuatHang ct: chiTietDonXuatHangList){
                    DienThoai dt = ct.getDienThoai();
                    dt.setSoLuong(dt.getSoLuong()+ ct.getSoLuong());
                    dienThoaiDAO.update(dt);
                }


                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Thông báo");
                alert1.setHeaderText(null);
                alert1.setContentText("Xoá đơn hàng thành công.");
                alert1.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một đơn hàng để xóa.");
            alert.showAndWait();
        }

    }
    @FXML
    private void onDetailBtnClick(){
        System.out.println("xem chi tiết");
        DonXuatHang donXuatHang = exportFormTable.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-chi-tiet-don-xuat-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();

            // Get the controller of the loaded FXML file
            ChiTietDonXuatController controller = fxmlLoader.getController();
            controller.initData(donXuatHang);

            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show(); // Show the stage
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một đơn hàng để xem chi tiết");
            alert.showAndWait();
        }
    }
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){}
    @FXML
    private void onResetBtnClick(){resetData();}
    private void resetData(){
        searchTxt.clear();
        exportFormTable.setItems(getAllDonXuat());
        filterCbbox.setValue("Tất cả");
    }
    @FXML
    private void onFilterCbboxAction(){
        exportFormTable.setItems(searchDonXuat());
    }
    @FXML
    private void onSearchTxtAction(){
        exportFormTable.setItems(searchDonXuat());
    }
    @FXML
    private void initialize(){
        exportFormTable.getColumns().clear();
        TableColumn<DonXuatHang, String> idColumn = new TableColumn<>("Mã đơn xuất");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maDon"));

        TableColumn<DonXuatHang, String> authorColumn = new TableColumn<>("Người tạo đơn");
        authorColumn.setCellValueFactory(cellData  -> {
            String nguoiTaoDon = cellData.getValue().getNhanVien().getHo() + " " + cellData.getValue().getNhanVien().getTen();
            return new SimpleStringProperty(nguoiTaoDon);
        });
        TableColumn<DonXuatHang, String> customerColumn = new TableColumn<>("Khách hàng");
        customerColumn.setCellValueFactory(cellData  -> {
            String khachHang = cellData.getValue().getKhachHang().getTenKhachHang();
            return new SimpleStringProperty(khachHang);
        });

        TableColumn<DonXuatHang, String> timeColumn = new TableColumn<>("Thời gian tạo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("thoiGianTao"));

        TableColumn<DonXuatHang, String> totalColumn = new TableColumn<>("Tổng tiền");
        totalColumn.setCellValueFactory(cellData->{
            Double value = cellData.getValue().getTongTien();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedNumber = decimalFormat.format(value);
            return new SimpleStringProperty(formattedNumber);
        });

        TableColumn<DonXuatHang, String> statusColumn = new TableColumn<>("Trạng thái");
        statusColumn.setCellValueFactory(cellData->{
            String value = cellData.getValue().getTrangThai();
            String formatTrangThai = value.equals("done")?"Hoàn thành":"Đang chờ";
            return new SimpleStringProperty(formatTrangThai);
        });

        exportFormTable.getColumns().addAll(idColumn, authorColumn,customerColumn, timeColumn, totalColumn,statusColumn);
        exportFormTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                DonXuatHang selectedRow = exportFormTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getClass() + " mã: " + selectedRow.getKhachHang().getMaKhachHang() );
                }
            }
        });

        exportFormTable.setItems(getAllDonXuat());
        setUpFilterBox();
    }
    private ObservableList<DonXuatHang> getAllDonXuat() {
        ObservableList<DonXuatHang> data = FXCollections.observableArrayList();
        List<DonXuatHang> donXuatHangList = donXuatHangDAO.findAll();
        for (DonXuatHang dh : donXuatHangList) {
            data.add(dh);
        }
        return data;
    }
    private void setUpFilterBox(){
        ObservableList<String> data = FXCollections.observableArrayList();
        data.add("Tất cả");
        data.add("Đang chờ");
        data.add("Hoàn thành");
        filterCbbox.setItems(data);
        filterCbbox.setValue("Tất cả");
    }
    private ObservableList<DonXuatHang> searchDonXuat(){
        ObservableList<DonXuatHang> data = FXCollections.observableArrayList();
        String keyword = searchTxt.getText();
        String filter = filterCbbox.getSelectionModel().getSelectedItem();
        String convertFilter = filter.equals("Tất cả")?"all":filter.equals("Hoàn thành")?"done":"pending";
        List<DonXuatHang> donXuatHangList  = donXuatHangDAO.searchByKeyword(keyword,convertFilter);
        for (DonXuatHang dh : donXuatHangList) {
            data.add(dh);
        }
        return data;
    }
}

