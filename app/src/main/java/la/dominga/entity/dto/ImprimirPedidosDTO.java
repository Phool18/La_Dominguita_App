package la.dominga.entity.dto;

import java.util.List;

import la.dominga.entity.CarritoDeCompras;
import la.dominga.entity.DatosCompra;

public class ImprimirPedidosDTO {

    private CarritoDeCompras carritoDeCompras ;

    private List<DatosCompra> detallePedido;

    public ImprimirPedidosDTO() {
    }

    public ImprimirPedidosDTO(CarritoDeCompras carritoDeCompras, List<DatosCompra> detallePedido) {
        this.carritoDeCompras = carritoDeCompras;
        this.detallePedido = detallePedido;
    }

    public CarritoDeCompras getCarritoDeCompras() {
        return carritoDeCompras;
    }

    public void setCarritoDeCompras(CarritoDeCompras carritoDeCompras) {
        this.carritoDeCompras = carritoDeCompras;
    }

    public List<DatosCompra> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DatosCompra> detallePedido) {
        this.detallePedido = detallePedido;
    }
}
