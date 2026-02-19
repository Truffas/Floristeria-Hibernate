package com.sergiosanz.floristeriaHibernate.mvc;

import com.sergiosanz.floristeriaHibernate.base.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class Controlador implements ActionListener, ListSelectionListener {

    private Vista vista;
    private Modelo modelo;
    private boolean conectado;

    public Controlador(Modelo modelo, Vista vista) {
        this.vista = vista;
        this.modelo = modelo;
        this.conectado = false;

        addActionListeners(this);
        addListSelectionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (!conectado && !comando.equalsIgnoreCase("Conectar") && !comando.equalsIgnoreCase("Salir")) {
            JOptionPane.showMessageDialog(null, "No has conectado con la BBDD",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (comando) {
            case "Salir":
                modelo.desconectar();
                System.exit(0);
                break;

            case "Conectar":
                vista.conexionItem.setEnabled(false);
                modelo.conectar();
                conectado = true;
                break;

            // CLIENTES
            case "altaClienteBtn": {
                Cliente c = new Cliente();
                c.setNombre(vista.txtNombre.getText());
                c.setApellidos(vista.txtApellidos.getText());
                c.setEmail(vista.txtEmail.getText());
                c.setTelefono(vista.txtTelefono.getText());

                if (vista.fechaAlta.getDate() != null) {
                    c.setFechaAlta(Date.valueOf(vista.fechaAlta.getDate()));
                }

                modelo.insertar(c);
                break;
            }

            case "modificarClienteBtn": {
                Cliente c = (Cliente) vista.listClientes.getSelectedValue();
                if (c == null) break;
                c.setNombre(vista.txtNombre.getText());
                c.setApellidos(vista.txtApellidos.getText());
                c.setEmail(vista.txtEmail.getText());
                c.setTelefono(vista.txtTelefono.getText());

                if (vista.fechaAlta.getDate() != null) {
                    c.setFechaAlta(Date.valueOf(vista.fechaAlta.getDate()));
                } else {
                    c.setFechaAlta(null);
                }

                modelo.modificar(c);
                break;
            }

            case "borrarClienteBtn": {
                Cliente c = (Cliente) vista.listClientes.getSelectedValue();
                if (c == null) break;

                // No borramos si tiene pedidos
                if (!modelo.getPedidosCliente(c).isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "No puedes borrar el cliente porque tiene pedidos.\nBorra antes sus pedidos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }

                modelo.eliminar(c);
                break;
            }

            // PEDIDOS
            case "altaPedidoBtn": {
                Pedido p = new Pedido();

                if (vista.fecha.getDate() != null) {
                    p.setFechaCreacion(Date.valueOf(vista.fecha.getDate()));
                }

                double total = 0;
                if (!vista.txtTotal.getText().isEmpty()) {
                    total = Double.parseDouble(vista.txtTotal.getText());
                }
                p.setTotal(total);

                Cliente cli = (Cliente) vista.comboCliente.getSelectedItem();
                p.setCliente(cli);

                modelo.insertar(p);
                break;
            }

            case "modificarPedidoBtn": {
                Pedido p = (Pedido) vista.listPedidos.getSelectedValue();
                if (p == null) break;

                if (vista.fecha.getDate() != null) {
                    p.setFechaCreacion(Date.valueOf(vista.fecha.getDate()));
                } else {
                    p.setFechaCreacion(null);
                }

                double total = 0;
                if (!vista.txtTotal.getText().isEmpty()) {
                    total = Double.parseDouble(vista.txtTotal.getText());
                }
                p.setTotal(total);

                Cliente cli = (Cliente) vista.comboCliente.getSelectedItem();
                p.setCliente(cli);

                modelo.modificar(p);
                break;
            }

            case "borrarPedidoBtn": {
                Pedido p = (Pedido) vista.listPedidos.getSelectedValue();
                if (p == null) break;

                // Borramos antes sus detalles para no dejar datos huerfanos
                ArrayList<DetallePedido> detalles = modelo.getDetallesPedido(p);
                for (DetallePedido d : detalles) {
                    modelo.eliminar(d);
                }

                modelo.eliminar(p);
                break;
            }

            // DETALLES
            case "altaDetallesBtn": {
                DetallePedido d = new DetallePedido();

                int cant = 0;
                if (!vista.txtCantidad.getText().isEmpty()) {
                    cant = Integer.parseInt(vista.txtCantidad.getText());
                }
                d.setCantidad(cant);

                double precio = 0;
                if (!vista.txtPrecioDetalle.getText().isEmpty()) {
                    precio = Double.parseDouble(vista.txtPrecioDetalle.getText());
                }
                d.setPrecio(precio);

                d.setPedido((Pedido) vista.comboPedido.getSelectedItem());
                d.setAdorno((Adorno) vista.comboAdorno.getSelectedItem());

                modelo.insertar(d);
                break;
            }

            case "modificarDetallesBtn": {
                DetallePedido d = (DetallePedido) vista.listDetalles.getSelectedValue();
                if (d == null) break;

                int cant = 0;
                if (!vista.txtCantidad.getText().isEmpty()) {
                    cant = Integer.parseInt(vista.txtCantidad.getText());
                }
                d.setCantidad(cant);

                double precio = 0;
                if (!vista.txtPrecioDetalle.getText().isEmpty()) {
                    precio = Double.parseDouble(vista.txtPrecioDetalle.getText());
                }
                d.setPrecio(precio);

                d.setPedido((Pedido) vista.comboPedido.getSelectedItem());
                d.setAdorno((Adorno) vista.comboAdorno.getSelectedItem());

                modelo.modificar(d);
                break;
            }

            case "borrarDetallesBtn": {
                DetallePedido d = (DetallePedido) vista.listDetalles.getSelectedValue();
                if (d == null) break;

                modelo.eliminar(d);
                break;
            }

            // ADORNOS
            case "altaAdornoBtn": {
                Adorno a = new Adorno();
                a.setTipo(vista.txtTipo.getText());
                a.setDescripcion(vista.txtDescripcion.getText());

                double precio = 0;
                if (!vista.txtPrecioAdorno.getText().isEmpty()) {
                    precio = Double.parseDouble(vista.txtPrecioAdorno.getText());
                }
                a.setPrecio(precio);

                int stock = 0;
                if (!vista.txtStock.getText().isEmpty()) {
                    stock = Integer.parseInt(vista.txtStock.getText());
                }
                a.setStock(stock);

                // En la aplicación no damos opciones a elegir Categoría, ni proveedor, metemos el primero de la bd
                Categoria cat = modelo.getPrimeraCategoria();
                Proveedor pro = modelo.getPrimerProveedor();

                if (cat == null || pro == null) {
                    JOptionPane.showMessageDialog(null,
                            "Para dar de alta un adorno necesitas al menos 1 categoría y 1 proveedor en la BD.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }

                a.setCategoria(cat);
                a.setProveedor(pro);

                modelo.insertar(a);
                break;
            }

            case "modificarAdornoBtn": {
                Adorno a = (Adorno) vista.listAdornos.getSelectedValue();
                if (a == null) break;

                a.setTipo(vista.txtTipo.getText());
                a.setDescripcion(vista.txtDescripcion.getText());

                double precio = 0;
                if (!vista.txtPrecioAdorno.getText().isEmpty()) {
                    precio = Double.parseDouble(vista.txtPrecioAdorno.getText());
                }
                a.setPrecio(precio);

                int stock = 0;
                if (!vista.txtStock.getText().isEmpty()) {
                    stock = Integer.parseInt(vista.txtStock.getText());
                }
                a.setStock(stock);

                modelo.modificar(a);
                break;
            }

            case "borrarAdornoBtn": {
                Adorno a = (Adorno) vista.listAdornos.getSelectedValue();
                if (a == null) break;

                // Borramos antes detalles relacionados
                ArrayList<DetallePedido> dets = modelo.getDetallesAdorno(a);
                for (DetallePedido d : dets) {
                    modelo.eliminar(d);
                }

                modelo.eliminar(a);
                break;
            }
        }

        limpiarCampos();
        actualizar();
    }

    private void addActionListeners(ActionListener listener) {
        vista.conexionItem.addActionListener(listener);
        vista.salirItem.addActionListener(listener);

        // ActionCommands
        vista.altaClienteButton.setActionCommand("altaClienteBtn");
        vista.modificarClienteButton.setActionCommand("modificarClienteBtn");
        vista.borrarClienteButton.setActionCommand("borrarClienteBtn");

        vista.altaPedidoButton.setActionCommand("altaPedidoBtn");
        vista.modificarPedidoButton.setActionCommand("modificarPedidoBtn");
        vista.borrarPedidoButton.setActionCommand("borrarPedidoBtn");

        vista.altaDetallesButton.setActionCommand("altaDetallesBtn");
        vista.modificarDetallesButton.setActionCommand("modificarDetallesBtn");
        vista.borrarDetallesButton.setActionCommand("borrarDetallesBtn");

        vista.altaAdornoButton.setActionCommand("altaAdornoBtn");
        vista.modificarAdornoButton.setActionCommand("modificarAdornoBtn");
        vista.borrarAdornoButton.setActionCommand("borrarAdornoBtn");

        vista.altaClienteButton.addActionListener(listener);
        vista.modificarClienteButton.addActionListener(listener);
        vista.borrarClienteButton.addActionListener(listener);

        vista.altaPedidoButton.addActionListener(listener);
        vista.modificarPedidoButton.addActionListener(listener);
        vista.borrarPedidoButton.addActionListener(listener);

        vista.altaDetallesButton.addActionListener(listener);
        vista.modificarDetallesButton.addActionListener(listener);
        vista.borrarDetallesButton.addActionListener(listener);

        vista.altaAdornoButton.addActionListener(listener);
        vista.modificarAdornoButton.addActionListener(listener);
        vista.borrarAdornoButton.addActionListener(listener);
    }

    //Añadir los listeners de las listas
    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listClientes.addListSelectionListener(listener);
        vista.listPedidos.addListSelectionListener(listener);
        vista.listDetalles.addListSelectionListener(listener);
        vista.listAdornos.addListSelectionListener(listener);
    }

    private void actualizar() {
        listarClientes(modelo.getClientes());
        listarPedidos(modelo.getPedidos());
        listarAdornos(modelo.getAdornos());

        // Si hay pedido seleccionado, muestro sus detalles, si no, muestro todos
        Pedido pedido = (Pedido) vista.listPedidos.getSelectedValue();
        if (pedido != null) {
            listarDetalles(modelo.getDetallesPedido(pedido));
        } else {
            listarDetalles(modelo.getDetalles());
        }

        // Tabla pedidos del cliente seleccionado
        Cliente cliente = (Cliente) vista.listClientes.getSelectedValue();
        if (cliente != null) {
            cargarTablaPedidosCliente(modelo.getPedidosCliente(cliente));
        } else {
            cargarTablaPedidosCliente(new ArrayList<>());
            // cargarTablaPedidosCliente espera una lista, por eso le meto una vacía
        }
    }

    private void listarClientes(ArrayList<Cliente> lista) {
        vista.dlmClientes.clear();
        for (Cliente c : lista) {
            vista.dlmClientes.addElement(c);
        }

        vista.comboCliente.removeAllItems();
        for (Cliente c : lista) {
            vista.comboCliente.addItem(c);
        }
        vista.comboCliente.setSelectedIndex(-1);
    }

    private void listarPedidos(ArrayList<Pedido> lista) {
        vista.dlmPedidos.clear();
        for (Pedido p : lista) {
            vista.dlmPedidos.addElement(p);
        }

        vista.comboPedido.removeAllItems();
        for (Pedido p : lista) {
            vista.comboPedido.addItem(p);
        }
        vista.comboPedido.setSelectedIndex(-1);
    }

    private void listarDetalles(ArrayList<DetallePedido> lista) {
        vista.dlmDetalles.clear();
        for (DetallePedido d : lista) {
            vista.dlmDetalles.addElement(d);
        }
    }

    private void listarAdornos(ArrayList<Adorno> lista) {
        vista.dlmAdornos.clear();
        for (Adorno a : lista) {
            vista.dlmAdornos.addElement(a);
        }

        vista.comboAdorno.removeAllItems();
        for (Adorno a : lista) {
            vista.comboAdorno.addItem(a);
        }
        vista.comboAdorno.setSelectedIndex(-1);
    }

    // Metodo para rellenar la tabla con los pedidos del cliente seleccionado
    private void cargarTablaPedidosCliente(ArrayList<Pedido> pedidos) {
        // Vaciamos la tabla, borro todas las filas
        vista.dtmPedidosCliente.setRowCount(0);
        // Recorremos los pedidos del cliente seleccionado
        for (Pedido p : pedidos) {
            // Creamos una fila, le pasamos un array tipo object
            Object[] fila = new Object[3];
            fila[0] = p.getId();
            fila[1] = p.getFechaCreacion();
            fila[2] = p.getTotal();
            vista.dtmPedidosCliente.addRow(fila);
        }
    }

    private void limpiarCampos() {
        // Clientes
        vista.txtNombre.setText("");
        vista.txtApellidos.setText("");
        vista.txtEmail.setText("");
        vista.txtTelefono.setText("");
        vista.fechaAlta.setDate(null);

        // Pedidos
        vista.fecha.setDate(null);
        vista.txtTotal.setText("");
        vista.comboCliente.setSelectedIndex(-1);

        // Detalles
        vista.txtCantidad.setText("");
        vista.txtPrecioDetalle.setText("");
        vista.comboPedido.setSelectedIndex(-1);
        vista.comboAdorno.setSelectedIndex(-1);

        // Adornos
        vista.txtTipo.setText("");
        vista.txtDescripcion.setText("");
        vista.txtPrecioAdorno.setText("");
        vista.txtStock.setText("");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {

            if (e.getSource() == vista.listClientes) {
                Cliente c = (Cliente) vista.listClientes.getSelectedValue();
                if (c != null) {
                    vista.txtNombre.setText(String.valueOf(c.getNombre()));
                    vista.txtApellidos.setText(String.valueOf(c.getApellidos()));
                    vista.txtEmail.setText(String.valueOf(c.getEmail()));
                    vista.txtTelefono.setText(String.valueOf(c.getTelefono()));

                    if (c.getFechaAlta() != null) {
                        vista.fechaAlta.setDate(c.getFechaAlta().toLocalDate());
                    } else {
                        vista.fechaAlta.setDate(null);
                    }

                    cargarTablaPedidosCliente(modelo.getPedidosCliente(c));
                }
            }

            if (e.getSource() == vista.listPedidos) {
                Pedido p = (Pedido) vista.listPedidos.getSelectedValue();
                if (p != null) {
                    if (p.getFechaCreacion() != null) {
                        vista.fecha.setDate(p.getFechaCreacion().toLocalDate());
                    } else {
                        vista.fecha.setDate(null);
                    }
                    vista.txtTotal.setText(String.valueOf(p.getTotal()));
                    vista.comboCliente.setSelectedItem(p.getCliente());

                    // Al seleccionar pedido, cargo detalles de ese pedido
                    listarDetalles(modelo.getDetallesPedido(p));
                    vista.comboPedido.setSelectedItem(p);
                }
            }

            if (e.getSource() == vista.listDetalles) {
                DetallePedido d = (DetallePedido) vista.listDetalles.getSelectedValue();
                if (d != null) {
                    vista.txtCantidad.setText(String.valueOf(d.getCantidad()));
                    vista.txtPrecioDetalle.setText(String.valueOf(d.getPrecio()));
                    vista.comboPedido.setSelectedItem(d.getPedido());
                    vista.comboAdorno.setSelectedItem(d.getAdorno());
                }
            }

            if (e.getSource() == vista.listAdornos) {
                Adorno a = (Adorno) vista.listAdornos.getSelectedValue();
                if (a != null) {
                    vista.txtTipo.setText(String.valueOf(a.getTipo()));
                    vista.txtDescripcion.setText(String.valueOf(a.getDescripcion()));
                    vista.txtPrecioAdorno.setText(String.valueOf(a.getPrecio()));
                    vista.txtStock.setText(String.valueOf(a.getStock()));
                }
            }
        }
    }
}

