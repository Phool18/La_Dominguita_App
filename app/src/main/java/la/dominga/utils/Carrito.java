package la.dominga.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import la.dominga.entity.DatosCompra;
import la.dominga.entity.Producto;

public class Carrito {
    private static final List<DatosCompra> datosCompras = new ArrayList<>();

    public static String agregarProducto(Producto producto, int cantidad) {
        Optional<DatosCompra> productoExistente = datosCompras.stream()
                .filter(dc -> dc.getProducto().getId() == producto.getId())
                .findFirst();

        if (productoExistente.isPresent()) {
            DatosCompra datosCompraExistente = productoExistente.get();
            datosCompraExistente.setCantidad(datosCompraExistente.getCantidad() + cantidad);
            return "Cantidad actualizada en el carrito";
        } else {
            DatosCompra nuevoDatosCompra = new DatosCompra();
            nuevoDatosCompra.setProducto(producto);
            nuevoDatosCompra.setCantidad(cantidad);
            nuevoDatosCompra.setPrecio(producto.getPrecio());
            datosCompras.add(nuevoDatosCompra);
            return "Producto agregado al carrito";
        }
    }

    public static void eliminarProducto(int idProducto) {
        datosCompras.removeIf(dc -> dc.getProducto().getId() == idProducto);
    }

    public static List<DatosCompra> obtenerProductos() {
        return datosCompras;
    }

    public static void limpiarCarrito() {
        datosCompras.clear();
    }
}
