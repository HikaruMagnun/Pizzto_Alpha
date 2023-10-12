package pizzoto.app.models;

public class PedidoDescripcion {
    Producto producto = new Producto();
    int cantidad;

    public PedidoDescripcion(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public PedidoDescripcion() {

    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
