/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package grupo.proyectocatalogodevideojuegos;

import grupo.modelo.LCDE;
import grupo.modelo.Videojuego;
import static grupo.proyectocatalogodevideojuegos.PaginaInicialController.ultimoVideojuegoElegido;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import grupo.modelo.*;
import static grupo.proyectocatalogodevideojuegos.PaginaInicialController.agregarAwishList;
import java.util.PriorityQueue;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;


public class InfoVideojuegoController implements Initializable {

    @FXML
    private Label titulo;
    @FXML
    private ScrollPane capturas;
    @FXML
    private Label genero;
    @FXML
    private Label descripcion;
    Videojuego videojuegoe = PaginaInicialController.ultimoVideojuegoElegido;
    @FXML
    private ImageView portada;
    @FXML
    private Button atras;
    @FXML
    private TilePane panelCapturas;
    @FXML
    private VBox vboxReview;
    @FXML
    private VBox vBoxInformacionReview;
    @FXML
    private ScrollPane scroll;


    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            titulo.setText(videojuegoe.getTitulo());
            descripcion.setText(LectorCsvCatalogo.toUTF8(videojuegoe.getDescripcion()));
            descripcion.setWrapText​(true);
            descripcion.setTextFill(Color.web("rgb(168, 167, 167)"));
            genero.setText(videojuegoe.getGeneros());
            portada.setImage(new Image(new FileInputStream("src\\main\\resources\\grupo\\ListaVideojuegos\\imagenes\\Portada\\" + videojuegoe.getPortada()), 1280, 720, true, false));
            portada.setFitWidth(200);
            portada.setFitHeight(300);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Label tituloReview=new Label("Reseñas: ");
        
        
        
        
        Label prome=new Label("Promedio de valoraciones: "+promedioValoracion()+"/100");
        prome.setPadding(new Insets(0, 0, 10, 0));
        prome.setFont(Font.font("sans-serif", 25));
        prome.setTextFill(Color.web("rgb(255, 255, 255)"));
        
        
        vBoxInformacionReview.getChildren().addAll(prome,infoReview());
        vBoxInformacionReview.setAlignment(Pos.CENTER);
        vBoxInformacionReview.setPadding(new Insets(0, 0, 10, 0));
        mostrarVideojuegos(videojuegoe.getCapturasDePantalla());
        
