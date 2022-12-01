package grupo.modelo;

public class WishList {
    private String nombre;
    private LCDE<Videojuego> lista;

    public WishList(String nombre) {
        this.nombre = nombre;
        lista = new LCDE<Videojuego>();
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
