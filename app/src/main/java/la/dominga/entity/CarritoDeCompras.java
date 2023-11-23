package la.dominga.entity;


import java.sql.Date;

public class CarritoDeCompras {

    private int id;

    private Date fechaCompra;

    private Cliente cliente;

    private Double monto;

    public CarritoDeCompras() {
    }

    public CarritoDeCompras(int id, Date fechaCompra, Cliente cliente, Double monto) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.cliente = cliente;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
