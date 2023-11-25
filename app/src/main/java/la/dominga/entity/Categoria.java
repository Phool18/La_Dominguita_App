package la.dominga.entity;


public class Categoria {

    private int id;


    private String nombre;

    private Picture foto;

    public Categoria() {
    }

    public Categoria(int id, String nombre, Picture foto) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Picture getFoto() {
        return foto;
    }

    public void setFoto(Picture foto) {
        this.foto = foto;
    }
}
