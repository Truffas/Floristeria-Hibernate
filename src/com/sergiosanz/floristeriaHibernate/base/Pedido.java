package com.sergiosanz.floristeriaHibernate.base;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedidos", schema = "floristeria_hibernate", catalog = "")
public class Pedido {
    private int id;
    private Date fechaCreacion;
    private double total;
    private Cliente cliente;
    private Factura factura;
    private List<DetallePedido> detalles;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Basic
    @Column(name = "total")
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id &&
                Double.compare(pedido.total, total) == 0 &&
                Objects.equals(fechaCreacion, pedido.fechaCreacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaCreacion, total);
    }

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id_pedido", nullable = false)
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @OneToMany(mappedBy = "pedido")
    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Pedido ID: " + id + " | Fecha: " + fechaCreacion + " | Total: " + total;
    }

}
