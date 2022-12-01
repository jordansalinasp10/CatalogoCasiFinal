module grupo.proyectocatalogodevideojuegos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens grupo.proyectocatalogodevideojuegos to javafx.fxml;
    exports grupo.proyectocatalogodevideojuegos;
}
