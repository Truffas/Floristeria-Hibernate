package com.sergiosanz.floristeriaHibernate.mvc;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase Vista que hereda de JFrame que permite la utilización de la interfaz
 */

public class Vista extends JFrame {
    private JPanel panel1;
    private JFrame frame;

    //Panel Clientes
    public JTabbedPane tabbedPane1;
    public JTextField txtNombre;
    public JTextField txtApellidos;
    public JTextField txtEmail;
    public JTextField txtTelefono;
    public JButton altaClienteButton;
    public JButton modificarClienteButton;
    public JButton borrarClienteButton;
    public JList listClientes;
    public DatePicker fechaAlta;
    public JTable tablePedidosCliente;

    //Panel Pedidos
    public DatePicker fecha;
    public JTextField txtTotal;
    public JComboBox comboCliente;
    public JList listPedidos;
    public JButton altaPedidoButton;
    public JButton modificarPedidoButton;
    public JButton borrarPedidoButton;

    //Panel Detalles
    public JTextField txtCantidad;
    public JTextField txtPrecioDetalle;
    public JComboBox comboPedido;
    public JComboBox comboAdorno;
    public JButton altaDetallesButton;
    public JButton modificarDetallesButton;
    public JButton borrarDetallesButton;
    public JList listDetalles;

    //Panel Adornos
    public JTextField txtTipo;
    public JTextField txtDescripcion;
    public JTextField txtPrecioAdorno;
    public JTextField txtStock;
    public JButton altaAdornoButton;
    public JButton modificarAdornoButton;
    public JButton borrarAdornoButton;
    public JList listAdornos;

    public DefaultListModel dlmClientes;
    public DefaultListModel dlmPedidos;
    public DefaultListModel dlmDetalles;
    public DefaultListModel dlmAdornos;

    public DefaultTableModel dtmPedidosCliente;

    //Menu
    public JMenuItem conexionItem;
    public JMenuItem salirItem;

    public Vista() {
        frame = new JFrame("Floristería Hibernate");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("/flor.png")).getImage()); // Cargo la imagen desde los recursos del proyecto
        frame.setVisible(true);
        frame.setSize(new Dimension(550, 450));
        frame.setLocationRelativeTo(null);

        crearMenu();
        crearModelos();
    }

    /**
     * Método que permite crear un menú y añadir actionCommands
     */
    private void crearMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        conexionItem = new JMenuItem("Conectar");
        conexionItem.setActionCommand("Conectar");

        salirItem = new JMenuItem("Salir");
        salirItem.setActionCommand("Salir");

        menu.add(conexionItem);
        menu.add(salirItem);
        barra.add(menu);
        frame.setJMenuBar(barra);
    }

    /**
     * Método que permite crear los modelos, es decir añadir DLM a las listas
     */
    private void crearModelos() {
        dlmClientes = new DefaultListModel();
        listClientes.setModel(dlmClientes);

        dlmPedidos = new DefaultListModel();
        listPedidos.setModel(dlmPedidos);

        dlmDetalles = new DefaultListModel();
        listDetalles.setModel(dlmDetalles);

        dlmAdornos = new DefaultListModel();
        listAdornos.setModel(dlmAdornos);

        dtmPedidosCliente = new DefaultTableModel();
        dtmPedidosCliente.addColumn("ID");
        dtmPedidosCliente.addColumn("Fecha");
        dtmPedidosCliente.addColumn("Total");
        tablePedidosCliente.setModel(dtmPedidosCliente);

    }
}
