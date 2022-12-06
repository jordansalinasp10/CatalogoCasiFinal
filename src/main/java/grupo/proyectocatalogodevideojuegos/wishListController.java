package grupo.proyectocatalogodevideojuegos;
import grupo.modelo.LCDE;
import grupo.modelo.LectorCsvCatalogo;
import grupo.modelo.Videojuego;
import grupo.modelo.WishList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import grupo.proyectocatalogodevideojuegos.PaginaInicialController;
import static grupo.proyectocatalogodevideojuegos.PaginaInicialController.agregarAwishList;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


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
                presentarListaPersonalizadas(LectorCsvCatalogo.cargarListaPersonalizada());
         TextField x = (TextField)hBoxParteDeArriba.getChildren().get(0);
         WishList lista =new WishList(x.getText());
         
         for(WishList w:listaWishList){
             System.out.println(w);
         }
         hBoxParteDeArriba.getChildren().clear();
         
         Label LbNombre = new Label(lista.getNombre());
                 
         Button btnAnterior = new Button("<");
         Button btnSiguiente = new Button(">");
         hBoxParteDeArriba.getChildren().addAll(LbNombre,btnAnterior,btnSiguiente);
         
         HBox hBoxParteDeAbajo = new HBox();
         
         contenedorLista.getChildren().add(presentarJuegosWishList(lista.getLista()));
       
         listaWishList.addLast(lista);
         LectorCsvCatalogo.guardarWishList(lista);
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
    private HBox presentarJuegosWishList(LCDE<Videojuego> listaV){
        HBox HBfinal= new HBox(15);
        for(Videojuego vj: listaV){
            //Prueba
            
            VBox vbox = new VBox();
        try{

            Image image = new Image(new FileInputStream("src\\main\\resources\\grupo\\ListaVideojuegos\\imagenes\\Portada\\" + vj.getPortada()), 1280, 720, true, false);
            ImageView imagePortada = new ImageView(image);
            imagePortada.setFitWidth(200);
            imagePortada.setFitHeight(300);

            vbox.getChildren().add(imagePortada);
            Label titleLabel = new Label(vj.getTitulo());
            titleLabel.setWrapText(true);
            titleLabel.setPadding(new Insets(10, 0, 10, 0));
            titleLabel.setTextFill(Color.web("#ffffff"));
            titleLabel.setFont(Font.font("sans-serif", 13));
            titleLabel.setMaxWidth(150);
            vbox.getChildren().add(titleLabel);
            
            vbox.getChildren().add(vbox);

           
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        }//final for
       return HBfinal;
    }
    private void presentarListaPersonalizadas(WishList wl){
        
       listaWishList.addLast(wl);
    }
}
