open module org.example.quanlykhohang {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.hibernate.orm.core;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jakarta.persistence;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.materialdesign2;
    requires java.base;
//    requires org.apache.commons.codec;
    requires jbcrypt;
	requires javafx.base;
	requires javafx.graphics;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
//    opens org.example.quanlykhohang to javafx.fxml;
    exports org.example.quanlykhohang;
    exports org.example.quanlykhohang.controller;
//    opens org.example.quanlykhohang.controller to javafx.fxml;
}