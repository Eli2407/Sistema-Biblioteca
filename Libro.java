
public class Libro {

    private String titulo;
    private String autor;
    private String ISBN;
    private String genero;
    private int numeroCopias;
    private boolean disponibilidad;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNumeroCopias() {
        return numeroCopias;
    }

    public void setNumeroCopias(int numeroCopias) {
        this.numeroCopias = numeroCopias;
        this.disponibilidad = numeroCopias > 0;
    }

    public void setDisponibilidad(boolean disponible) {
        this.disponibilidad = disponible;
    }
    
    public boolean isDisponible(){
        return disponibilidad;
    }


    @Override
    public String toString() {
        return titulo + "," + autor + "," + ISBN + "," + genero + "," + numeroCopias + "," + disponibilidad;
    }
    
   

}
