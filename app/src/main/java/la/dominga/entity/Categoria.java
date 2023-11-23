package la.dominga.entity;


public class Categoria {

    private int id;


    private String nombre;

    private Foto foto;

    public Categoria() {
    }

    public Categoria(int id, String nombre, Foto foto) {
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

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
