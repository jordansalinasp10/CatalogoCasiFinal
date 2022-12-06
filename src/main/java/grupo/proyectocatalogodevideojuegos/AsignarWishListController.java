/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package grupo.proyectocatalogodevideojuegos;

import grupo.modelo.WishList;
import static grupo.proyectocatalogodevideojuegos.PaginaInicialController.videojuegoElegidoWL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class AsignarWishListController implements Initializable {
    
    @FXML
    private ComboBox<WishList> comboOpciones;
    @FXML
    private Button btnAceptar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (WishList w:wishListController.listaWishList){
            comboOpciones.getItems().add(w);
        }
        btnAceptar.setOnAction(eh->{
            WishList opcion = comboOpciones.getValue();
            opcion.addJuego(PaginaInicialController.videojuegoElegidoWL);
            try {
            App.setRoot("paginaInicial");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        });
    }    
    
}
