package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import org.example.quanlykhohang.Main;

import java.io.IOException;

public class AdminSidebarController {
    @FXML
    private Pane productPane;
    @FXML
    private Pane providerPane;
    @FXML
    private Pane customerPane;
    @FXML
    private Pane importFormPane;
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
    private void onCustomerPaneClick(){}
    @FXML
    private void onProviderPaneClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/nha-cung-cap-view.fxml"));
            AnchorPane item = fxmlLoader.load();
            borderPane.setRight(item);
            applyStyle(providerPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void onImportFormPaneClick(){}
    @FXML
    private void onExportFormPaneClick(){}
    @FXML
    private void onImportTicketPaneClick(){}
    @FXML
    private void onExportTicketPaneClick(){}
    @FXML
    private void onStoragePaneClick(){}
    @FXML
    private void onAccountPaneClick(){}
    @FXML
    private void onStatisticPaneClick(){}
    @FXML
    private void onLogoutPaneClick(){}
    @FXML
    private void onInformationPaneClick(){}
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
        importFormPane.getStyleClass().clear();
        importFormPane.getStyleClass().add("pane-btn");
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
