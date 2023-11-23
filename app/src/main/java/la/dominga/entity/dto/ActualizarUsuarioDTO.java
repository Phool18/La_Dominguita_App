package la.dominga.entity.dto;

public class ActualizarUsuarioDTO {
    private int idUsuario;
    private String correo;
    private String nombreCompleto;
    private String numeroTelefonico;

    public ActualizarUsuarioDTO() {
    }

    public ActualizarUsuarioDTO(int idUsuario, String correo, String nombreCompleto, String numeroTelefonico) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
        this.numeroTelefonico = numeroTelefonico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
}