package la.dominga.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import la.dominga.entity.DatosCompra;
import la.dominga.entity.Producto;

public class Carrito {
    private static final List<DatosCompra> datosCompras = new ArrayList<>();

    public static String agregarProductos(DatosCompra datosCompra) {
        Optional<DatosCompra> existingItem = datosCompras.stream()
                .filter(item -> item.getProducto().getId() == datosCompra.getProducto().getId())
                .findFirst();

        if (existingItem.isPresent()) {
            int index = datosCompras.indexOf(existingItem.get());
            datosCompras.set(index, datosCompra);
            return "El producto ha sido agregado al carrito, se actualizará la cantidad";
        } else {
            datosCompras.add(datosCompra);
            return "El producto ha sido agregado al carrito con éxito";
        }
    }

    public static void eliminar(final int idProducto) {
        Optional<DatosCompra> itemToRemove = datosCompras.stream()
                .filter(item -> item.getProducto().getId() == idProducto)
                .findFirst();

        itemToRemove.ifPresent(dpE -> {
            datosCompras.remove(dpE);
            System.out.println("Se eliminó el producto del detalle de pedido");
        });
    }

    public static List<DatosCompra> getDatosCompras() {
        return datosCompras;
    }

    public static void limpiar() {
        datosCompras.clear();
    }
}
