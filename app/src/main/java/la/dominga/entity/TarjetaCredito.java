package la.dominga.entity;


import java.time.LocalDate;
import java.time.Year;

public class TarjetaCredito {


    private int id;


    private String numeroTarjeta;


    private String titular;


    private String mes_anio;


    private String cvv;


    private Usuario usuario;

    public TarjetaCredito() {
    }

    public TarjetaCredito(int id, String numeroTarjeta, String titular, String mes_anio, String cvv, Usuario usuario) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.mes_anio = mes_anio;
        this.cvv = cvv;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getMes_anio() {
        return mes_anio;
    }

    public void setMes_anio(String mes_anio) {
        this.mes_anio = mes_anio;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public boolean isValidDate() {
        try {
            // Intenta analizar la cadena mes_anio en un objeto LocalDate
            String[] parts = mes_anio.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt("20" + parts[1]); // Suponiendo que siempre es en el siglo XXI

            LocalDate inputDate = LocalDate.of(Year.now().getValue(), month, 1);
            LocalDate currentDate = LocalDate.now();

            return !inputDate.isBefore(currentDate);
        } catch (Exception e) {
            return false; // Error al analizar la cadena o fecha en el pasado
        }
    }
}
