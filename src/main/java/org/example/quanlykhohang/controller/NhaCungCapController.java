package org.example.quanlykhohang.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
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
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.*;

public class NhaCungCapController {
    NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
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
    private TableView<NhaCungCap> providerTable;
    @FXML
    private void onAddBtnClick(){
        try {
        // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-them-nha-cung-cap-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();

            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);
            ThemNhaCungCapController controller = fxmlLoader.getController();
            controller.setNhaCungCapController(this);

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
        NhaCungCap selectedRow = providerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-nha-cung-cap-view.fxml"));
                // Load the root pane
                Pane item = fxmlLoader.load();

                // Get the controller of the loaded FXML file
                SuaNhaCungCapController controller = fxmlLoader.getController();
                controller.initData(selectedRow);
                controller.setNhaCungCapController(this);

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
            alert.setContentText("Vui lòng chọn một nhà cung cấp để sửa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onDeleteBtnClick(){
        NhaCungCap selectedRow = (NhaCungCap)providerTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa nhà cung cấp này?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                nhaCungCapDAO.delete(selectedRow.getMaNhaCungCap());
                providerTable.getItems().remove(selectedRow);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một nhà cung cấp để xóa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onImportExcelBtnClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file excel cần nhập");
        String userHome = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(userHome));
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
                        NhaCungCap ncc = new NhaCungCap(name,sdt,diaChi,email);
                        if(!nhaCungCapDAO.existByEmail(email)|| !nhaCungCapDAO.existBySDT(sdt)) {
                            nhaCungCapDAO.create(ncc);
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
        String userHome = System.getProperty("user.home");
        directoryChooser.setInitialDirectory(new File(userHome));  //cái này có khi phải sửa lại trên window
        Stage stage = new Stage();
        File selectedFile = directoryChooser.showDialog(stage);
        if(selectedFile!=null) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Supplier");
                XSSFRow header = sheet.createRow(0);
                for (int i = 0; i < providerTable.getColumns().size(); i++) {
                    TableColumn column = providerTable.getColumns().get(i);
                    XSSFCell cell = header.createCell(i);
                    cell.setCellValue(column.getText());
                }
                for (int j = 0; j < providerTable.getItems().size(); j++) {
                    XSSFRow row = sheet.createRow(j + 1);
                    for (int k = 0; k < providerTable.getColumns().size(); k++) {
                        XSSFCell cell = row.createCell(k);

                        if (providerTable.getColumns().get(k).getCellData(j) != null) {
                            cell.setCellValue(providerTable.getColumns().get(k).getCellData(j).toString());
                        }

                    }
                }
                FileOutputStream out = new FileOutputStream(selectedFile.getAbsolutePath()+"/Supplier.xlsx");
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
        }}
    @FXML
    private void onResetBtnClick(){
        resetData();
    }
    @FXML
    private void onSearchTxtAction(){
        providerTable.setItems(searchNhaCungCap(searchTxt.getText()));
    }
    @FXML
    private void onFilterCbboxAction(){}
    @FXML
    private void initialize(){
        providerTable.getColumns().clear();
        providerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<NhaCungCap, String> idColumn = new TableColumn<>("Mã nhà cung cấp");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maNhaCungCap"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhaCungCap, String> nameColumn = new TableColumn<>("Tên nhà cung cấp");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tenNhaCungCap"));
        nameColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhaCungCap, String> sdtColumn = new TableColumn<>("Số điện thoại");
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        sdtColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhaCungCap, String> diaChiColumn = new TableColumn<>("Địa chỉ");
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        diaChiColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhaCungCap, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setStyle("-fx-alignment: CENTER;");

        providerTable.getColumns().addAll(idColumn, nameColumn, sdtColumn, diaChiColumn, emailColumn);
        providerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                NhaCungCap selectedRow = (NhaCungCap)providerTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Đã chọn hàng: " + selectedRow.getMaNhaCungCap());
                }
            }
        });

        providerTable.setItems(getAllNhaCungCap());
        searchTxt.setPromptText("Nhập từ khoá ở đây...");

    }
    public void resetData(){
        System.out.println("Reset");
        providerTable.setItems(getAllNhaCungCap());
        searchTxt.clear();
    }

    private ObservableList<NhaCungCap> getAllNhaCungCap() {
        ObservableList<NhaCungCap> data = FXCollections.observableArrayList();

        List<NhaCungCap> nhaCungCapList = nhaCungCapDAO.findAll();

        for (NhaCungCap ncc : nhaCungCapList) {
            // Set other fields accordingly
            data.add(ncc);
        }

        return data;
    }
    private ObservableList<NhaCungCap> searchNhaCungCap(String keyword) {
        ObservableList<NhaCungCap> data = FXCollections.observableArrayList();
        List<NhaCungCap> nhaCungCapList = nhaCungCapDAO.findByKeyword(keyword);
        for (NhaCungCap ncc : nhaCungCapList) {
            data.add(ncc);
        }

        return data;
    }
}
