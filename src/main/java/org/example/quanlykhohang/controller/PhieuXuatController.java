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
import org.example.quanlykhohang.entity.PhieuStatus;
import org.example.quanlykhohang.entity.PhieuXuat;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

                phieuXuat.setStatus(PhieuStatus.Deleted);
                phieuXuatDAO.update(phieuXuat);
                DonXuatHang donXuatHang = phieuXuat.getDonXuatHang();
                donXuatHang.setTrangThai("pending");
                donXuatHangDAO.update(donXuatHang);

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Thông báo");
                alert1.setHeaderText(null);
                alert1.setContentText("Xoá phiếu xuất thành công.");
                alert1.showAndWait();
                reset();
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

            Pane item = fxmlLoader.load();


            ChiTietPhieuXuatController controller = fxmlLoader.getController();
            controller.initData(phieuXuat);


            Scene scene = new Scene(item);


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
        exportTicketTable.setItems(getAllPhieuXuat());
    }
    @FXML
    private void onFilterCbboxAction(){
        exportTicketTable.setItems(searchPhieuXuat());
    }
    @FXML
    private void onSearchTxtAction(){
        exportTicketTable.setItems(searchPhieuXuat());
    }
    @FXML
    private void initialize(){
        exportTicketTable.getColumns().clear();
        exportTicketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<PhieuXuat, String> idColumn = new TableColumn<>("Mã phiếu xuất");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieu"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuXuat, String> authorColumn = new TableColumn<>("Người tạo phiếu");
        authorColumn.setCellValueFactory(cellData  -> {
            String nguoiTaoDon = cellData.getValue().getNguoiTao().getHo() + " " + cellData.getValue().getNguoiTao().getTen();
            return new SimpleStringProperty(nguoiTaoDon);
        });
        authorColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuXuat, String> customerColumn = new TableColumn<>("Khách hàng");
        customerColumn.setCellValueFactory(cellData  -> {
            String khachHang = cellData.getValue().getDonXuatHang().getKhachHang().getTenKhachHang();
            return new SimpleStringProperty(khachHang);
        });
        customerColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuXuat, String> timeColumn = new TableColumn<>("Thời gian tạo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("thoiGianTao"));
        timeColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuXuat, String> totalColumn = new TableColumn<>("Tổng tiền");
        totalColumn.setCellValueFactory(cellData->{
            Double value = cellData.getValue().getDonXuatHang().getTongTien();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedNumber = decimalFormat.format(value);
            return new SimpleStringProperty(formattedNumber);
        });
        totalColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuXuat, String> vehicle = new TableColumn<>("Biển số xe");
        vehicle.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));
        vehicle.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuXuat, String> statusColumn = new TableColumn<>("Trạng thái");
        statusColumn.setCellValueFactory(cellData->{
            PhieuStatus value = cellData.getValue().getStatus();
            String formatTrangThai = value.equals(PhieuStatus.Done)?"Hoàn thành":"Đã xoá";
            return new SimpleStringProperty(formatTrangThai);
        });
        statusColumn.setStyle("-fx-alignment: CENTER;");

        exportTicketTable.getColumns().addAll(idColumn, authorColumn,customerColumn, timeColumn, totalColumn,vehicle, statusColumn);
        exportTicketTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                PhieuXuat phieuXuat = exportTicketTable.getSelectionModel().getSelectedItem();
                System.out.println("Đã chọn phiếu: "+phieuXuat.getMaPhieu());
            }
        });
        exportTicketTable.setItems(getAllPhieuXuat());

        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Tất cả");
        options.add("Hoàn thành");
        options.add("Đã xoá");
        filterCbbox.setItems(options);
        filterCbbox.setValue("Tất cả");
        searchTxt.setPromptText("Nhập từ khoá ở đây...");
    }
    private ObservableList<PhieuXuat> searchPhieuXuat(){
        ObservableList<PhieuXuat> data = FXCollections.observableArrayList();
        String keyword = searchTxt.getText();
        String filter = filterCbbox.getSelectionModel().getSelectedItem();
        List<PhieuXuat> phieuXuatList;
        if(filter.equals("Tất cả")){
            phieuXuatList = phieuXuatDAO.searchByKeyword(keyword);
        } else {
            String convert =filter.equals("Hoàn thành")?"done":"deleted";
            phieuXuatList = phieuXuatDAO.searchByKeyword(keyword,convert);
        }
        String convert = filter.equals("Tất cả")?"all":filter.equals("Hoàn thành")?"done":"deleted";
        for(PhieuXuat px: phieuXuatList){
            data.add(px);
        }
        return data;
    }
    private ObservableList<PhieuXuat> getAllPhieuXuat(){
        ObservableList<PhieuXuat> data = FXCollections.observableArrayList();
        List<PhieuXuat> phieuXuatList = phieuXuatDAO.findAll();
        for(PhieuXuat px: phieuXuatList){
            data.add(px);
        }
        return data;
    }

}

