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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ChiTietNhanVienController implements Initializable {
    @FXML
    private TextField  idTxt;
    @FXML
    private TextField  lastNameTxt;
    @FXML
    private  TextField firstNameTxt;
    @FXML
    private  TextField phoneTxt;
    @FXML
    private  TextField addressTxt;
    @FXML
    private  TextField emailTxt;
    @FXML
    private  DatePicker startDatePicker;
    @FXML
    private  DatePicker endDatePicker;
    @FXML
    private  DatePicker birthdayPicker;
    @FXML
    private  ChoiceBox genderChoiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
