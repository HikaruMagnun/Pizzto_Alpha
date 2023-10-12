package pizzoto.app.models;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Date fecha ;
    private Time horaRealizadoPedido;
    private Time horaEntrega;
    private String tipo = "llevar";
    private String estado;
    private double totalPagar;
    private Cliente cliente;
    private Empleado empleado;
    private List<PedidoDescripcion> pedidoListaDescripton = new ArrayList<>();



    // Constructores
    public Pedido() {

    }

    public Pedido(int id, Date fecha, Time horaRealizadoPedido, Time horaEntrega, String tipo, String estado, double totalPagar, Cliente cliente, Empleado empleado, List<PedidoDescripcion> pedidoListaDescripton) {
        this.id = id;
        this.fecha = fecha;
        this.horaRealizadoPedido = horaRealizadoPedido;
        this.horaEntrega = horaEntrega;
        this.tipo = tipo;
        this.estado = estado;
        this.totalPagar = totalPagar;
        this.cliente = cliente;
        this.empleado = empleado;
        this.pedidoListaDescripton = pedidoListaDescripton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHoraRealizadoPedido() {
        return horaRealizadoPedido;
    }

    public void setHoraRealizadoPedido(Time horaRealizadoPedido) {
        this.horaRealizadoPedido = horaRealizadoPedido;
    }

    public Time getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(Time horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<PedidoDescripcion> getPedidoListaDescripton() {
        return pedidoListaDescripton;
    }

    public void setPedidoListaDescripton(List<PedidoDescripcion> pedidoListaDescripton) {
        this.pedidoListaDescripton = pedidoListaDescripton;
    }
}

