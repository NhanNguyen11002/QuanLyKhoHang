package org.example.quanlykhohang.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.example.quanlykhohang.entity.ChiTietDonXuatHang;
import org.example.quanlykhohang.entity.ChiTietPhieuNhap;
import org.example.quanlykhohang.entity.DonXuatHang;

import java.io.IOException;
import java.io.InputStream;
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
    private Label khachHangLbl;
    private final DecimalFormat format = new DecimalFormat("#,###.0");

    @FXML
    private void onXuatPDFBtnClick(){
        String maPhieu = maPhieuLbl.getText();
        String kh = khachHangLbl.getText();
        String tg = thoiGianTaoLbl.getText();
        String nguoiTao = nguoiTaoLbl.getText();
        String tongTien = tongTienLbl.getText();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                InputStream inputNormalStream = getClass().getResourceAsStream("/fonts/Arial.ttf");
                PDType0Font normalFont = PDType0Font.load(document, inputNormalStream);
                InputStream inputBoldStream = getClass().getResourceAsStream("/fonts/Arial_Bold.ttf");
                PDType0Font boldFont = PDType0Font.load(document, inputBoldStream);
                InputStream inputItalicStream = getClass().getResourceAsStream("/fonts/Arial_Italic.ttf");
                PDType0Font italicFont = PDType0Font.load(document, inputItalicStream);

                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();

                contentStream.beginText();
                contentStream.setFont(boldFont, 20);
                contentStream.newLineAtOffset(pageWidth / 3, pageHeight - 50);
                contentStream.showText("CHI TIẾT ĐƠN HÀNG");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(italicFont, 13);
                contentStream.newLineAtOffset(pageWidth*1/2, pageHeight - 80);
                contentStream.showText("Thời gian tạo: " + tg);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(normalFont, 13);
                contentStream.newLineAtOffset(pageWidth/6, pageHeight - 130);
                contentStream.showText("Mã phiếu: " + maPhieu);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(normalFont, 13);
                contentStream.newLineAtOffset(pageWidth/6, pageHeight - 150);
                contentStream.showText("Người tạo: " + nguoiTao);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(normalFont, 13);
                contentStream.newLineAtOffset(pageWidth/6, pageHeight - 170);
                contentStream.showText("Khách hàng: " + kh);
                contentStream.endText();

                // table chi tiết phiếu nhập
                ObservableList<ChiTietDonXuatHang> dataList = ctDonXuatTbl.getItems();
                float tableWidth = 500;
                float tableHeight = (dataList.size()+1)*40;
                float startX = (pageWidth - tableWidth) / 2;
                float startY = pageHeight - 200;
                System.out.println("List size"+dataList.size());
                // đường ngang của bảng
                for (int i = 0; i <= dataList.size()+1; i++) {
                    contentStream.moveTo(startX, startY - i * 40);
                    contentStream.lineTo(startX + tableWidth, startY - i * 40);
                    contentStream.stroke();
                }
                // Vẽ đường kẻ dọc của bảng
                contentStream.moveTo(startX, startY);
                contentStream.lineTo(startX, startY - tableHeight);
                contentStream.stroke();
                contentStream.moveTo(startX+125, startY);
                contentStream.lineTo(startX+125, startY - tableHeight);
                contentStream.stroke();
                contentStream.moveTo(startX+250, startY);
                contentStream.lineTo(startX+250, startY - tableHeight);
                contentStream.stroke();
                contentStream.moveTo(startX+375, startY);
                contentStream.lineTo(startX+375, startY - tableHeight);
                contentStream.stroke();
                contentStream.moveTo(startX+500, startY);
                contentStream.lineTo(startX+500, startY - tableHeight);
                contentStream.stroke();

                // Hiển thị dữ liệu trong bảng
                float textY = startY - 22; // Độ lệch dọc cho dữ liệu

                // Header
                contentStream.beginText();
                contentStream.setFont(normalFont, 14);
                String header1 = "Mã điện thoại";
                System.out.println(caculateStartXCenter(normalFont, header1, 14, startX, startX+125));
                contentStream.newLineAtOffset(caculateStartXCenter(normalFont, header1, 14, startX, startX+125), textY);
                contentStream.showText(header1);
                contentStream.endText();
                contentStream.beginText();
                String header2 = "Tên điện thoại";
                System.out.println(caculateStartXCenter(normalFont, header2, 14, startX+125, startX+250));
                contentStream.setFont(normalFont, 14);
                contentStream.newLineAtOffset(caculateStartXCenter(normalFont, header2, 14, startX+125, startX+250), textY);
                contentStream.showText(header2);
                contentStream.endText();
                contentStream.beginText();
                String header3 = "Giá xuất";
                System.out.println(caculateStartXCenter(normalFont, header3, 14, startX+250, startX+375));
                contentStream.setFont(normalFont, 14);
                contentStream.newLineAtOffset(caculateStartXCenter(normalFont, header3, 14, startX+250, startX+375), textY);
                contentStream.showText(header3);
                contentStream.endText();
                contentStream.beginText();
                String header4 = "Số lượng xuất";
                contentStream.setFont(normalFont, 14);
                System.out.println(caculateStartXCenter(normalFont, header4, 14, startX+375, startX+500));
                contentStream.newLineAtOffset(caculateStartXCenter(normalFont, header4, 14, startX+375, startX+500), textY);
                contentStream.showText(header4);
                contentStream.endText();
                // Content
                for (int i = 0; i < dataList.size(); i++) {
                    contentStream.beginText();
                    String content1 = dataList.get(i).getDienThoai().getMaDT();
                    contentStream.setFont(normalFont, 10);
                    contentStream.newLineAtOffset(caculateStartXCenter(normalFont, content1, 10, startX, startX+125), textY - (i+1)*40);
                    contentStream.showText(content1);
                    contentStream.endText();
                    contentStream.beginText();
                    String content2 = dataList.get(i).getDienThoai().getTenDT();
                    contentStream.setFont(normalFont, 11);
                    contentStream.newLineAtOffset(caculateStartXCenter(normalFont, content2, 11, startX+125, startX+250), textY - (i+1)*40);
                    contentStream.showText(content2);
                    contentStream.endText();
                    contentStream.beginText();
                    String content3 = format.format(dataList.get(i).getDonGia());
                    contentStream.setFont(normalFont, 11);
                    contentStream.newLineAtOffset(caculateStartXCenter(normalFont, content3, 11, startX+250, startX+375), textY - (i+1)*40);
                    contentStream.showText(content3);
                    contentStream.endText();
                    contentStream.beginText();
                    String content4 = dataList.get(i).getSoLuong().toString();
                    contentStream.setFont(normalFont, 11);
                    contentStream.newLineAtOffset(caculateStartXCenter(normalFont, content4, 11, startX+375, startX+500), textY - (i+1)*40);
                    contentStream.showText(content4);
                    contentStream.endText();
                }

                contentStream.beginText();
                contentStream.setFont(boldFont, 16);
                contentStream.newLineAtOffset(pageWidth/6, startY - (dataList.size()+1)*40 -50);
                contentStream.showText("Tổng tiền: " + tongTien);
                contentStream.endText();
            }

            String pdfName = maPhieuLbl.getText();
