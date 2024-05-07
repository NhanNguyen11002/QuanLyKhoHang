package org.example.quanlykhohang.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.*;
import org.example.quanlykhohang.entity.KhachHang;
import org.example.quanlykhohang.entity.PhieuNhap;
import org.example.quanlykhohang.entity.PhieuStatus;
import org.example.quanlykhohang.entity.PhieuXuat;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

public class ThongKeController {
    KhachHangDAO khachHangDAO = new KhachHangDAO();
    NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    DienThoaiDAO dienThoaiDAO = new DienThoaiDAO();
    TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    ThongKeDAO thongKeDAO  = new ThongKeDAO();
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.0");
    @FXML
    private Label numProductLabel;
    @FXML
    private Label numProviderLabel;
    @FXML
    private Label numCustomerLabel;
    @FXML
    private Label numAccountLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private void initialize(){

        setUp4Label();
        setUpTicketTable();
    }
    private void setUp4Label(){
        Long numProduct = dienThoaiDAO.count();
        Long numProvider = nhaCungCapDAO.count();
        Long numCustomer = khachHangDAO.count();
        Long numAccount = taiKhoanDAO.count();
        numProductLabel.setText(String.valueOf(numProduct));
        numProviderLabel.setText(String.valueOf(numProvider));
        numCustomerLabel.setText(String.valueOf(numCustomer));
        numAccountLabel.setText(String.valueOf(numAccount));
    }
    private void setUpTicketTable(){
        ticketTable.getColumns().clear();
        ticketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Object, String> idColumn = new TableColumn<>("Mã phiếu");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieu"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> nameColumn = new TableColumn<>("Người tạo đơn");
        nameColumn.setCellValueFactory(cellData->{
            Object obj = cellData.getValue();
            if(obj instanceof PhieuNhap){
                PhieuNhap pn = (PhieuNhap) obj;
                String nguoiTaoDon = pn.getNguoiTao().getHo() + " " + pn.getNguoiTao().getTen();
                return new SimpleStringProperty(nguoiTaoDon);
            }else{
                PhieuXuat pn = (PhieuXuat) obj;
                String nguoiTaoDon = pn.getNguoiTao().getHo() + " " + pn.getNguoiTao().getTen();
                return new SimpleStringProperty(nguoiTaoDon);
            }
        });
        nameColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> timeColumn = new TableColumn<>("Thời gian tạo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("thoiGianTao"));
        timeColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> totalColumn = new TableColumn<>("Tổng tiền");
        totalColumn.setCellValueFactory(cellData->{
            Object obj = cellData.getValue();
            if(obj instanceof PhieuNhap){
                PhieuNhap pn = (PhieuNhap) obj;
                return new SimpleStringProperty(decimalFormat.format(pn.getTongTien()));
            } else {
                PhieuXuat px = (PhieuXuat) obj;
                return new SimpleStringProperty(decimalFormat.format(px.getDonXuatHang().getTongTien()));
            }
        });
        totalColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> statusColumn = new TableColumn<>("Trạng thái");
        statusColumn.setCellValueFactory(cellData->{
            Object obj = cellData.getValue();
            if ( obj instanceof PhieuNhap){
                PhieuStatus value = ((PhieuNhap) obj).getStatus();
                String formatTrangThai = value.equals(PhieuStatus.Done)?"Hoàn thành":"Đã xoá";
                return new SimpleStringProperty(formatTrangThai);
            }else {
                PhieuStatus value = ((PhieuXuat) obj).getStatus();
                String formatTrangThai = value.equals(PhieuStatus.Done)?"Hoàn thành":"Đã xoá";
                return new SimpleStringProperty(formatTrangThai);
            }
        });
        statusColumn.setStyle("-fx-alignment: CENTER;");

        ticketTable.getColumns().addAll(idColumn, nameColumn, timeColumn, totalColumn, statusColumn);
    }


    // product tab
    @FXML
    private TextField searchTxt;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private Button resetBtn;
    @FXML
    private TableView productTable;

    @FXML
    private void onFromDatePickerAction(){}

    @FXML
    private void onToDatePickerAction(){}

    @FXML
    private void onResetBtnClick(){}


    // ticket tab
    @FXML
    private TextField searchTicketTxt;
    @FXML
    private DatePicker ticketFromDatePicker;
    @FXML
    private DatePicker ticketToDatePicker;
    @FXML
    private Button resetTicketBtn;
    @FXML
    private Button detailBtn;
    @FXML
    private TableView<Object> ticketTable;
    @FXML
    private Label numTicketLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private TextField fromTotalTxt;
    @FXML
    private TextField toTotalTxt;
    @FXML
    private void onSearchTicketTxtAction(){
        ticketTable.setItems(searchAllPhieu());
        updateTotalAmount(ticketTable);

    }
    private void updateTotalAmount(TableView<Object> tableView) {
        double totalAmount = 0.0;
        TableColumn<Object, String> totalColumn = getColumnByName(tableView, "Tổng tiền");
        if (totalColumn != null) {
            for (Object item : tableView.getItems()) {
                String cellValue = totalColumn.getCellObservableValue(item).getValue();
                String formattedValue = cellValue.replaceAll(",", "");
                double amount = Double.parseDouble(formattedValue);
                totalAmount += amount;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        // Chuyển đổi totalAmount thành chuỗi định dạng
        String formattedTotalAmount = decimalFormat.format(totalAmount);
        // Gán giá trị đã định dạng cho totalLabel
        totalLabel.setText(formattedTotalAmount);
        int numTickets = tableView.getItems().size();
        numTicketLabel.setText(String.valueOf(numTickets));
    }




    // Phương thức để lấy cột từ tên cột
    @SuppressWarnings("unchecked")
    private TableColumn<Object, String> getColumnByName(TableView<Object> tableView, String columnName) {
        for (TableColumn<Object, ?> col : tableView.getColumns()) {
            if (col.getText().equals(columnName)) {
                return (TableColumn<Object, String>) col;
            }
        }
        return null;
    }

    @FXML
    private void onTicketFromDatePickerAction(){
        if(ticketToDatePicker.getValue() == null) return;
        ticketTable.setItems(searchAllPhieu());
        updateTotalAmount(ticketTable);
    }

    @FXML
    private void onTicketToDatePickerAction(){
        if(ticketFromDatePicker.getValue() == null) return;
        ticketTable.setItems(searchAllPhieu());
        updateTotalAmount(ticketTable);
    }

    @FXML
    private void onResetTicketBtnClick(){
        fromTotalTxt.clear();
        toTotalTxt.clear();
        searchTicketTxt.clear();
        ticketFromDatePicker.setValue(null);
        ticketToDatePicker.setValue(null);
        ticketTable.setItems(searchAllPhieu());
        updateTotalAmount(ticketTable);
    }

    @FXML
    private void onDetailBtnClick(){
        Object obj = ticketTable.getSelectionModel().getSelectedItem();
        if(obj == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Vui lòng chọn phiếu để xem chi tiết");
            alert1.showAndWait();
            return;
        }
        if(obj instanceof PhieuXuat) detailPhieuXuat((PhieuXuat)obj);
        //pn
    }
    private void detailPhieuXuat(PhieuXuat phieuXuat){
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
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText(null);
            alert1.setContentText("Có lỗi xảy ra khi xem chi tiết phiếu xuất");
            alert1.showAndWait();
            return;
        }
    }
    private void detailPhieuNhap(PhieuNhap phieuNhap){

    }
    @FXML
    private void onTicketTabChange(){

        ticketTable.setItems(searchAllPhieu());
        double number = thongKeDAO.getTotal();
        String formattedNumber = decimalFormat.format(number);
        double allIncome = thongKeDAO.getIncome();
        String formattedAllIncome = decimalFormat.format(allIncome);
        totalLabel.setText(formattedNumber);
        incomeLabel.setText(formattedAllIncome);
        numTicketLabel.setText(String.valueOf(thongKeDAO.count()));
    }
    @FXML
    private void onFromTotalTxtAction(){
        try{
            if(toTotalTxt.getText().equals("")) return;
            double parse = Double.parseDouble(fromTotalTxt.getText());
            ticketTable.setItems(searchAllPhieu());
            updateTotalAmount(ticketTable);
        } catch (Exception e){
            e.printStackTrace();
            String message = e instanceof NumberFormatException?"Vui lòng nhập số tiền thích hợp":"Có lỗi xảy ra khi lọc";
            fromTotalTxt.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi ");
            alert1.setHeaderText(null);
            alert1.setContentText(message);
            alert1.showAndWait();
        }
    }
    @FXML
    private void onToTotalTxtAction(){
        try{
            if(fromTotalTxt.getText().equals("")) return;
            double parse = Double.parseDouble(toTotalTxt.getText());
            ticketTable.setItems(searchAllPhieu());
            updateTotalAmount(ticketTable);
        } catch (Exception e){
            e.printStackTrace();
            String message = e instanceof NumberFormatException?"Vui lòng nhập số tiền thích hợp":"Có lỗi xảy ra khi lọc";
            toTotalTxt.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi ");
            alert1.setHeaderText(null);
            alert1.setContentText(message);
            alert1.showAndWait();
        }
    }


    private ObservableList<Object> searchAllPhieu(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        List<Object> objectList;
        String keyword = searchTicketTxt.getText();
        System.out.println("key"+keyword);
        boolean time = ticketFromDatePicker.getValue() != null && ticketToDatePicker.getValue() != null;
        System.out.println("time"+time);
        boolean total = !fromTotalTxt.getText().equals("") && !toTotalTxt.getText().equals("");
        System.out.println("time"+total);
        if(time && total){
            Timestamp timestampFrom = Timestamp.valueOf(ticketFromDatePicker.getValue().atStartOfDay());
            Timestamp timestampTo = Timestamp.valueOf(ticketToDatePicker.getValue().atStartOfDay());
            double totalFrom = Double.parseDouble(fromTotalTxt.getText());
            double totalTo = Double.parseDouble(toTotalTxt.getText());
            objectList = thongKeDAO.searchAllPhieu(keyword,timestampFrom,timestampTo,totalFrom,totalTo);
        } else if(time){
            Timestamp timestampFrom = Timestamp.valueOf(ticketFromDatePicker.getValue().atStartOfDay());
            Timestamp timestampTo = Timestamp.valueOf(ticketToDatePicker.getValue().atStartOfDay());
            objectList = thongKeDAO.searchAllPhieu(keyword,timestampFrom,timestampTo);
        } else if(total){
            double totalFrom = Double.parseDouble(fromTotalTxt.getText());
            double totalTo = Double.parseDouble(toTotalTxt.getText());
            objectList = thongKeDAO.searchAllPhieu(keyword,totalFrom,totalTo);
        } else {
            objectList = thongKeDAO.searchAllPhieu(keyword);
        }
        data.addAll(objectList);
        System.out.println("List ne ==========================================");
        for(Object obj:objectList){
            System.out.println(obj.toString());
        }
        return data;

    }

}
