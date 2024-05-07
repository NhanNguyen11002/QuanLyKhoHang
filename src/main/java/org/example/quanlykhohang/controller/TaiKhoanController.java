package org.example.quanlykhohang.controller;

import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.*;
import org.example.quanlykhohang.util.JpaUtils;
import javafx.scene.input.KeyCode;
import org.mindrot.jbcrypt.BCrypt;


public class TaiKhoanController {
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
    private ComboBox filterCbbox;

    @FXML
    private TextField searchTxt;


    @FXML
    private TableView<TaiKhoanNhanVienDTO> accountTable;
    @FXML
    private void onAddBtnClick(){
        try {
        // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-them-tai-khoan-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();
            // Get the controller of the loaded FXML file
            ThemTaiKhoanController controller = fxmlLoader.getController();
            // Gửi dữ liệu từ table sang controller ThemTaiKhoanController
            controller.setTaiKhoanController(this); // Truyền đối tượng TaiKhoanController vào ThemTaiKhoanController
            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);

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
        // Lấy hàng được chọn từ bảng
        TaiKhoanNhanVienDTO selectedRow = accountTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-tai-khoan-view.fxml"));
                // Load the root pane
                Pane item = fxmlLoader.load();

                // Get the controller of the loaded FXML file
                SuaTaiKhoanController controller = fxmlLoader.getController();
                controller.setTaiKhoanController(this); // Truyền đối tượng TaiKhoanController vào ThemTaiKhoanController

                // Truyền dữ liệu từ hàng được chọn vào form chi tiết
                controller.initData(selectedRow);

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
            alert.setContentText("Vui lòng chọn một tài khoản để sửa.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onDeleteBtnClick() {

            // Lấy hàng được chọn
            TaiKhoanNhanVienDTO selectedRow = accountTable.getSelectionModel().getSelectedItem();
            if (selectedRow.getMaNhanVien() == UserSession.getInstance().getMaNhanVien()) {
                showError("Không thể xóa tài khoản của chính mình!!!");
            } else {
                if (selectedRow != null) {
                    // Tạo hộp thoại xác nhận
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Xác nhận xóa");
                    alert.setHeaderText(null);
                    alert.setContentText("Bạn có chắc chắn muốn xóa tài khoản này?");

                    // Xác nhận xóa
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Gọi phương thức xóa từ TaiKhoanDAO
                        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
                        NhanVienDAO nhanVienDAO = new NhanVienDAO();
                        try {
                            taiKhoanDAO.delete(selectedRow.getMaNhanVien());
                            nhanVienDAO.delete(selectedRow.getMaNhanVien());
                            // Refresh bảng sau khi xóa
                            accountTable.getItems().remove(selectedRow);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showError("Không thể xóa do có bản ghi liên kết");
                        }
                    }
                } else {
                    // Hiển thị thông báo nếu không có hàng nào được chọn
                    showAlert("Vui lòng chọn một tài khoản để xóa");
                }
            }

    }

    @FXML
    private void onDetailBtnClick(){
        // Lấy hàng được chọn từ bảng
        TaiKhoanNhanVienDTO selectedRow = accountTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-chi-tiet-tai-khoan-view.fxml"));
                // Load the root pane
                Pane item = fxmlLoader.load();

                // Get the controller of the loaded FXML file
                ChiTietTaiKhoanController controller = fxmlLoader.getController();
                // Truyền dữ liệu từ hàng được chọn vào form chi tiết
                controller.initData(selectedRow);

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
            alert.setContentText("Vui lòng chọn một tài khoản để xem chi tiết.");
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
                        String firstName = row.getCell(1).getStringCellValue();
                        String lastName = row.getCell(2).getStringCellValue();
                        LocalDate birthday = LocalDate.parse(row.getCell(3).getStringCellValue());
                        String gender = row.getCell(4).getStringCellValue();

                        String phone = row.getCell(5).getStringCellValue();
                        String address = row.getCell(6).getStringCellValue();
                        String username = row.getCell(7).getStringCellValue();
                        String password;
                        Cell cell = row.getCell(8);
                        if (cell != null) {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                double numericValue = cell.getNumericCellValue();
                                password = String.valueOf((int) numericValue);
                            } else {
                                password = cell.getStringCellValue();
                            }
                        } else {
                            password = ""; // Xử lý trường hợp ô rỗng
                        }

