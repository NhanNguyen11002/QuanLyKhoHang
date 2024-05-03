package org.example.quanlykhohang.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.DonXuatHangDAO;
import org.example.quanlykhohang.entity.DonXuatHang;
import org.example.quanlykhohang.entity.KhachHang;

import java.io.IOException;
import java.util.List;

public class DonXuatController {
    private DonXuatHangDAO donXuatHangDAO = new DonXuatHangDAO();
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
    private TableView exportFormTable;
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
    private void onEditBtnClick(){}
    @FXML
    private void onDeleteBtnClick(){}
    @FXML
    private void onDetailBtnClick(){}
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){}
    @FXML
    private void onResetBtnClick(){resetData();}
    private void resetData(){
        searchTxt.clear();
        exportFormTable.setItems(getAllDonXuat());
    }
    @FXML
    private void onFilterCbboxAction(){}
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
        TableColumn<DonXuatHang, String> idAuthorColumn = new TableColumn<>("Mã người tạo đơn");
        idAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("nguoiTao"));
        idAuthorColumn.setVisible(false);

        TableColumn<DonXuatHang, String> customerColumn = new TableColumn<>("Khách hàng");
        customerColumn.setCellValueFactory(cellData  -> {
            String khachHang = cellData.getValue().getKhachHang().getTenKhachHang();
            return new SimpleStringProperty(khachHang);
        });
        TableColumn<DonXuatHang, String> idCustomerColumn = new TableColumn<>("Mã khách hàng");
        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        idCustomerColumn.setVisible(false);

        TableColumn<DonXuatHang, String> timeColumn = new TableColumn<>("Thời gian tạo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("thoiGianTao"));

        TableColumn<DonXuatHang, String> totalColumn = new TableColumn<>("Tổng tiền");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));

        TableColumn<DonXuatHang, String> statusColumn = new TableColumn<>("Trạng thái");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        exportFormTable.getColumns().addAll(idColumn, authorColumn, idAuthorColumn,customerColumn,idCustomerColumn, timeColumn, totalColumn,statusColumn);
        exportFormTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                DonXuatHang selectedRow = (DonXuatHang)exportFormTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getClass() + " " + selectedRow.toString() );
                }
            }
        });

        exportFormTable.setItems(getAllDonXuat());
    }
    private ObservableList<DonXuatHang> getAllDonXuat() {
        ObservableList<DonXuatHang> data = FXCollections.observableArrayList();
        List<DonXuatHang> donXuatHangList = donXuatHangDAO.findAll();
        for (DonXuatHang dh : donXuatHangList) {
            // Set other fields accordingly
            data.add(dh);
        }
        return data;
    }
}

