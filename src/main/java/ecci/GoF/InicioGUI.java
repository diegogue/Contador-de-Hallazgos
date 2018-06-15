package ecci.GoF;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interfaz de inicio del programa CHIM, con un botón para conteo nuevo
 * y otro para consulta.
 */

public class InicioGUI {
    private JButton BotonNuevoConteo;
    private JButton BotonConsulta;
    private JPanel Panel;

    /**
     * Constructor
     * Inicializa los atributos y agrega los listeners correspondientes
     */
    public InicioGUI() {
        BotonNuevoConteo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CellCountGUI.main(null);
            }
        });
        BotonConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Consulta No implementada");
            }
        });
    }

    /**
     * Método principal
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CHIM");
        frame.setContentPane(new InicioGUI().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
