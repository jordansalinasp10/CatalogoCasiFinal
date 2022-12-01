package grupo.proyectocatalogodevideojuegos;


import grupo.modelo.LCDE;
import grupo.modelo.Videojuego;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import grupo.proyectocatalogodevideojuegos.PaginaInicialController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class wishListController implements  Initializable {
    @FXML
    private Button back;
    @FXML
    private HBox hB1;
    @FXML
    private HBox hB2;
    @FXML
    private HBox hB3;

    private LCDE<Videojuego> lista;

    private int valorDerecha;
    private int valorIzquierda=0;

    @Override
    public void initialize(URL url,ResourceBundle rb){
        back.setOnMouseClicked(event -> {
            try {
                App.setRoot("paginaInicial");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });




    }

}
