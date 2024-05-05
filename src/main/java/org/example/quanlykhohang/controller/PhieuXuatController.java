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
import org.example.quanlykhohang.dao.DonXuatHangDAO;
import org.example.quanlykhohang.dao.PhieuXuatDAO;
import org.example.quanlykhohang.entity.ChiTietDonXuatHang;
import org.example.quanlykhohang.entity.DonXuatHang;
import org.example.quanlykhohang.entity.PhieuXuat;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class PhieuXuatController {
    private DonXuatHangDAO donXuatHangDAO = new DonXuatHangDAO();
    private PhieuXuatDAO phieuXuatDAO = new PhieuXuatDAO();
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
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
    private TableView<PhieuXuat> exportTicketTable;
    @FXML
    private void onAddBtnClick(){
        try {
            Stage currentStage = (Stage) addBtn.getScene().getWindow();
            BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/tao-phieu-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            borderPane.getLeft().setDisable(true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onDeleteBtnClick(){
        PhieuXuat phieuXuat = exportTicketTable.getSelectionModel().getSelectedItem();
        if(phieuXuat == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Vui lòng chọn phiếu xuất để xoá");
            alert1.showAndWait();
            return;
        }
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa phiếu xuất này?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // xoa phieu xuat


                DonXuatHang donXuatHang = phieuXuat.getDonXuatHang();
                donXuatHang.setTrangThai("pending");
                donXuatHangDAO.update(donXuatHang);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Thông báo");
                alert1.setHeaderText(null);
                alert1.setContentText("Xoá phiếu xuất thành công.");
                alert1.showAndWait();
            }
        } catch (Exception e){
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Có lỗi xảy ra trong quá trình xoá phiếu xuất");
            alert1.showAndWait();

        }

    }
    @FXML
    private void onDetailBtnClick(){
        PhieuXuat phieuXuat = exportTicketTable.getSelectionModel().getSelectedItem();
        if(phieuXuat == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Vui lòng chọn phiếu xuất để xem chi tiết");
            alert1.showAndWait();
            return;
        }
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-chi-tiet-phieu-xuat-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();

            // Get the controller of the loaded FXML file
            ChiTietPhieuXuatController controller = fxmlLoader.getController();
            controller.initData(phieuXuat);

            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show(); // Show the stage
        } catch (Exception e){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Có lỗi xảy ra khi xem chi tiết phiếu xuất");
            alert1.showAndWait();
            return;
        }


    }
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){}
    @FXML
    private void onResetBtnClick(){
        reset();
    }
    private void reset(){
        searchTxt.clear();
        filterCbbox.setValue("Tất cả");
    }
    @FXML
    private void onFilterCbboxAction(){}
    @FXML
    private void initialize(){
        exportTicketTable.getColumns().clear();
        TableColumn<PhieuXuat, String> idColumn = new TableColumn<>("Mã phiếu xuất");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieuXuat"));

        TableColumn<PhieuXuat, String> authorColumn = new TableColumn<>("Người tạo phiếu");
        authorColumn.setCellValueFactory(cellData  -> {
            String nguoiTaoDon = cellData.getValue().getNguoiTao().getHo() + " " + cellData.getValue().getNguoiTao().getTen();
            return new SimpleStringProperty(nguoiTaoDon);
        });
        TableColumn<PhieuXuat, String> customerColumn = new TableColumn<>("Khách hàng");
        customerColumn.setCellValueFactory(cellData  -> {
            String khachHang = cellData.getValue().getDonXuatHang().getKhachHang().getTenKhachHang();
            return new SimpleStringProperty(khachHang);
        });

        TableColumn<PhieuXuat, String> timeColumn = new TableColumn<>("Thời gian tạo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("thoiGianTao"));

        TableColumn<PhieuXuat, String> totalColumn = new TableColumn<>("Tổng tiền");
        totalColumn.setCellValueFactory(cellData->{
            Double value = cellData.getValue().getDonXuatHang().getTongTien();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedNumber = decimalFormat.format(value);
            return new SimpleStringProperty(formattedNumber);
        });
        TableColumn<PhieuXuat, String> vehicle = new TableColumn<>("Biển số xe");
        vehicle.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));

//        TableColumn<PhieuXuat, String> statusColumn = new TableColumn<>("Trạng thái");
//        statusColumn.setCellValueFactory(cellData->{
//            String value = cellData.getValue().getTrangThai();
//            String formatTrangThai = value.equals("done")?"Hoàn thành":"Đã xoá";
//            return new SimpleStringProperty(formatTrangThai);
//        });

        exportTicketTable.getColumns().addAll(idColumn, authorColumn,customerColumn, timeColumn, totalColumn,vehicle);
        exportTicketTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                PhieuXuat phieuXuat = exportTicketTable.getSelectionModel().getSelectedItem();
                System.out.println("Đã chọn phiếu: "+phieuXuat.getMaPhieu());
            }
        });
//      exportTicketTable.setItems();


        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Tất cả");
        options.add("Hoàn thành");
        options.add("Đã xoá");
        filterCbbox.setItems(options);
        filterCbbox.setValue("Tất cả");
    }
}

