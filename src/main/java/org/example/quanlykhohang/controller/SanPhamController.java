package org.example.quanlykhohang.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.quanlykhohang.Main;
import org.example.quanlykhohang.dao.DienThoaiDAO;
import org.example.quanlykhohang.dao.NhaCungCapDAO;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.NhaCungCap;

public class SanPhamController {
	DienThoaiDAO phoneDAO = new DienThoaiDAO();
	
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
	private TableView<DienThoai> productTable;
	@FXML
	private TableColumn<DienThoai, String> idPhoneColumn;
	@FXML
	private TableColumn<DienThoai, String> namePhoneColumn;
	@FXML
	private TableColumn<DienThoai, Double> inputPriceColumn;
	@FXML
	private TableColumn<DienThoai, Double> outputPriceColumn;

	private ObservableList<DienThoai> phoneList = FXCollections.observableArrayList();

	public TableView<DienThoai> getProductTable() {
		return this.productTable;
	}

	public void reloadPhoneList() {
		phoneList.clear();
		phoneList.addAll(phoneDAO.findAll());
		productTable.getItems().clear();
		productTable.getItems().addAll(phoneList);
		productTable.refresh();
	}

	@FXML
	private void initialize() {
		idPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("maDT"));
		idPhoneColumn.setStyle("-fx-alignment: CENTER;");
		namePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("tenDT"));
		namePhoneColumn.setStyle("-fx-alignment: CENTER;");
		inputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
		inputPriceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		inputPriceColumn.setCellFactory(tc -> new TableCell<DienThoai, Double>() {
			private final DecimalFormat format = new DecimalFormat("#,###.0");
			
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
		outputPriceColumn.setCellValueFactory(new PropertyValueFactory<>("giaXuat"));
		outputPriceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		outputPriceColumn.setCellFactory(tc -> new TableCell<DienThoai, Double>() {
			private final DecimalFormat format = new DecimalFormat("#,###.0");
			
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
		productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
		phoneList.clear();
		phoneList.addAll(phoneDAO.findAll());
		productTable.getItems().addAll(phoneList);
		productTable.refresh();
	}

	@FXML
	private void onAddBtnClick() {
		try {
			// Load the FXML file
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-them-san-pham-view.fxml"));
			// Load the root pane
			Pane item = fxmlLoader.load();
			ThemSanPhamController controller = fxmlLoader.getController();
			controller.setSanPhamController(this);
			// Create a new scene with the loaded pane
			Scene scene = new Scene(item);

			// Create a new stage and set the scene
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); // Show the stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onEditBtnClick() {
		try {
			// Load the FXML file
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-san-pham-view.fxml"));
			// Load the root pane
			Pane item = fxmlLoader.load();
			SuaSanPhamController controller = fxmlLoader.getController();
			controller.setSanPhamController(this);
			// Create a new scene with the loaded pane
			Scene scene = new Scene(item);

			// Create a new stage and set the scene
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); // Show the stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onDeleteBtnClick() {
		try {
			DienThoai selectedPhone = productTable.getSelectionModel().getSelectedItem();
			if (selectedPhone != null) {
				String idPhone = selectedPhone.getMaDT();
				DienThoaiDAO phoneDAO = new DienThoaiDAO();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Xác nhận xóa");
				alert.setHeaderText(null);
				alert.setContentText("Bạn có chắc chắn muốn xóa sản phẩm này?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					try {
						phoneDAO.delete(idPhone);
						reloadPhoneList();
						showSuccess("Xóa sản phẩm thành công");
					} catch (Exception e) {
						e.printStackTrace();
						showError("Không thể xóa do có bản ghi liên kết");
					}
				}
			} else {
				throw new IOException("Không có sản phẩm nào được chọn");
			}
		} catch (IOException e) {
			showError("Lỗi");
		}
	}

	@FXML
	private void onDetailBtnClick() {
		try {
			// Load the FXML file
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-chi-tiet-san-pham-view.fxml"));
			// Load the root pane
			Pane item = fxmlLoader.load();
			ChiTietSanPhamController controller = fxmlLoader.getController();
			controller.setSanPhamController(this);
			// Create a new scene with the loaded pane
			Scene scene = new Scene(item);

			// Create a new stage and set the scene
			Stage stage = new Stage();
			stage.setScene(scene);
//            stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); // Show the stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleSearchTextFieldKeyPress(ActionEvent event) {
		filterTable(searchTxt.getText());
	}

	private void filterTable(String keyword) {
		ObservableList<DienThoai> filteredList = FXCollections.observableArrayList(phoneDAO.findByKeyword(keyword));
		productTable.getItems().clear();
		productTable.getItems().addAll(filteredList);
		productTable.refresh();
	}

	@FXML
	private void onImportExcelBtnClick() {
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
                        String maDT = row.getCell(0).getStringCellValue();
                        String tenDT = row.getCell(1).getStringCellValue();
                        Double giaNhap = Double.valueOf(row.getCell(2).getNumericCellValue());
                        Double giaXuat = Double.valueOf(row.getCell(3).getNumericCellValue());
                        DienThoai dt = new DienThoai(maDT,tenDT,giaNhap,giaXuat, 0);
                        if(!phoneDAO.existsById(maDT)) {
                           phoneDAO.create(dt);
                        }

                    }
                    reloadPhoneList();
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
	private void onExportExcelBtnClick() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chọn ví trí lưu file excel");
        String userHome = System.getProperty("user.home");
        directoryChooser.setInitialDirectory(new File(userHome));  //cái này có khi phải sửa lại trên window
        Stage stage = new Stage();
        File selectedFile = directoryChooser.showDialog(stage);
        if(selectedFile!=null) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Product");
                XSSFRow header = sheet.createRow(0);
                DataFormatter dataFormatter = new DataFormatter();
                for (int i = 0; i < productTable.getColumns().size(); i++) {
                    TableColumn column = productTable.getColumns().get(i);
                    XSSFCell cell = header.createCell(i);
                    cell.setCellValue(column.getText());
                }
                for (int j = 0; j < productTable.getItems().size(); j++) {
                    XSSFRow row = sheet.createRow(j + 1);
                    ObservableList<TableColumn<DienThoai, ?>> columns = productTable.getColumns();
                    for (int k = 0; k < productTable.getColumns().size(); k++) {
                        XSSFCell cell = row.createCell(k);
                        String cellValue = "";
                        TableColumn column = columns.get(k);
                        if (column.getCellData(j) != null) {
                            Object value = column.getCellData(j);
                            if (value instanceof Double) {
                                cell.setCellValue((Double)value);
                            } else {
                                cellValue = value.toString();
                                cell.setCellValue(cellValue);
                            }
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(selectedFile.getAbsolutePath()+"/Product.xlsx");
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
	private void onResetBtnClick() {
		reloadPhoneList();
	}

	@FXML
	private void onFilterCbboxAction() {
	}
	private void showError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Lỗi");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	private void showSuccess(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Lỗi");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
