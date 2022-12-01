package grupo.proyectocatalogodevideojuegos;

import grupo.modelo.LCDE;
import grupo.modelo.LectorCsvCatalogo;
import grupo.modelo.Videojuego;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PaginaInicialController implements Initializable {

    @FXML
    private TilePane panel;
    public static Videojuego ultimoVideojuegoElegido;
    @FXML
    private TextField buscarTitulo;
    /*@FXML
    private Button btnBuscar;*/
    LCDE<Videojuego> videojuegos;
    LCDE<Videojuego> listaInicialVideojuegos;
    @FXML
    private TextField txtAño;
    @FXML
    private Button mostrarTodo;
    @FXML
    private VBox scenapr;
    @FXML
    private ImageView imgLupa;
    @FXML
    private Button bwish;


    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.videojuegos = LectorCsvCatalogo.cargarListaVideojuegos();
        this.listaInicialVideojuegos = LectorCsvCatalogo.cargarListaVideojuegos();
        for(Videojuego v:videojuegos){
            System.out.println(v.getTitulo() + "    "+ v.getFechaDeLanzamiento());
            v.imprimirReviews();
            
        }
        ordenarPorTitulo();
        mostrarVideojuegos(videojuegos);
        setActions();
    }    
    
    private void mostrarVideojuegos(LCDE<Videojuego> videojuegos){
        for (Videojuego v : videojuegos) {
            VBox bookView = crearElementosVideojuego(v);
            panel.getChildren().addAll(bookView);
        }
    }
    
    private VBox crearElementosVideojuego(Videojuego videojuego){
        VBox vbox = new VBox();
        try{

            Image image = new Image(new FileInputStream("src\\main\\resources\\grupo\\ListaVideojuegos\\imagenes\\Portada\\" + videojuego.getPortada()), 1280, 720, true, false);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);

            vbox.getChildren().add(imageView);
            Label titleLabel = new Label(videojuego.getTitulo());
            titleLabel.setPadding(new Insets(8, 0, 0, 0));
            titleLabel.setTextFill(Color.web("#F5F5F5"));
            titleLabel.setFont(Font.font("SansSerif", 13));
            titleLabel.setMaxWidth(150);
            vbox.getChildren().add(titleLabel);

            Button botonWish= new Button("Agregar a WishList");
            Label fecha = new Label(videojuego.getFechaDeLanzamiento());
            fecha.setPadding(new Insets(8, 0, 0, 0));
            fecha.setTextFill(Color.web("#F5F5F5"));
            fecha.setFont(Font.font("SansSerif", 13));
            fecha.setMaxWidth(150);

            vbox.getChildren().add(fecha);
            vbox.getChildren().add(botonWish);

            
            imageView.setOnMouseClicked(event -> cambioAInfoVideojuego(videojuego));
            botonWish.setOnAction(eh->agregarAwishList());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return vbox;
    }
    
    private void setActions() {
        imgLupa.setOnMouseClicked(eh -> {
            scenapr.requestFocus();
            ordenarPorBusqueda();
        });
        scenapr.setOnKeyPressed(eh -> {
            if (eh.getCode().equals(KeyCode.ENTER)) {
                scenapr.requestFocus();
                ordenarPorBusqueda();
            }
        });

        mostrarTodo.setOnAction(eh-> mostrarTodo());
        bwish.setOnAction(eh->cambioAwishList());







    }
    
    
    private void ordenarPorTitulo(){
        LCDE<Videojuego> tmp = new LCDE<>();
        PriorityQueue<Videojuego> colaVideojuegos = new PriorityQueue<>((v1,v2)->{
            return v1.getTitulo().compareTo(v2.getTitulo());
        });
        for(Videojuego v : this.videojuegos){
            colaVideojuegos.offer(v);
        }
        while(!colaVideojuegos.isEmpty()){
            tmp.addLast(colaVideojuegos.remove());
        }
        panel.getChildren().clear();
        this.videojuegos = tmp;
        mostrarVideojuegos(this.videojuegos);
    }
    
    private void ordenarPorfecha(){
        LCDE<Videojuego> tmp = new LCDE<>();
        PriorityQueue<Videojuego> colaVideojuego = new PriorityQueue<>((v1,v2)->{
            return v1.getFechaDeLanzamiento().compareTo(v2.getFechaDeLanzamiento());
        });
        for (Videojuego v : this.videojuegos) {
            colaVideojuego.offer(v);
        }
        while (!colaVideojuego.isEmpty()) {
            tmp.addLast(colaVideojuego.remove());
        }
        panel.getChildren().clear();
        this.videojuegos = tmp;
        mostrarVideojuegos(this.videojuegos);
    }

    private void ordenarPorBusqueda() {

        String año = txtAño.getText();
        System.out.println(año);

        String palabra = buscarTitulo.getText();
        LCDE<Videojuego> tmp = new LCDE<>();
        Set<Videojuego> colaVideojuegos = new TreeSet<>((v1, v2) -> {
            return v1.getTitulo().compareTo(v2.getTitulo());
        });
        for (Videojuego v : this.listaInicialVideojuegos) {

            String[] fecha = v.getFechaDeLanzamiento().split("-");

            String tituloNormalizado = v.getTitulo().toLowerCase();
            String palabraNormalizada = palabra.toLowerCase();
            String[] tituloSeparado = tituloNormalizado.split(" ");
            for (String tituloPartes : tituloSeparado) {
                if (año != "") {
                    if ((tituloPartes.startsWith(palabraNormalizada) || tituloNormalizado.startsWith(palabraNormalizada)) && año.equals(fecha[0])) {
                        colaVideojuegos.add(v);
                    }
                } else {
                    if ((tituloPartes.startsWith(palabraNormalizada) || tituloNormalizado.startsWith(palabraNormalizada))) {
                        colaVideojuegos.add(v);
                    }
                }
            }
        }
        Iterator<Videojuego> i = colaVideojuegos.iterator();

        while (i.hasNext()) {
            tmp.addLast(i.next());
        }
        buscarTitulo.clear();
        txtAño.clear();
        panel.getChildren().clear();
        this.videojuegos = tmp;
        mostrarVideojuegos(this.videojuegos);
    }

    private void mostrarTodo() {
        LCDE<Videojuego> tmp = new LCDE<>();
        for (Videojuego v : this.listaInicialVideojuegos) {
            tmp.addLast(v);
        }
        this.videojuegos = tmp;
        panel.getChildren().clear();
        ordenarPorTitulo();
        mostrarVideojuegos(this.videojuegos);
    }
    
    private void cambioAInfoVideojuego(Videojuego videojuego) {
        try {
            ultimoVideojuegoElegido = videojuego;
            App.setRoot("infoVideojuego");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   private void cambioAwishList()  {
       try {


           App.setRoot("wishList");

       } catch (IOException ex) {
           ex.printStackTrace();
       }
    }
    public void agregarAwishList(){
        LCDE<Videojuego> listaRetorno=new LCDE<>();
        Videojuego juego=ultimoVideojuegoElegido;
        listaRetorno.addLast(juego);

        System.out.println(juego);


    }
    
}




