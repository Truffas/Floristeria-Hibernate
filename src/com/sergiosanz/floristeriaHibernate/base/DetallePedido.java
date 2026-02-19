package com.sergiosanz.floristeriaHibernate.base;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pedido_adorno", schema = "floristeria_hibernate", catalog = "")
public class DetallePedido {
    private int id;
    private int cantidad;
    private double precio;
    private Pedido pedido;
    private Adorno adorno;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cantidad")
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Basic
    @Column(name = "precio")
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetallePedido that = (DetallePedido) o;
        return id == that.id &&
                cantidad == that.cantidad &&
                Double.compare(that.precio, precio) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidad, precio);
    }

    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id", nullable = false)
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @ManyToOne
    @JoinColumn(name = "id_adorno", referencedColumnName = "id", nullable = false)
    public Adorno getAdorno() {
        return adorno;
    }

    public void setAdorno(Adorno adorno) {
        this.adorno = adorno;
    }

    @Override
    public String toString() {
        return "Pedido: " + pedido.getId() + " | Adorno: " + adorno.getTipo() +
                " | Cant: " + cantidad + " | Precio: " + precio;
    }

}
