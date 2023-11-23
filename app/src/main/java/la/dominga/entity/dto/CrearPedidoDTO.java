package la.dominga.entity.dto;


import la.dominga.entity.CarritoDeCompras;
import la.dominga.entity.Cliente;
import la.dominga.entity.DatosCompra;

public class CrearPedidoDTO {
    private CarritoDeCompras carritoDeCompras ;

    private Iterable<DatosCompra> informacionDeLaVenta;
    private Cliente cliente;

    public CrearPedidoDTO() {
    }

    public CrearPedidoDTO(CarritoDeCompras carritoDeCompras, Iterable<DatosCompra> informacionDeLaVenta, Cliente cliente) {
        this.carritoDeCompras = carritoDeCompras;
        this.informacionDeLaVenta = informacionDeLaVenta;
        this.cliente = cliente;
    }

    public CarritoDeCompras getCarritoDeCompras() {
        return carritoDeCompras;
    }

    public void setCarritoDeCompras(CarritoDeCompras carritoDeCompras) {
        this.carritoDeCompras = carritoDeCompras;
    }

    public Iterable<DatosCompra> getInformacionDeLaVenta() {
        return informacionDeLaVenta;
    }

    public void setInformacionDeLaVenta(Iterable<DatosCompra> informacionDeLaVenta) {
        this.informacionDeLaVenta = informacionDeLaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
