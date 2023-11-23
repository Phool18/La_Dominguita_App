package la.dominga.entity;


public class Cliente {

    private int id;

    private String nombreCompleto;

    private String numeroTelefonico;

    private Foto foto;

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

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
    public String getNombreCompletoCliente() {
        return this.nombreCompleto != null ? this.nombreCompleto : "-----";
    }
}
