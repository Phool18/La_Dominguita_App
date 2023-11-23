package la.dominga.entity.dto;


import la.dominga.entity.CarritoDeCompras;
import la.dominga.entity.DatosCompra;

public class ImprimirPedidosDTO {

    private CarritoDeCompras carritoDeCompras ;

    private Iterable<DatosCompra> detallePedido;

    public ImprimirPedidosDTO() {
    }

    public ImprimirPedidosDTO(CarritoDeCompras carritoDeCompras, Iterable<DatosCompra> detallePedido) {
        this.carritoDeCompras = carritoDeCompras;
        this.detallePedido = detallePedido;
    }

    public CarritoDeCompras getCarritoDeCompras() {
        return carritoDeCompras;
    }

    public void setCarritoDeCompras(CarritoDeCompras carritoDeCompras) {
        this.carritoDeCompras = carritoDeCompras;
    }

    public Iterable<DatosCompra> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(Iterable<DatosCompra> detallePedido) {
        this.detallePedido = detallePedido;
    }
}
