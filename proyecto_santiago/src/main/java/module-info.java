module org.example.proyecto_santiago {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires java.desktop;

    opens org.example.proyecto_santiago to javafx.fxml;

    exports org.example.proyecto_santiago;
}