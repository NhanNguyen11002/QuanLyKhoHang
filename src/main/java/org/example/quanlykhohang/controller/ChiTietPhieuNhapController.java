/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.example.quanlykhohang.dao.ChiTietPhieuNhapDAO;
import org.example.quanlykhohang.entity.ChiTietPhieuNhap;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.NhaCungCap;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.PhieuNhap;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ChiTietPhieuNhapController implements Initializable {
    @FXML
    private Label  maPhieuLbl;
    @FXML
    private Label  nhaCungCapLbl;
    @FXML
    private Label  thoiGianTaoLbl;
    @FXML
    private Label  nguoiTaoLbl;
    @FXML
    private Label  tongTienLbl;
    @FXML
    private Button  xuatPDFBtn;
    @FXML
	private AnchorPane anchorPane;
    @FXML
    private TableView<ChiTietPhieuNhap>  ctPhieuNhapTbl;
    @FXML
	private TableColumn<ChiTietPhieuNhap, String> idPhoneColumn;
	@FXML
	private TableColumn<ChiTietPhieuNhap, String> namePhoneColumn;
	@FXML
	private TableColumn<ChiTietPhieuNhap, Double> inputPriceColumn;
	@FXML
	private TableColumn<ChiTietPhieuNhap, Integer> soLuongColumn;

    private PhieuNhapController phieuNhapController;

    private final DecimalFormat format = new DecimalFormat("#,###.0");

    public void setPhieuNhapController(PhieuNhapController phieuNhapController) {
		try {
			ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	sdf.setTimeZone(TimeZone.getTimeZone("GMT+7")); // UTC+7
			this.phieuNhapController = phieuNhapController;
			TableView<PhieuNhap> productTable = this.phieuNhapController.getPNTable();
			PhieuNhap selectedPN = productTable.getSelectionModel().getSelectedItem();
			if (selectedPN != null) {
				String maPhieu = selectedPN.getMaPhieu();
				NhanVien nguoiTao = selectedPN.getNguoiTao();
				NhaCungCap ncc = selectedPN.getNhaCungCap();
				Timestamp tgTao = selectedPN.getThoiGianTao();
				Double tongTien = selectedPN.getTongTien();
				maPhieuLbl.setText(maPhieu);
				nhaCungCapLbl.setText(ncc.getTenNhaCungCap());
				thoiGianTaoLbl.setText(sdf.format(tgTao));
				nguoiTaoLbl.setText(nguoiTao.getHo() + " " + nguoiTao.getTen());
				tongTienLbl.setText(format.format(selectedPN.getTongTien())+" đ");
				// table chi tiet phieu nhap
				idPhoneColumn.setCellValueFactory(cellData -> {
				    ChiTietPhieuNhap chiTiet = cellData.getValue();
				    DienThoai dienThoai = chiTiet.getDienThoai();
				    if (dienThoai != null) {
				        return new SimpleObjectProperty<>(dienThoai.getMaDT());
				    } else {
				        return new SimpleObjectProperty<>("");
				    }
				});
				idPhoneColumn.setStyle("-fx-alignment: CENTER;");
				namePhoneColumn.setCellValueFactory(cellData -> {
				    ChiTietPhieuNhap chiTiet = cellData.getValue();
				    DienThoai dienThoai = chiTiet.getDienThoai();
				    if (dienThoai != null) {
				        return new SimpleObjectProperty<>(dienThoai.getTenDT());
				    } else {
				        return new SimpleObjectProperty<>("");
				    }
				});
				namePhoneColumn.setStyle("-fx-alignment: CENTER;");
				inputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
				inputPriceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
				inputPriceColumn.setCellFactory(tc -> new TableCell<ChiTietPhieuNhap, Double>() {
					@Override
					protected void updateItem(Double item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
						} else {
							setText(format.format(item));
						}
					}
				});
				soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
				soLuongColumn.setStyle("-fx-alignment: CENTER;");
				List<ChiTietPhieuNhap> ctpnList = ctpnDAO.findByMaPhieu(maPhieu);
				ctPhieuNhapTbl.getItems().addAll(ctpnList);
				ctPhieuNhapTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
				ctPhieuNhapTbl.refresh();
			} else {
				throw new IOException("Không có phiếu nhập nào được chọn");
			}
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.setOnCloseRequest(event -> {
	            stage.close();
	        });
		}

	}

    @FXML
    private void onXuatPDFBtnClick(){
    	String maPhieu = maPhieuLbl.getText();
    	String ncc = nhaCungCapLbl.getText();
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
                contentStream.showText("CHI TIẾT PHIẾU NHẬP");
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
                contentStream.showText("Nhà cung cấp: " + ncc);
                contentStream.endText();

                // table chi tiết phiếu nhập
                ObservableList<ChiTietPhieuNhap> dataList = ctPhieuNhapTbl.getItems();
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
                String header3 = "Giá nhập";
                System.out.println(caculateStartXCenter(normalFont, header3, 14, startX+250, startX+375));
                contentStream.setFont(normalFont, 14);
                contentStream.newLineAtOffset(caculateStartXCenter(normalFont, header3, 14, startX+250, startX+375), textY);
                contentStream.showText(header3);
                contentStream.endText();
                contentStream.beginText();
                String header4 = "Số lượng nhập";
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
            document.save("C:\\Documents\\"+pdfName+".pdf");
        } catch (IOException e) {
			e.printStackTrace();
		}
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
