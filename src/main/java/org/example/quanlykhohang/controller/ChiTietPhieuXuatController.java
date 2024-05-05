/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.quanlykhohang.entity.ChiTietDonXuatHang;
import org.example.quanlykhohang.entity.PhieuXuat;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ChiTietPhieuXuatController {
    private PhieuXuat phieuXuat;
    @FXML
    private Label  maPhieuLbl;
    @FXML
    private Label  maDonLbl;
    @FXML
    private Label bienSoLbl;
    @FXML
    private Label  thoiGianTaoLbl;
    @FXML
    private Label  nguoiTaoLbl;
    @FXML
    private Label  tongTienLbl;
    @FXML
    private Button  xuatPDFBtn;
    @FXML
    private TableView  ctPhieuXuatTbl;
    private ObservableList<ChiTietDonXuatHang> chiTietDonXuatHangObservableList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */

    public void initData(PhieuXuat phieuXuat){
        this.phieuXuat = phieuXuat;
        maPhieuLbl.setText(phieuXuat.getMaPhieu());
        maDonLbl.setText(phieuXuat.getDonXuatHang().getMaDon());
        bienSoLbl.setText(phieuXuat.getBienSoXe());
        thoiGianTaoLbl.setText(phieuXuat.getThoiGianTao().toString());
        nguoiTaoLbl.setText(phieuXuat.getNguoiTao().getHo()+" "+phieuXuat.getNguoiTao().getTen());
        Double totalValue  = phieuXuat.getDonXuatHang().getTongTien();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedValue = decimalFormat.format(totalValue);
        tongTienLbl.setText(formattedValue);
        for(ChiTietDonXuatHang ct:phieuXuat.getDonXuatHang().getChiTietDonXuatHangList()){
            chiTietDonXuatHangObservableList.add(ct);
        }
        ctPhieuXuatTbl.setItems(chiTietDonXuatHangObservableList);
    }
    @FXML
    public void initialize(){
        ctPhieuXuatTbl.getColumns().clear();
        TableColumn<ChiTietDonXuatHang, String> prodId = new TableColumn<>("Mã điện thoại");
        prodId.setCellValueFactory(cellData  -> {
            String maDT = cellData.getValue().getDienThoai().getMaDT();
            return new SimpleStringProperty(maDT);
        });
        TableColumn<ChiTietDonXuatHang, String> prodName = new TableColumn<>("Tên điện thoại");
        prodName.setCellValueFactory(cellData  -> {
            String tenDT = cellData.getValue().getDienThoai().getTenDT();
            return new SimpleStringProperty(tenDT);
        });
        TableColumn<ChiTietDonXuatHang, String> quantity = new TableColumn<>("Số lượng");
        quantity.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        TableColumn<ChiTietDonXuatHang, String> price = new TableColumn<>("Đơn giá");
        price.setCellValueFactory(cellData  -> {
            Double value  = cellData.getValue().getDonGia();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedValue = decimalFormat.format(value);
            return new SimpleStringProperty(formattedValue);
        });
        ctPhieuXuatTbl.getColumns().addAll(prodId, prodName,quantity,price);
    }
    @FXML
    private void onXuatPDFBtnClick(){

    }
    
}
