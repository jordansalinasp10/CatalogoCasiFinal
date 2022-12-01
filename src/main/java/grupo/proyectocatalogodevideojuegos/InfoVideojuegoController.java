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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


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


    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            titulo.setText(videojuegoe.getTitulo());
            descripcion.setText(videojuegoe.getDescripcion());
            genero.setText(videojuegoe.getGeneros());
            portada.setImage(new Image(new FileInputStream("src\\main\\resources\\grupo\\ListaVideojuegos\\imagenes\\Portada\\" + videojuegoe.getPortada()), 1280, 720, true, false));
            portada.setFitWidth(200);
            portada.setFitHeight(300);




        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Label tituloReview=new Label("Reseñas: ");
        
        String avg=Float.toString(promedioValoracion(videojuegoe.getReviews()));
        Label prome=new Label("Promedio de valoraciones: "+avg+"/100");
        vBoxInformacionReview.getChildren().addAll(prome,infoReview());
        
        infoReview();
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
        nombreUsuario.setTextFill(Color.web("#F5F5F5"));
        nombreUsuario.setFont(Font.font("SansSerif", 13));
        nombreUsuario.setMaxWidth(150);
        Label fechaR=new Label();
        fechaR.setText("Usuario: "+r.getUsuario());
        fechaR.setTextFill(Color.web("#F5F5F5"));
        fechaR.setFont(Font.font("SansSerif", 13));
        fechaR.setMaxWidth(150);
        fechaR.setText("Publicado el: "+r.getFecha()); 
        
        nombre_fecha.getChildren().addAll(nombreUsuario,fechaR);
        
        Label valoracion=new Label();
        valoracion.setText("Valoracion: "+r.getValoracion()+"/100");
        
        Label comentario=new Label();
        comentario.setText(r.getComentario());
        
        VboxR.getChildren().addAll(nombre_fecha,valoracion,comentario);
        
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
        
        Button botonfecha=new Button("Ver más antiguos ");
        botonfecha.setOnAction(eh->ordenarReviewsFecha());
        Button botonValoracion =new Button("Ver por mejores Reseñas ");
        botonValoracion.setOnAction(eh->ordenarReviewsValoracion());
        infoReview.getChildren().addAll(tituloReview,botonfecha,botonValoracion);

       return infoReview; 
    }

    public float promedioValoracion(LCDE<Review> reviews){
        int s=0;
        int e=0;
        for(Review r: reviews){
            s+=r.getValoracion(); 
            e++;
        }
        if(e==0){
            return 0;
        }
        
        return (float) s/e;
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


