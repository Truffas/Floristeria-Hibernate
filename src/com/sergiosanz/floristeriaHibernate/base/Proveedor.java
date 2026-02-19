package com.sergiosanz.floristeriaHibernate.base;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "proveedores", schema = "floristeria_hibernate", catalog = "")
public class Proveedor {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
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
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proveedor proveedor = (Proveedor) o;
        return id == proveedor.id &&
                Objects.equals(nombre, proveedor.nombre) &&
                Objects.equals(telefono, proveedor.telefono) &&
                Objects.equals(email, proveedor.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, telefono, email);
    }

    @OneToMany(mappedBy = "proveedor")
    public List<Adorno> getAdornos() {
        return adornos;
    }

    public void setAdornos(List<Adorno> adornos) {
        this.adornos = adornos;
    }
}
