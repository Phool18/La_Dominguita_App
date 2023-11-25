package la.dominga.entity;


public class Cliente {

    private int id;

    private String nombreCompleto;

    private String numeroTelefonico;

    private Picture foto;

    public Cliente() {
    }

    public Cliente(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public Picture getFoto() {
        return foto;
    }

    public void setFoto(Picture foto) {
        this.foto = foto;
    }
    public String getNombreCompletoCliente() {
        return this.nombreCompleto != null ? this.nombreCompleto : "-----";
    }
}
