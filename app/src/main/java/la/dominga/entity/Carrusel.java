package la.dominga.entity;

public class Carrusel {
    private String titulo;
    private int imagen;

    public Carrusel() {
    }

    public Carrusel(int imagen, String titulo) {
        this.imagen = imagen;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}