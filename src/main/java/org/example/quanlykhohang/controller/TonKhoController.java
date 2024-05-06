package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.entity.DienThoai;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TonKhoController {
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

//    @FXML
//    private TableView productTable;
    @FXML
    private void onAddBtnClick(){}
    @FXML
    private void onEditBtnClick(){}
    @FXML
    private void onDeleteBtnClick(){}
    @FXML
    private void onDetailBtnClick(){}
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chọn ví trí lưu file excel");
        String userHome = System.getProperty("user.home");
        directoryChooser.setInitialDirectory(new File(userHome));   //cái này có khi phải sửa lại trên window
        Stage stage = new Stage();
        File selectedFile = directoryChooser.showDialog(stage);
        if(selectedFile!=null) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Inventory");
                XSSFRow header = sheet.createRow(0);
                for (int i = 0; i < productTable.getColumns().size(); i++) {
                    TableColumn column = productTable.getColumns().get(i);
                    XSSFCell cell = header.createCell(i);
                    cell.setCellValue(column.getText());
                }
                for (int j = 0; j < productTable.getItems().size(); j++) {
                    XSSFRow row = sheet.createRow(j + 1);
                    for (int k = 0; k < productTable.getColumns().size(); k++) {
                        XSSFCell cell = row.createCell(k);

                        if (productTable.getColumns().get(k).getCellData(j) != null) {
                            cell.setCellValue(productTable.getColumns().get(k).getCellData(j).toString());
                        }

                    }
                }
                FileOutputStream out = new FileOutputStream(selectedFile.getAbsolutePath()+"/Inventory.xlsx");
                workbook.write(out);
                workbook.close();
                out.close();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Thông báo");
                alert1.setHeaderText(null);
                alert1.setContentText("Xuất excel file thành công");
                alert1.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi ");
                alert1.setHeaderText(null);
                alert1.setContentText("Đã có lỗi xảy ra khi xuất excel");
                alert1.showAndWait();
            }
        }
    }
    @FXML
    private void onResetBtnClick(){}
    @FXML
    private void onFilterCbboxAction(){}
    @FXML
    private TableView<DienThoai> productTable;

    @FXML
    private TableColumn<DienThoai, String> idPhoneColumn;

    @FXML
    private TableColumn<DienThoai, String> namePhoneColumn;

    @FXML
    private TableColumn<DienThoai, Double> inputPriceColumn;

    @FXML
    private TableColumn<DienThoai, Double> outputPriceColumn;
    @FXML
    private TableColumn<DienThoai, Integer> soLuongColumn;

    private DienThoaiDAO phoneDAO = new DienThoaiDAO(); // Khai báo DAO

    @FXML
    private void initialize() {
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Thiết lập cột và đổ dữ liệu vào TableView
        idPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("maDT"));
        namePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("tenDT"));
        inputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        outputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("giaXuat"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        // Lấy danh sách điện thoại từ DAO
        List<DienThoai> phoneList = phoneDAO.findAll();

        // Đổ danh sách điện thoại vào TableView
        productTable.getItems().addAll(phoneList);
    }
}