        Reviews(videojuegoe.getReviews());

        
        atras.setOnMouseClicked(event -> {
                try {
                    App.setRoot("paginaInicial");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        
    }
       private void Reviews(LCDE<Review> rese){
        for(Review r: rese){
            vboxReview.getChildren().addAll(ReviewIndividual(r));
        }
        
}
       
       
        private VBox ReviewIndividual(Review r){
        VBox VboxR=new VBox();
        
        HBox nombre_fecha=new HBox(15);
        
        Label nombreUsuario=new Label();
        nombreUsuario.setText("Usuario: "+r.getUsuario());
        nombreUsuario.setTextFill(Color.web("rgb(255, 255, 255)"));
        nombreUsuario.setFont(Font.font("SansSerif", 14));
        nombreUsuario.setMaxWidth(150);
        Label fechaR=new Label();
        fechaR.setText("Usuario: "+r.getUsuario());
        fechaR.setTextFill(Color.web("rgb(255, 255, 255)"));
        fechaR.setFont(Font.font("SansSerif", 14));
        //fechaR.setMaxWidth(150);
        fechaR.setText("Publicado el: "+r.getFecha()); 
        
        nombre_fecha.getChildren().addAll(nombreUsuario,fechaR);
        nombre_fecha.getPrefWidth();
        nombre_fecha.setPadding(new Insets(0, 0, 6, 0));
        
        Label valoracion=new Label();
        valoracion.setText("Valoracion: "+r.getValoracion()+"/100");
        valoracion.setPadding(new Insets(0, 0, 6, 0));
        valoracion.setFont(Font.font("SansSerif", 14));
        valoracion.setTextFill(Color.web("rgb(168, 167, 167)"));
        
        Label comentario=new Label();
        comentario.setText(r.getComentario());
        //comentario.setFont(Font.font("SansSerif", 14));
        comentario.setTextFill(Color.web("rgb(168, 167, 167)"));
        comentario.setFont(Font.font("SansSerif", FontPosture.ITALIC, 14));
        
        VboxR.getChildren().addAll(nombre_fecha,valoracion,comentario);
        VboxR.setPadding(new Insets(0, 0, 30, 0));
        return VboxR;
              
        
        
        
    }
    private void mostrarVideojuegos(LCDE<String> caps){
        for (String c : caps) {
            VBox boxCaptura = crearElementosVideojuego(c);
            panelCapturas.getChildren().addAll(boxCaptura);
            panelCapturas.setMaxWidth(2000);
            panelCapturas.setMaxHeight(3000);
        }
    }

    private VBox crearElementosVideojuego(String c) {
        VBox vbox = new VBox();
  
        try {
            Image image = new Image(new FileInputStream("src\\main\\resources\\grupo\\ListaVideojuegos\\imagenes\\Screenshot\\" + c), 1920, 1080, true, false);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);
            vbox.getChildren().add(imageView);
            vbox.setPadding(new Insets(0, 8, 0, 0));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        vbox.setSpacing(20);

        
  
        return vbox;
    }

    public HBox infoReview(){
        HBox infoReview=new HBox(15);
        
        Label tituloReview=new Label("Reseñas: ");
        tituloReview.setFont(Font.font("sans-serif", 15));
        tituloReview.setTextFill(Color.web("rgb(168, 167, 167)"));
        
        
        Button botonfecha=new Button("Ver más antiguos ");
        botonfecha.setTextFill(Color.web("rgb(255, 255, 255)"));
        botonfecha.setStyle("-fx-background-color:transparent;"
                + "-fx-border-color: white;");
        botonfecha.setCursor(Cursor.CLOSED_HAND);
        
        botonfecha.setOnAction(eh->ordenarReviewsFecha());
        Button botonValoracion =new Button("Ver por mejores Reseñas ");
        botonValoracion.setTextFill(Color.web("rgb(255, 255, 255)"));
        botonValoracion.setCursor(Cursor.CLOSED_HAND);
        botonValoracion.setStyle("-fx-background-color:transparent;"
                + "-fx-border-color: white;");
        botonValoracion.setOnAction(eh->ordenarReviewsValoracion());
        infoReview.getChildren().addAll(tituloReview,botonfecha,botonValoracion);
        infoReview.setAlignment(Pos.CENTER);
        infoReview.setPadding(new Insets(0, 0, 10, 0));

       return infoReview; 
    }

    public String promedioValoracion(){
        
        int s=0;
        int e=0;
        for(Review r: videojuegoe.getReviews()){
            s+=r.getValoracion(); 
            e++;
        }
        if(e==0){
            return "0";
        }
        
        String avg=Integer.toString(s/e);
        return avg;
    }
    
    public void ordenarReviewsFecha(){
        LCDE<Review> tmp = new LCDE<>();
        PriorityQueue<Review> colaReview = new PriorityQueue<>((v1,v2)->{
            return v1.getFecha().compareTo(v2.getFecha());
        });
        for (Review v : videojuegoe.getReviews()) {
            colaReview.offer(v);
        }
        while (!colaReview.isEmpty()) {
            tmp.addLast(colaReview.remove());
        }
        vboxReview.getChildren().clear();

        Reviews(tmp);
    }
    
    
        public void ordenarReviewsValoracion(){
        LCDE<Review> tmp = new LCDE<>();
        PriorityQueue<Review> colaReview = new PriorityQueue<>((v1,v2)->{
            return v2.getValoracion()-(v1.getValoracion());
        });
        for (Review v : videojuegoe.getReviews()) {
            colaReview.offer(v);
        }
        while (!colaReview.isEmpty()) {
            tmp.addLast(colaReview.remove());
        }
        vboxReview.getChildren().clear();

        Reviews(tmp);
    }
}


