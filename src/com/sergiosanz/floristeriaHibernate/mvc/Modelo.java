package com.sergiosanz.floristeriaHibernate.mvc;

import com.sergiosanz.floristeriaHibernate.base.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;

public class Modelo {

    SessionFactory sessionFactory;

    public void desconectar() {
        // Cierro la factoría de sesiones si está abierta
        if (sessionFactory != null && sessionFactory.isOpen())
            sessionFactory.close();
    }

    public void conectar() {
        Configuration configuracion = new Configuration();
        // Cargo el fichero hibernate.cfg.xml
        configuracion.configure("hibernate.cfg.xml");

        // Indico las clases mapeadas
        configuracion.addAnnotatedClass(Cliente.class);
        configuracion.addAnnotatedClass(Pedido.class);
        configuracion.addAnnotatedClass(DetallePedido.class);
        configuracion.addAnnotatedClass(Adorno.class);
        configuracion.addAnnotatedClass(Categoria.class);
        configuracion.addAnnotatedClass(Proveedor.class);
        configuracion.addAnnotatedClass(Factura.class);

        //Creamos un objeto ServiceRegistry a partir de los parámetros de configuración
        //Esta clase se usa para gestionar y proveer de acceso a servicios
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
                .applySettings(configuracion.getProperties()).build();

        //finalmente creamos un objeto sessionfactory a partir de la configuracion y del registro de servicios
        sessionFactory = configuracion.buildSessionFactory(ssr);
    }


    ArrayList<Cliente> getClientes() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Cliente");
        ArrayList<Cliente> lista = (ArrayList<Cliente>) query.getResultList();
        sesion.close();
        return lista;
    }

    ArrayList<Pedido> getPedidos() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Pedido");
        ArrayList<Pedido> lista = (ArrayList<Pedido>) query.getResultList();
        sesion.close();
        return lista;
    }

    ArrayList<Adorno> getAdornos() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Adorno");
        ArrayList<Adorno> lista = (ArrayList<Adorno>) query.getResultList();
        sesion.close();
        return lista;
    }

    ArrayList<DetallePedido> getDetalles() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM DetallePedido");
        ArrayList<DetallePedido> lista = (ArrayList<DetallePedido>) query.getResultList();
        sesion.close();
        return lista;
    }

    ArrayList<Pedido> getPedidosCliente(Cliente cliente) {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Pedido WHERE cliente = :cli");
        query.setParameter("cli", cliente);
        ArrayList<Pedido> lista = (ArrayList<Pedido>) query.getResultList();
        sesion.close();
        return lista;
    }

    ArrayList<DetallePedido> getDetallesPedido(Pedido pedido) {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM DetallePedido WHERE pedido = :ped");
        query.setParameter("ped", pedido);
        ArrayList<DetallePedido> lista = (ArrayList<DetallePedido>) query.getResultList();
        sesion.close();
        return lista;
    }

    ArrayList<DetallePedido> getDetallesAdorno(Adorno adorno) {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM DetallePedido WHERE adorno = :a");
        query.setParameter("a", adorno);
        ArrayList<DetallePedido> lista = (ArrayList<DetallePedido>) query.getResultList();
        sesion.close();
        return lista;
    }

    // Para dar de alta Adorno sin meter Categoria/Proveedor en la interfaz:
    Categoria getPrimeraCategoria() {
        Session sesion = sessionFactory.openSession();
        Query q = sesion.createQuery("FROM Categoria");
        ArrayList<Categoria> lista = (ArrayList<Categoria>) q.getResultList();
        sesion.close();
        if (lista.isEmpty()) return null;
        return lista.get(0);
    }

    Proveedor getPrimerProveedor() {
        Session sesion = sessionFactory.openSession();
        Query q = sesion.createQuery("FROM Proveedor");
        ArrayList<Proveedor> lista = (ArrayList<Proveedor>) q.getResultList();
        sesion.close();
        if (lista.isEmpty()) return null;
        return lista.get(0);
    }

    void insertar(Object o) {
        Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.save(o);
        sesion.getTransaction().commit();
        sesion.close();
    }

    void modificar(Object o) {
        Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(o);
        sesion.getTransaction().commit();
        sesion.close();
    }

    void eliminar(Object o) {
        Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.delete(o);
        sesion.getTransaction().commit();
        sesion.close();
    }
}

