import com.formdev.flatlaf.FlatLightLaf;
import com.sergiosanz.floristeriaHibernate.mvc.Controlador;
import com.sergiosanz.floristeriaHibernate.mvc.Modelo;
import com.sergiosanz.floristeriaHibernate.mvc.Vista;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.out.println("No se ha podido aplicar FlatLaf.");
        }

        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo, vista);
    }
}