package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import org.example.quanlykhohang.Main;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminSidebarController {
    @FXML
    private Pane productPane;
    @FXML
    private Pane providerPane;
    @FXML
    private Pane customerPane;
    @FXML
    private Pane exportFormPane;
    @FXML
    private Pane importTicketPane;
    @FXML
    private Pane exportTicketPane;
    @FXML
    private Pane storagePane;
    @FXML
    private Pane accountPane;
    @FXML
    private Pane statisticPane;
    @FXML
    private Pane logoutPane;
    @FXML
    private Pane informationPane;
    @FXML
    private Pane mainPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private void onProductPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/san-pham-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(productPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onCustomerPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/khach-hang-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(customerPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onProviderPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/nha-cung-cap-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(providerPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    private void onExportFormPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/don-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(exportFormPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onImportTicketPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-nhap-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(importTicketPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onExportTicketPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/phieu-xuat-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(storagePane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onStoragePaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/ton-kho-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(storagePane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onAccountPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/tai-khoan-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(accountPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onStatisticPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/thong-ke-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(statisticPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onLogoutPaneClick(){}
    @FXML
    private void onInformationPaneClick(){
        try {
        // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popup-sua-thong-tin-ca-nhan-view.fxml"));
            // Load the root pane
            Pane item = fxmlLoader.load();

            // Create a new scene with the loaded pane
            Scene scene = new Scene(item);

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.show(); // Show the stage
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/san-pham-view.fxml"));
            Pane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(productPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void applyStyle(Pane pane){
        productPane.getStyleClass().clear();
        productPane.getStyleClass().add("pane-btn");
        providerPane.getStyleClass().clear();
        providerPane.getStyleClass().add("pane-btn");
        customerPane.getStyleClass().clear();
        customerPane.getStyleClass().add("pane-btn");
        exportFormPane.getStyleClass().clear();
        exportFormPane.getStyleClass().add("pane-btn");
        importTicketPane.getStyleClass().clear();
        importTicketPane.getStyleClass().add("pane-btn");
        exportTicketPane.getStyleClass().clear();
        exportTicketPane.getStyleClass().add("pane-btn");
        storagePane.getStyleClass().clear();
        storagePane.getStyleClass().add("pane-btn");
        accountPane.getStyleClass().clear();
        accountPane.getStyleClass().add("pane-btn");
        statisticPane.getStyleClass().clear();
        statisticPane.getStyleClass().add("pane-btn");
        logoutPane.getStyleClass().clear();
        logoutPane.getStyleClass().add("logout-btn");
        informationPane.getStyleClass().clear();
        informationPane.getStyleClass().add("pane-btn");
        pane.getStyleClass().add("bg-teal");
    }

}
