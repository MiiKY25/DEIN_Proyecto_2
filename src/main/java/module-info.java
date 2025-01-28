module org.mikel.dein_proyecto_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires java.desktop;

    opens org.mikel.dein_proyecto_2.controladores to javafx.fxml;
    opens org.mikel.dein_proyecto_2.modelos to javafx.base;
    opens org.mikel.dein_proyecto_2 to javafx.fxml;
    exports org.mikel.dein_proyecto_2;
}