//            document.save("C:\\Documents\\"+pdfName+".pdf");
            String path  = System.getProperty("user.home")+"/Documents/"+pdfName+".pdf";
            document.save(path);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Thông tin");
            alert1.setHeaderText(null);
            alert1.setContentText("Xuất pdf thành công\nFile ở vị trí: "+path);
            alert1.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi ");
            alert1.setHeaderText(null);
            alert1.setContentText("Đã có lỗi xảy ra khi xuất pdf");
            alert1.showAndWait();
        }
    }
    @FXML
    private TableView ctDonXuatTbl;
    @FXML
    private Label statusLbl;

    private ObservableList<ChiTietDonXuatHang> chiTietDonXuatHangObservableList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        ctDonXuatTbl.getColumns().clear();
        ctDonXuatTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<ChiTietDonXuatHang, String> prodId = new TableColumn<>("Mã điện thoại");
        prodId.setCellValueFactory(cellData  -> {
            String maDT = cellData.getValue().getDienThoai().getMaDT();
            return new SimpleStringProperty(maDT);
        });
        prodId.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietDonXuatHang, String> prodName = new TableColumn<>("Tên điện thoại");
        prodName.setCellValueFactory(cellData  -> {
            String tenDT = cellData.getValue().getDienThoai().getTenDT();
            return new SimpleStringProperty(tenDT);
        });
        prodName.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietDonXuatHang, String> quantity = new TableColumn<>("Số lượng");
        quantity.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        quantity.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietDonXuatHang, String> price = new TableColumn<>("Đơn giá");
        price.setCellValueFactory(cellData  -> {
            Double value  = cellData.getValue().getDonGia();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedValue = decimalFormat.format(value);
            return new SimpleStringProperty(formattedValue);
        });
        price.setStyle("-fx-alignment: CENTER;");

        ctDonXuatTbl.getColumns().addAll(prodId, prodName,quantity,price);

    }
    public void initData(DonXuatHang donXuatHang){
        maPhieuLbl.setText(donXuatHang.getMaDon());
        thoiGianTaoLbl.setText(donXuatHang.getThoiGianTao().toString());
        nguoiTaoLbl.setText(donXuatHang.getNhanVien().getHo()+" "+donXuatHang.getNhanVien().getTen());
        statusLbl.setText(donXuatHang.getTrangThai().equals("done")?"Hoàn thành":"Đang chờ");
        khachHangLbl.setText(donXuatHang.getKhachHang().getTenKhachHang());
        Double totalValue  = donXuatHang.getTongTien();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedValue = decimalFormat.format(totalValue);
        tongTienLbl.setText(formattedValue);
        for(ChiTietDonXuatHang ct:donXuatHang.getChiTietDonXuatHangList()){
            chiTietDonXuatHangObservableList.add(ct);
        }
        ctDonXuatTbl.setItems(chiTietDonXuatHangObservableList);
    }
    public float caculateTextWidth (PDType0Font font, String text, float fontSize) {
        float textWidth = 0;
        try {
            textWidth = font.getStringWidth(text) / 1000 * fontSize;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textWidth;
    }

    public float caculateStartXCenter (PDType0Font font, String text, float fontSize,
                                       float columnStart, float columnEnd) {
        float startX = 0;
        try {
            float textWidth = caculateTextWidth(font, text, fontSize);
            startX = columnStart + ((columnEnd-columnStart) - textWidth) / 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return startX;
    }

}
