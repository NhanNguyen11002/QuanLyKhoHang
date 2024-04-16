package org.example.quanlykhohang.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ThongKeController {
    @FXML
    private Label numProductLabel;
    @FXML
    private Label numProviderLabel;
    @FXML
    private Label numCustomerLabel;
    @FXML
    private Label numAccountLabel;


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
    private TableView ticketTable;
    @FXML
    private Label numTicketLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private void onTicketFromDatePickerAction(){}

    @FXML
    private void onTicketToDatePickerAction(){}

    @FXML
    private void onResetTicketBtnClick(){}

    @FXML
    private void onDetailBtnClick(){}




}
