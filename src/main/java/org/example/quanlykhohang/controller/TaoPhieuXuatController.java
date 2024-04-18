package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.quanlykhohang.Main;

import java.io.IOException;

public class TaoPhieuXuatController {
    @FXML
    private TextField searchTxt;
    @FXML
    private Button resetBtn;
    @FXML
    private Button backBtn;
    @FXML
    private TableView exportFormTable;
    @FXML
    private TextField ticketIdTxt;
    @FXML
    private TextField creatorTxt;
    @FXML
    private Button exportBtn;
    @FXML
    private TableView exportFormDetailTable;

    @FXML
    private void onResetBtnClick(){}

    @FXML
    private void onBackBtnClick(){
        try {
            Stage currentStage = (Stage) backBtn.getScene().getWindow();
            BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onExportBtnClick(){}


}
