package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;

import java.io.IOException;

public class TaoPhieuNhapController {
    @FXML
    private Button addBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Button importExcelBtn;
    @FXML
    private Button editQuantityBtn;
    @FXML
    private Button deleteProductBtn;
    @FXML
    private TextField searchTxt;
    @FXML
    private TextField quantityTxt;
    @FXML
    private TextField formIdTxt;
    @FXML
    private TextField creatorTxt;
    @FXML
    private ComboBox<String> providerCbbox;
    @FXML
    private TableView productTable;
    @FXML
    private TableView importFormTable;
    @FXML
    private Button importBtn;
    @FXML
    private Label totalMoneyLabel;
    @FXML
    private Button backBtn;
    @FXML
    private void onResetBtnClick(){}
    @FXML
    private void onAddBtnClick(){}
    @FXML
    private void onProviderCbboxAction(){}
    @FXML
    private void onImportExcelBtnClick(){}
    @FXML
    private void onEditQuantityBtnClick(){}
    @FXML
    private void onDeleteProductBtnClick(){}
    @FXML
    private void onImportBtnClick(){}
    @FXML
    private void onBackBtnClick(){
        try {
            Stage currentStage = (Stage) backBtn.getScene().getWindow();
            BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-nhap-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
