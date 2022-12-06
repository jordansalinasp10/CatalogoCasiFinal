
package grupo.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class LectorCsvCatalogo {
    public static LCDE<Videojuego> cargarListaVideojuegos(){
        LCDE<Videojuego> videojuegos = new LCDE<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\grupo\\ListaVideojuegos\\Catalogo.txt"));){
            br.readLine(); 
            String linea;
            while ((linea = br.readLine())!=null){
                String[] datos = linea.split("/");
                String capturas= datos[6];
                String[] arregloCapturas = capturas.substring(1,capturas.length()-1).split(",");
                
                Videojuego vj=new Videojuego(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],agregarALista(arregloCapturas));
                
                
                vj.addReviews(new Review("Muy bueno","JordanSP10",90,"2022-11-20"));
                vj.addReviews(new Review("Decente","Licc17",70,"2022-11-21"));                
                vj.addReviews(new Review("Me encantoo!","DLaborde",100,"2020-07-10"));  
                vj.addReviews(new Review("Aburrido la mitad de la campana","JorgeG",44,"2021-10-10"));  
                vj.addReviews(new Review("Me gustó pero no lo jugaría de nuevo","Xxx_ProMaster_xxX",55,"2019-01-10"));  
                vj.addReviews(new Review("Disfrutable","DaniLI",75,"2020-12-08"));  
                videojuegos.addLast(vj);
                
            }
        
        }catch (IOException e){
            e.printStackTrace();
        }
        return videojuegos;
    }
    
    public static LCDE<String> agregarALista(String[] arreglo){
        LCDE<String> lista = new LCDE<>();
        for (String s: arreglo){
            
            lista.addLast(s);
        }
        return lista;
    }
    
    public static void guardarWishList(WishList juegos){

        try(ObjectOutputStream serializar=new ObjectOutputStream(new FileOutputStream("src\\main\\resources\\grupo\\ListaVideojuegos\\Lista.csv"))){
      serializar.writeObject(juegos);
        }catch(IOException e){
            e.printStackTrace();
    }
    }
    
    
    public static WishList cargarListaPersonalizada(){
        WishList wl=new WishList();
        try(ObjectInputStream deserializar=new ObjectInputStream(new FileInputStream("src\\main\\resources\\grupo\\ListaVideojuegos\\Lista.csv"))){


         return (WishList)deserializar.readObject();        

 
        }catch(Exception e){
            e.printStackTrace();
        }
        return wl; 
    }
    
    
    public static String toUTF8(String s) {
    if (s != null) {
        String ss;
        try {
            ss = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace(System.out);
            ss = null;
        }
        return ss;
    } else {
        return "";
    }
}
}
