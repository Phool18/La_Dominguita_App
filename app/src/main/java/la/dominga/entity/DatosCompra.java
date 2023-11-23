package la.dominga.entity;

public class DatosCompra {

    private int id;

    private int cantidad;

    private Double precio;

    private Producto producto;

    private CarritoDeCompras carritoDeCompras;

    public DatosCompra() {
    }

    public DatosCompra(int id, int cantidad, Double precio, Producto producto, CarritoDeCompras carritoDeCompras) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.producto = producto;
        this.carritoDeCompras = carritoDeCompras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public CarritoDeCompras getCarritoDeCompras() {
        return carritoDeCompras;
    }

    public void setCarritoDeCompras(CarritoDeCompras carritoDeCompras) {
        this.carritoDeCompras = carritoDeCompras;
    }
}