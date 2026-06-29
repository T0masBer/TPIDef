package integrado.entities;

import integrado.enums.Estado;
import integrado.enums.FormaPago;
import integrado.interfaces.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private final List<DetallePedido> detalles = new ArrayList<>();

    public Pedido(Usuario usuario) {
        super();
        this.usuario = usuario;
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
    }



    public void addDetallePedido(int cantidad, Producto producto) {
        if(cantidad <= 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        if (producto == null || producto.isEliminado()){
            throw new IllegalArgumentException("Producto invalido o eliminado");
        }

        DetallePedido detalle = new DetallePedido(cantidad,producto);
        detalles.add(detalle);
        this.total = calcularTotal();

    }

    public Optional<DetallePedido> findeDetallePedidoByProducto(Producto producto){
        return detalles.stream().filter(d -> d.getProducto().equals(producto) && !d.isEliminado()).findFirst();
    }

    public boolean deleteDetallePedidoByProduccto(Producto producto){
        Optional<DetallePedido> opt = findeDetallePedidoByProducto(producto);
        if (opt.isPresent()){
            opt.get().setEliminado(true);
            this.total = calcularTotal();
            return true;
        }
        return false;
    }


    @Override
    public double calcularTotal() {
        return detalles.stream()
                .filter(d -> !d.isEliminado())
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }


    @Override
    public String toString() {
        return String.format("Pedido{id=%d, usuario='%s', estado=%s, formaPago=%s, total=%.2f, fecha=%s}", id,
                usuario == null ? "null" : usuario.getMail(), estado, formaPago, total, fecha);
    }
}
