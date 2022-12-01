package grupo.modelo;

public class Review {
    private String comentario;
    private String Usuario;
    private int valoracion;
    private String fecha;

    public Review(String comentario, String Usuario, int valoracion, String  fecha) {
        this.comentario = comentario;
        this.Usuario = Usuario;
        this.valoracion = valoracion;
        this.fecha=fecha;

    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Review{" + "comentario=" + comentario + ", Usuario=" + Usuario + ", valoracion=" + valoracion + ", fecha=" + fecha + '}';
    }



            
}
