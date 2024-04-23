package org.example.quanlykhohang.controller;

import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.Role;
import org.example.quanlykhohang.entity.TaiKhoan;
import org.example.quanlykhohang.entity.TaiKhoanNhanVienDTO;
import org.example.quanlykhohang.util.JpaUtils;

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
    private ComboBox<String> filterCbbox;

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
    private void onDeleteBtnClick(){
        // Lấy hàng được chọn
        TaiKhoanNhanVienDTO selectedRow = accountTable.getSelectionModel().getSelectedItem();
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
                taiKhoanDAO.delete(selectedRow.getMaNhanVien());
                nhanVienDAO.delete(selectedRow.getMaNhanVien());
                // Refresh bảng sau khi xóa
                accountTable.getItems().remove(selectedRow);
            }
        } else {
            // Hiển thị thông báo nếu không có hàng nào được chọn
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một tài khoản để xóa.");
            alert.showAndWait();
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
    private void onImportExcelBtnClick(){}
    @FXML
    private void onExportExcelBtnClick(){}
    @FXML
    private void onResetBtnClick(){
        accountTable.setItems(getAllTaiKhoanNhanVien());
    }
    @FXML
    private void onFilterCbboxAction(){}
    @FXML
    private void initialize() {
        TableColumn<TaiKhoanNhanVienDTO, String> sttColumn = new TableColumn<>("Mã nhân viên");
        sttColumn.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));

        TableColumn<TaiKhoanNhanVienDTO, String> hoColumn = new TableColumn<>("Họ");
        hoColumn.setCellValueFactory(new PropertyValueFactory<>("ho"));

        TableColumn<TaiKhoanNhanVienDTO, String> tenColumn = new TableColumn<>("Tên");
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> ngaySinhColumn = new TableColumn<>("Ngày sinh");
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> gioiTinhColumn = new TableColumn<>("Giới tính");
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> sdtColumn = new TableColumn<>("SDT");
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> diaChiColumn = new TableColumn<>("Địa chỉ");
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> tenDangNhapColumn = new TableColumn<>("Tên đăng nhập");
        tenDangNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenDangNhap"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn<TaiKhoanNhanVienDTO, Role> vaiTroColumn = new TableColumn<>("Vai trò");
        vaiTroColumn.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> trangThaiColumn = new TableColumn<>("Trạng thái");
        trangThaiColumn.setCellValueFactory(cellData -> {
            boolean trangThai = cellData.getValue().getTrangThai();
            return new SimpleStringProperty(trangThai ? "Đang hoạt động" : "Đã khóa");
        });
        
        TableColumn<TaiKhoanNhanVienDTO, String> ngayBatDauColumn = new TableColumn<>("Ngày bắt đầu");
        ngayBatDauColumn.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        
        TableColumn<TaiKhoanNhanVienDTO, String> ngayKetThucColumn = new TableColumn<>("Ngày kết thúc");
        ngayKetThucColumn.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));

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
            dto.setGioiTinh(nhanVien.getGioiTinh());
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
}
