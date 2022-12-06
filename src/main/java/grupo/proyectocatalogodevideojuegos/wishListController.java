package grupo.proyectocatalogodevideojuegos;


import grupo.modelo.LCDE;
import grupo.modelo.Videojuego;
import grupo.modelo.WishList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import grupo.proyectocatalogodevideojuegos.PaginaInicialController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;


public class wishListController implements  Initializable {
    @FXML
    private Button back;
    @FXML
    private HBox hB1;

    private LCDE<Videojuego> lista;

    private int valorDerecha;
    private int valorIzquierda=0;
    @FXML
    private Button btnAgregar;
    @FXML
    private VBox VBoxLista;
    @FXML
    private VBox contenedorPrincipal;
    private Queue<VBox> cola = new LinkedList<>();
    static LCDE<WishList> listaWishList = new LCDE<>();

    @Override
    public void initialize(URL url,ResourceBundle rb){
        //VBoxLista.getChildren().addAll(cola);
        actions();
    }
     private void asignarNombre(){
         VBox contenedorLista = new VBox();
         HBox hBoxParteDeArriba = new HBox();
         TextField txtNombre = new TextField();
         hBoxParteDeArriba.getChildren().add(txtNombre);
         contenedorLista.getChildren().add(hBoxParteDeArriba);
         VBoxLista.getChildren().add(contenedorLista);
         
         contenedorPrincipal.setOnKeyPressed(eh -> {
            if (eh.getCode().equals(KeyCode.ENTER)) {
                contenedorPrincipal.requestFocus();
                crearApartadoListas(hBoxParteDeArriba,contenedorLista);
            }
        });
     }
     
     private void crearApartadoListas(HBox hBoxParteDeArriba, VBox contenedorLista){
         TextField x = (TextField)hBoxParteDeArriba.getChildren().get(0);
         WishList lista =new WishList(x.getText());
         this.listaWishList.addLast(lista);
         for(WishList w:listaWishList){
             System.out.println(w);
         }
         hBoxParteDeArriba.getChildren().clear();
         
         Label LbNombre = new Label(lista.getNombre());
                 
         Button btnAnterior = new Button("<");
         Button btnSiguiente = new Button(">");
         hBoxParteDeArriba.getChildren().addAll(LbNombre,btnAnterior,btnSiguiente);
         
         HBox hBoxParteDeAbajo = new HBox();
         
         contenedorLista.getChildren().add(hBoxParteDeAbajo);
         cola.offer(contenedorLista);
         
     }
    
     private void actions(){
         back.setOnMouseClicked(event -> {
            try {
                App.setRoot("paginaInicial");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        
        btnAgregar.setOnAction(eh->{
            asignarNombre();
        });
     }

}
