package la.dominga.entity.dto;


import java.util.List;

import la.dominga.entity.CarritoDeCompras;
import la.dominga.entity.Cliente;
import la.dominga.entity.DatosCompra;

public class CrearPedidoDTO {
    private CarritoDeCompras carritoDeCompras ;

    private List<DatosCompra> informacionDeLaVenta;
    private Cliente cliente;

    public CrearPedidoDTO() {
    }

    public CrearPedidoDTO(CarritoDeCompras carritoDeCompras, List<DatosCompra> informacionDeLaVenta, Cliente cliente) {
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

    public List<DatosCompra> getInformacionDeLaVenta() {
        return informacionDeLaVenta;
    }

    public void setInformacionDeLaVenta(List<DatosCompra> informacionDeLaVenta) {
        this.informacionDeLaVenta = informacionDeLaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
