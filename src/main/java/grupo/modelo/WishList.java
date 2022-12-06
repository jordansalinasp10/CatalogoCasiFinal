package grupo.modelo;
import java.io.Serializable;
public class WishList implements Serializable{
    private String nombre;
    private LCDE<Videojuego> lista;

    public WishList(String nombre) {
        this.nombre = nombre;
        lista = new LCDE<Videojuego>();
    }
    public WishList(){

    }

    public String getNombre() {
        return nombre;
    }

    public LCDE<Videojuego> getLista() {
        return lista;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
