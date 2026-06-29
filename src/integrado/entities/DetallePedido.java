package integrado.entities;

public class DetallePedido extends Base{
    private int cantidad;
    private double subTotal;
    private Producto producto;

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subTotal = producto.getPrecio() * cantidad;
    }


    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                '}';
    }
}
