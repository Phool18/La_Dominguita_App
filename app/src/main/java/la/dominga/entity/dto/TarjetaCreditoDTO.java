package la.dominga.entity.dto;

public class TarjetaCreditoDTO {
    private int id;
    private String numeroTarjeta;
    private String cvv;
    private String mes_anio;

    public TarjetaCreditoDTO() {
    }

    public TarjetaCreditoDTO(int id, String numeroTarjetaOculto, String cvvOculto, String mesAnioOculto) {
        this.id = id;
        this.numeroTarjeta = numeroTarjetaOculto;
        this.cvv = cvvOculto;
        this.mes_anio = mesAnioOculto;
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getMes_anio() {
        return mes_anio;
    }

    public void setMes_anio(String mes_anio) {
        this.mes_anio = mes_anio;
    }
}
