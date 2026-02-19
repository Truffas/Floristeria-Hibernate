package com.sergiosanz.floristeriaHibernate.base;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categorias", schema = "floristeria_hibernate", catalog = "")
public class Categoria {
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private List<Adorno> adornos;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return id == categoria.id &&
                Objects.equals(codigo, categoria.codigo) &&
                Objects.equals(nombre, categoria.nombre) &&
                Objects.equals(descripcion, categoria.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nombre, descripcion);
    }

    @OneToMany(mappedBy = "categoria")
    public List<Adorno> getAdornos() {
        return adornos;
    }

    public void setAdornos(List<Adorno> adornos) {
        this.adornos = adornos;
    }
}