                        String email =String.valueOf(row.getCell(9).getStringCellValue());
                        Role role = Role.valueOf(row.getCell(10).getStringCellValue());
                        LocalDate startDate = LocalDate.parse(row.getCell(11).getStringCellValue());
                        TaiKhoanDAO taiKhoanDao = new TaiKhoanDAO();
                        NhanVienDAO nhanVienDAO = new NhanVienDAO();
                        if(taiKhoanDao.findByEmail(email) == null|| taiKhoanDao.findByPhone(phone)==null || taiKhoanDao.findByUsername(username)==null) {
                            NhanVien nhanVien = new NhanVien(firstName, lastName, birthday, gender.equalsIgnoreCase("Nam") ? Gender.Male : Gender.Female, startDate, null, phone, address, email);
                            nhanVienDAO.create(nhanVien);
                            // Tạo một đối tượng TaiKhoan
                            TaiKhoan taiKhoan = new TaiKhoan(username, BCrypt.hashpw(password, BCrypt.gensalt())   , role, true, nhanVien);
                            // Thiết lập quan hệ giữa nhân viên và tài khoản
                            taiKhoan.setNhanVien(nhanVien);
                            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

                            // Lưu tài khoản và nhân viên vào cơ sở dữ liệu
                            taiKhoanDAO.create(taiKhoan);
                        }

                    }
                    updateTable();

                    showSuccess("Nhập excel thành công");

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
                showAlert("Vui lòng chọn một file .xlsx hoặc .xls");
                return;
            }
        }
    }
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
                XSSFSheet sheet = workbook.createSheet("Account");
                XSSFRow header = sheet.createRow(0);
                for (int i = 0; i < accountTable.getColumns().size(); i++) {
                    TableColumn column = accountTable.getColumns().get(i);
                    XSSFCell cell = header.createCell(i);
                    cell.setCellValue(column.getText());
                }
                for (int j = 0; j < accountTable.getItems().size(); j++) {
                    XSSFRow row = sheet.createRow(j + 1);
                    for (int k = 0; k < accountTable.getColumns().size(); k++) {
                        XSSFCell cell = row.createCell(k);

                        if (accountTable.getColumns().get(k).getCellData(j) != null) {
                            cell.setCellValue(accountTable.getColumns().get(k).getCellData(j).toString());
                        }

                    }
                }
                FileOutputStream out = new FileOutputStream(selectedFile.getAbsolutePath()+"/Account.xlsx");
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
    public void onResetBtnClick(){
        accountTable.setItems(getAllTaiKhoanNhanVien());
        searchTxt.setText("");
    }
    @FXML
    private void onSearchEnterClick() {
        if (searchTxt.getText().isEmpty()) {
            showAlert("Hãy nhập mã nhân viên cần tìm kiếm");

        }
        else {
            ObservableList<TaiKhoanNhanVienDTO> data = FXCollections.observableArrayList();

            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            TaiKhoan taiKhoan = taiKhoanDAO.findByMaNhanVien1(Integer.parseInt(searchTxt.getText()));
            if (taiKhoan == null)
                showAlert("Không tìm thấy tài khoản có mã nhân viên này");
            else {
                TaiKhoanNhanVienDTO dto = new TaiKhoanNhanVienDTO();
                dto.setMaNhanVien(taiKhoan.getNhanVien().getMaNhanVien());
                dto.setHo(taiKhoan.getNhanVien().getHo());
                dto.setTen(taiKhoan.getNhanVien().getTen());
                dto.setNgaySinh(taiKhoan.getNhanVien().getNgaySinh());
                dto.setGioiTinh(taiKhoan.getNhanVien().getGioiTinh().equals(Gender.Male) ? "Nam" : "Nữ"); // Kiểm tra giới tính và thiết lập giá trị tương ứng
                dto.setSdt(taiKhoan.getNhanVien().getSdt());
                dto.setDiaChi(taiKhoan.getNhanVien().getDiaChi());
                dto.setTenDangNhap(taiKhoan.getUsername());
                dto.setEmail(taiKhoan.getNhanVien().getEmail());
                dto.setVaiTro(taiKhoan.getVaiTro());
                dto.setTrangThai(taiKhoan.isDangHoatDong());
                dto.setNgayBatDau(taiKhoan.getNhanVien().getNgayBatDau());
                dto.setNgayKetThuc(taiKhoan.getNhanVien().getNgayKetThuc());
                data.add(dto);
                accountTable.setItems(data);
            }
        }

    }
    public void updateTable(){
        accountTable.setItems(getAllTaiKhoanNhanVien());
    }

    public void setAccountTable(ObservableList<TaiKhoanNhanVienDTO> data) {
        accountTable.setItems(data);
    }
    @FXML
    private void onFilterCbboxAction(){
        if (filterCbbox.getValue()== Role.Admin){
            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            List<TaiKhoan> taiKhoans = taiKhoanDAO.findByRole(Role.Admin);
            if (taiKhoans == null)
                showAlert("Không tìm thấy tài khoản");
            else {
                filterData(taiKhoans);
            }
        }
        else if (filterCbbox.getValue()==Role.Staff) {
            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            List<TaiKhoan> taiKhoans = taiKhoanDAO.findByRole(Role.Staff);
            if (taiKhoans == null)
                showAlert("Không tìm thấy tài khoản");
            else {
                filterData(taiKhoans);
            }
        }
        else {
            accountTable.setItems(getAllTaiKhoanNhanVien());
        }
    }
    @FXML
    private void initialize() {
        accountTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        searchTxt.setOnAction(event -> {
            // Thực hiện xử lý tại đây
            onSearchEnterClick();
        });
        filterCbbox.getItems().addAll("Tất cả", Role.Admin, Role.Staff);

        TableColumn<TaiKhoanNhanVienDTO, String> sttColumn = new TableColumn<>("Mã nhân viên");
        sttColumn.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        sttColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> hoColumn = new TableColumn<>("Họ");
        hoColumn.setCellValueFactory(new PropertyValueFactory<>("ho"));
        hoColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> tenColumn = new TableColumn<>("Tên");
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        tenColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> ngaySinhColumn = new TableColumn<>("Ngày sinh");
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        ngaySinhColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> gioiTinhColumn = new TableColumn<>("Giới tính");
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        gioiTinhColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> sdtColumn = new TableColumn<>("SDT");
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        sdtColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> diaChiColumn = new TableColumn<>("Địa chỉ");
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        diaChiColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> tenDangNhapColumn = new TableColumn<>("Tên đăng nhập");
        tenDangNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenDangNhap"));
        tenDangNhapColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, Role> vaiTroColumn = new TableColumn<>("Vai trò");
        vaiTroColumn.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));
        vaiTroColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> trangThaiColumn = new TableColumn<>("Trạng thái");
        trangThaiColumn.setCellValueFactory(cellData -> {
            boolean trangThai = cellData.getValue().getTrangThai();
            return new SimpleStringProperty(trangThai ? "Đang hoạt động" : "Đã khóa");
        });
        trangThaiColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> ngayBatDauColumn = new TableColumn<>("Ngày bắt đầu");
        ngayBatDauColumn.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        ngayBatDauColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<TaiKhoanNhanVienDTO, String> ngayKetThucColumn = new TableColumn<>("Ngày kết thúc");
        ngayKetThucColumn.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        ngayKetThucColumn.setStyle("-fx-alignment: CENTER;");
        accountTable.getColumns().addAll(sttColumn, hoColumn, tenColumn, ngaySinhColumn, gioiTinhColumn, sdtColumn, diaChiColumn, tenDangNhapColumn,
                emailColumn, vaiTroColumn, trangThaiColumn, ngayBatDauColumn, ngayKetThucColumn/* Add other columns */);
        accountTable.setOnMouseClicked(event -> {
        if (event.getClickCount() == 1) { // Kiểm tra xem có phải là một lần nhấp chuột hay không
            TaiKhoanNhanVienDTO selectedRow = accountTable.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                // Xử lý sự kiện khi một hàng được chọn
                System.out.println("Đã chọn hàng: " + selectedRow.getMaNhanVien());
            }
        }
    });

        // Load data into the table
        accountTable.setItems(getAllTaiKhoanNhanVien());
    }
    private ObservableList<TaiKhoanNhanVienDTO> getAllTaiKhoanNhanVien() {
        ObservableList<TaiKhoanNhanVienDTO> data = FXCollections.observableArrayList();

        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(); 
        NhanVienDAO nhanVienDAO = new NhanVienDAO(); 

        List<TaiKhoan> taiKhoanList = taiKhoanDAO.findAll();
        List<NhanVien> nhanVienList = nhanVienDAO.findAll();

        for (int i = 0; i < Math.min(taiKhoanList.size(), nhanVienList.size()); i++) {
            TaiKhoan taiKhoan = taiKhoanList.get(i);
            NhanVien nhanVien = nhanVienList.get(i);

            TaiKhoanNhanVienDTO dto = new TaiKhoanNhanVienDTO();
            dto.setMaNhanVien(taiKhoan.getNhanVien().getMaNhanVien());
            dto.setHo(nhanVien.getHo());
            dto.setTen(nhanVien.getTen());
            dto.setNgaySinh(nhanVien.getNgaySinh());
            dto.setGioiTinh(nhanVien.getGioiTinh().equals(Gender.Male) ? "Nam" : "Nữ"); // Kiểm tra giới tính và thiết lập giá trị tương ứng
            dto.setSdt(nhanVien.getSdt());
            dto.setDiaChi(nhanVien.getDiaChi());
            dto.setTenDangNhap(taiKhoan.getUsername());
            dto.setEmail(nhanVien.getEmail());
            dto.setVaiTro(taiKhoan.getVaiTro());
            dto.setTrangThai(taiKhoan.isDangHoatDong());
            dto.setNgayBatDau(nhanVien.getNgayBatDau());
            dto.setNgayKetThuc(nhanVien.getNgayKetThuc());

            // Set other fields accordingly

            data.add(dto);
        }

        return data;
    }
    private void filterData(List<TaiKhoan> taiKhoans) {
        ObservableList<TaiKhoanNhanVienDTO> data = FXCollections.observableArrayList();
        for (TaiKhoan taiKhoan : taiKhoans) {
            TaiKhoanNhanVienDTO dto = new TaiKhoanNhanVienDTO();
            dto.setMaNhanVien(taiKhoan.getNhanVien().getMaNhanVien());
            dto.setHo(taiKhoan.getNhanVien().getHo());
            dto.setTen(taiKhoan.getNhanVien().getTen());
            dto.setNgaySinh(taiKhoan.getNhanVien().getNgaySinh());
            dto.setGioiTinh(taiKhoan.getNhanVien().getGioiTinh().equals(Gender.Male) ? "Nam" : "Nữ"); // Kiểm tra giới tính và thiết lập giá trị tương ứng
            dto.setSdt(taiKhoan.getNhanVien().getSdt());
            dto.setDiaChi(taiKhoan.getNhanVien().getDiaChi());
            dto.setTenDangNhap(taiKhoan.getUsername());
            dto.setEmail(taiKhoan.getNhanVien().getEmail());
            dto.setVaiTro(taiKhoan.getVaiTro());
            dto.setTrangThai(taiKhoan.isDangHoatDong());
            dto.setNgayBatDau(taiKhoan.getNhanVien().getNgayBatDau());
            dto.setNgayKetThuc(taiKhoan.getNhanVien().getNgayKetThuc());
            data.add(dto);
        }
        accountTable.setItems(data);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
