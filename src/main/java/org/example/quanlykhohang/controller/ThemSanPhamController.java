/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.example.quanlykhohang.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ThemSanPhamController implements Initializable {
    @FXML
    private TextField  idPhoneTxt;
    @FXML
    private  TextField namePhoneTxt;
    @FXML
    private  TextField inputPriceTxt;
    @FXML
    private  TextField outputPriceTxt;
    @FXML
    private  ChoiceBox supplierChoiceBox;
    @FXML
    private  Button saveButton;
    @FXML
    private  Button cancelButton;
    @FXML
    private void onSaveButtonClick(){}
    @FXML
    private void onCancelButtonClick(){}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
