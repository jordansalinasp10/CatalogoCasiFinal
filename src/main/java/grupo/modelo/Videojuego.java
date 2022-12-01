
package grupo.modelo;

public class Videojuego {
    private String titulo;
    private String descripcion;
    private String genero;
    private String desarrolladora;
    private String fechaDeLanzamiento;
    private String portada;
    private LCDE<String> capturasDePantalla;
    public LCDE<Review> reviews = new LCDE<>(); 


    public Videojuego(String titulo, String descripcion, String genero, String desarrolladora, String fechaDeLanzamiento, String portada, LCDE<String> capturasDePantalla) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.genero = genero;
        this.desarrolladora = desarrolladora;
        this.fechaDeLanzamiento = fechaDeLanzamiento;
        this.portada = portada;
        this.capturasDePantalla = capturasDePantalla;

    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getGeneros() {
        return genero;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public String getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public String getPortada() {
        return portada;
    }

    public LCDE<String> getCapturasDePantalla() {
        return capturasDePantalla;
    }
    
    public String toString(){
        return titulo;
    }
    
    public LCDE<Review> getReviews(){
        return reviews;
    }   
    
    public  void addReviews(Review r){
        reviews.addLast(r);
    }
    
    
    public void imprimirReviews(){
        for(Review r: reviews){
        System.out.println(r.toString());
        }
    }
}
