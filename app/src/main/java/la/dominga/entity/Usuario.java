package la.dominga.entity;


import com.google.gson.Gson;

public class Usuario {

    private int id;

    private String correo;

    private String clave;

    private Cliente cliente;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public static Usuario fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Usuario.class);
    }
}
