package org.example.quanlykhohang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.quanlykhohang.entity.SeedDataInitializer;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SeedDataInitializer dataInitializer = new SeedDataInitializer();
        dataInitializer.initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/dang-nhap-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Quản lý kho hàng");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
