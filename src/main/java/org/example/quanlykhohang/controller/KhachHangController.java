package org.example.quanlykhohang.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.KhachHangDAO;
import org.example.quanlykhohang.entity.KhachHang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class KhachHangController {
    KhachHangDAO khachHangDAO = new KhachHangDAO();
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
    private TextField searchTxt;

    @FXML
    private TableView<KhachHang> customerTable;
    @FXML
    private void onAddBtnClick(){
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-them-khach-hang-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();

            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);
            ThemKhachHangController controller = fxmlLoader.getController();
            controller.setKhachHangController(this);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show(); // Show the stage
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onEditBtnClick(){
        KhachHang selectedRow = customerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-khach-hang-view.fxml"));
                // Load the root pane
                Pane item = fxmlLoader.load();

                // Get the controller of the loaded FXML file
                SuaKhachHangController controller = fxmlLoader.getController();
                controller.initData(selectedRow);
                controller.setKhachHangController(this);

                // Create a new scene with the loaded pane
                Scene scene = new Scene(item);

                // Create a new stage and set the scene
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show(); // Show the stage
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Hiển thị thông báo nếu không có hàng nào được chọn
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một khách hàng để sửa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onDeleteBtnClick(){
        KhachHang selectedRow = customerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa khách hàng này?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                khachHangDAO.delete(selectedRow.getMaKhachHang());
                customerTable.getItems().remove(selectedRow);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Thông báo");
                alert1.setHeaderText(null);
                alert1.setContentText("Xoá khách hàng thành công.");
                alert1.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một khách hàng để xóa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onImportExcelBtnClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file excel cần nhập");
        fileChooser.setInitialDirectory(new File("/Users"));
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            if(selectedFile.getName().endsWith(".xlsx") || selectedFile.getName().endsWith(".xls")){
                try {
                    FileInputStream fileIn = new FileInputStream(selectedFile);
                    Workbook workbook = WorkbookFactory.create(fileIn);
                    Sheet sheet = workbook.getSheetAt(0);
                    Row row;
                    boolean shouldBreak = false;
                    for(int i = 1; i<=sheet.getPhysicalNumberOfRows();i++){
                        row = sheet.getRow(i);
                        if(row == null ) {
                            break;
                        } else {
                            for (int j = 1;j<row.getLastCellNum(); j++){
                                if(row.getCell(j)==null){
                                    shouldBreak = true;
                                    break;
                                }
                            }
                        }
                        if(shouldBreak) break;
                        String name = row.getCell(1).getStringCellValue();
                        String sdt = row.getCell(2).getStringCellValue();
                        String diaChi = row.getCell(3).getStringCellValue();
                        String email = String.valueOf(row.getCell(4).getStringCellValue());
                        KhachHang kh = new KhachHang(name,sdt,diaChi,email);
                        if(!khachHangDAO.existsByEmail(email)|| !khachHangDAO.existsBySDT(sdt)) {
                            khachHangDAO.create(kh);
                        }

                    }
                    resetData();

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Thành công");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Nhập excel thành công");
                    alert1.showAndWait();

                    fileIn.close();
                    workbook.close();
                } catch (Exception e){
                    e.printStackTrace();
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Lỗi ");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Đã có lỗi xảy ra khi nhập excel\nĐảm bảo file excel không bị hỏng");
                    alert1.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn một file .xlsx hoặc .xls");
                alert.showAndWait();
                return;
            }
        }
    }
    @FXML
    private void onExportExcelBtnClick(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chọn ví trí lưu file excel");
        directoryChooser.setInitialDirectory(new File("/Users"));  //cái này có khi phải sửa lại trên window
        Stage stage = new Stage();
        File selectedFile = directoryChooser.showDialog(stage);
        if(selectedFile!=null) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Customer");
                XSSFRow header = sheet.createRow(0);
                for (int i = 0; i < customerTable.getColumns().size(); i++) {
                    TableColumn column = customerTable.getColumns().get(i);
                    XSSFCell cell = header.createCell(i);
                    cell.setCellValue(column.getText());
                }
                for (int j = 0; j < customerTable.getItems().size(); j++) {
                    XSSFRow row = sheet.createRow(j + 1);
                    for (int k = 0; k < customerTable.getColumns().size(); k++) {
                        XSSFCell cell = row.createCell(k);

                        if (customerTable.getColumns().get(k).getCellData(j) != null) {
                            cell.setCellValue(customerTable.getColumns().get(k).getCellData(j).toString());
                        }

                    }
                }
                FileOutputStream out = new FileOutputStream(selectedFile.getAbsolutePath()+"/Customer.xlsx");
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
    private void onResetBtnClick(){ resetData();}
    @FXML
    private void onSearchTxtAction(){
        customerTable.setItems(searchKhachHang(searchTxt.getText()));
    }
    @FXML
    private void initialize(){
        customerTable.getColumns().clear();
        TableColumn<KhachHang, String> idColumn = new TableColumn<>("Mã khách hàng");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));

        TableColumn<KhachHang, String> nameColumn = new TableColumn<>("Tên khách hàng");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));

        TableColumn<KhachHang, String> sdtColumn = new TableColumn<>("Số điện thoại");
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));

        TableColumn<KhachHang, String> diaChiColumn = new TableColumn<>("Địa chỉ");
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        TableColumn<KhachHang, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        customerTable.getColumns().addAll(idColumn, nameColumn, sdtColumn, diaChiColumn, emailColumn);
        customerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                KhachHang selectedRow = (KhachHang)customerTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getMaKhachHang());
                }
            }
        });

        customerTable.setItems(getAllKhachHang());

    }
    public void resetData(){
        System.out.println("Reset");
        customerTable.setItems(getAllKhachHang());
        searchTxt.clear();
    }

    private ObservableList<KhachHang> getAllKhachHang() {
        ObservableList<KhachHang> data = FXCollections.observableArrayList();

        List<KhachHang> khachHangList = khachHangDAO.findAll();

        for (KhachHang kh : khachHangList) {
            // Set other fields accordingly
            data.add(kh);
        }

        return data;
    }
    private ObservableList<KhachHang> searchKhachHang(String keyword) {
        ObservableList<KhachHang> data = FXCollections.observableArrayList();
        List<KhachHang> khachHangList = khachHangDAO.findByKeyword(keyword);
        for (KhachHang kh : khachHangList) {
            data.add(kh);
        }

        return data;
    }
}

