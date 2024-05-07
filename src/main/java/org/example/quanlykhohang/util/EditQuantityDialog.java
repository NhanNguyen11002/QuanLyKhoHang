package org.example.quanlykhohang.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditQuantityDialog {
    public static Integer quantity;

    public static Integer display(Integer soLuongTon, Integer soLuongBanDau){
        quantity = soLuongBanDau;
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Sửa số lượng");

        // Tạo một TextField và một nút "Submit" trong popup
        TextField textField = new TextField();
        textField.setText(soLuongBanDau.toString());
        Label label = new Label();
        label.setText("Nhập số lượng mong muốn");
        Button submitButton = new Button("Lưu");

        // Xử lý sự kiện khi nhấn nút "Submit"
        submitButton.setOnAction(submitEvent -> {
            try{
                Integer newQuantity = Integer.parseInt(textField.getText());
                if(newQuantity <= 0 ){
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Lỗi ");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Số lượng phải là số nguyên dương");
                    alert1.showAndWait();
                    return;
                }
                if(newQuantity>soLuongTon){
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Lỗi ");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Số lượng xuất không được lớn hơn tồn kho");
                    alert1.showAndWait();
                    return;
                }
                quantity = newQuantity;
                popupStage.close();
            } catch (Exception e){
                e.printStackTrace();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi ");
                alert1.setHeaderText(null);
                alert1.setContentText("Số lượng xuất phải là số nguyên");
                alert1.showAndWait();
            }
        });

        // Tạo layout cho popup
        VBox popupLayout = new VBox(10);
        popupLayout.setSpacing(5);
        popupLayout.getChildren().addAll(label,textField, submitButton);
        popupLayout.setPadding(new Insets(30));
        popupLayout.setAlignment(Pos.CENTER);

        // Tạo scene cho popup và hiển thị nó trên popupStage
        Scene popupScene = new Scene(popupLayout, 300, 100);
        popupStage.setResizable(false);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
        return quantity;
    }
}
