package la_dominga.entidades;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    private double monto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodo;

    public Pago() {
    }

    public Pago(Long id, Compra compra, double monto, Date fechaPago, MetodoPago metodo) {
        this.id = id;
        this.compra = compra;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodo = metodo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }
}
