package com.sergiosanz.floristeriaHibernate.base;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "adornos", schema = "floristeria_hibernate", catalog = "")
public class Adorno {
    private int id;
    private String tipo;
    private String descripcion;
    private double precio;
    private int stock;
    private Categoria categoria;
    private Proveedor proveedor;
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
    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "precio")
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Basic
    @Column(name = "stock")
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adorno adorno = (Adorno) o;
        return id == adorno.id &&
                Double.compare(adorno.precio, precio) == 0 &&
                stock == adorno.stock &&
                Objects.equals(tipo, adorno.tipo) &&
                Objects.equals(descripcion, adorno.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, descripcion, precio, stock);
    }

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id", nullable = false)
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id", nullable = false)
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @OneToMany(mappedBy = "adorno")
    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Tipo: " + tipo + " | Precio: " + precio + " | Stock: " + stock;
    }

}
