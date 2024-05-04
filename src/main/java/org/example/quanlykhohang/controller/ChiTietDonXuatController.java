package org.example.quanlykhohang.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.quanlykhohang.entity.ChiTietDonXuatHang;
import org.example.quanlykhohang.entity.DonXuatHang;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ChiTietDonXuatController {
    @FXML
    private Label maPhieuLbl;
    @FXML
    private Label  thoiGianTaoLbl;
    @FXML
    private Label  nguoiTaoLbl;
    @FXML
    private Label  tongTienLbl;
    @FXML
    private Button xuatPDFBtn;
    @FXML
    private void onXuatPDFBtnClick(){}
    @FXML
    private TableView ctDonXuatTbl;

    private ObservableList<ChiTietDonXuatHang> chiTietDonXuatHangObservableList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        ctDonXuatTbl.getColumns().clear();
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
        ctDonXuatTbl.getColumns().addAll(prodId, prodName,quantity,price);

    }
    public void initData(DonXuatHang donXuatHang){
        maPhieuLbl.setText(donXuatHang.getMaDon());
        thoiGianTaoLbl.setText(donXuatHang.getThoiGianTao().toString());
        nguoiTaoLbl.setText(donXuatHang.getNhanVien().getHo()+" "+donXuatHang.getNhanVien().getTen());
        Double totalValue  = donXuatHang.getTongTien();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedValue = decimalFormat.format(totalValue);
        tongTienLbl.setText(formattedValue);
        for(ChiTietDonXuatHang ct:donXuatHang.getChiTietDonXuatHangList()){
            chiTietDonXuatHangObservableList.add(ct);
        }
        ctDonXuatTbl.setItems(chiTietDonXuatHangObservableList);
    }

}
