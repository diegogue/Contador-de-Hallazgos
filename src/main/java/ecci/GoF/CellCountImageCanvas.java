package ecci.GoF;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Wand;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JComboBox;

/**
 * Clase auxiliar para el uso de ImageCanvas con metodos modificados
 */
public class CellCountImageCanvas extends ImageCanvas {
    private CellCountImageData data;
    private CellCountGUI observer;

    /*Variables para las ventanas de agregación*/
    private JFrame frame;
    private JPanel pane;
    private JTextField campoUsuario;
    private JTextField campoProyecto;
    private JTextField campoDescripcion;
    private String usuario = "";
    private String nombreProyecto = "";
    private String descripcion = "";

    /**
     * Constructor de CustomCanvas
     * @param imp imagen a mostrar
     */
    CellCountImageCanvas (ImagePlus imp, CellCountImageData data) {
        super(imp);
        this.data = data;
    }

    CellCountImageCanvas(CellCountImageData data) {
        this(new ImagePlus(), data);
    }

    public void addObserver(CellCountGUI observer) {
        this.observer = observer;
    }

    public CellCountGUI getObserver() {
        return this.observer;
    }

    private void notifyObserver() {
        if (observer != null) {
            observer.update();
        }
    }

    /**
     * Muestra la imagen y los puntos en pantalla
     * @param g Objecto de manipulacion grafica
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Integer i = 0;
        for (ArrayList<Point> pointArray : data.getPoints()) {
            g.setColor(data.getColors().get(i));
            ArrayList<BlobDetector> blobArray = data.getBlobs().get(i);
            Integer j = 0;
            for (Point point : pointArray) {
                g.fillOval(point.x  - 2, point.y - 2, 4, 4);

                char[] label = {(char)((int)'1' + i)};
                g.drawChars(label, 0, 1, point.x - 2, point.y - 2);

                BlobDetector blob = blobArray.get(j);
                for (int k = 0; k < blob.npoints; ++k) {
                    g.fillOval(blob.xpoints[k] - 2, blob.ypoints[k] - 2, 4, 4);
                }
                ++j;
            }
            ++i;
        }
    }

    public void setImage(ImagePlus img) {
        super.imp = img;
    }

    /**
     * Listener del click el cursor.
     * Agrega punto de las coordenadas del mouse con respecto a el canvas.
     * Notifica a los observadores sobre un cambio.
     * @param event evento del mouse
     */
    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);
        int button = event.getButton();
        List<Point> points = data.getSelectedPoints();
        if (button == MouseEvent.BUTTON1) {
            Point mousePoint = event.getPoint();
            points.add(mousePoint);
            Wand newWand = new Wand(imp.getProcessor());
            newWand.autoOutline(mousePoint.x, mousePoint.y);
            data.addBlob(mousePoint);
        } else if (button == MouseEvent.BUTTON3) {
            /*
            int arraySize = points.size();
            if (arraySize > 0) {
                points.remove(arraySize - 1);
            }
            */
            repaint();
            data.setBlobReference(event.getPoint());
        }
        repaint();
        notifyObserver();
    }

    public void agregar() {
        pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        campoUsuario = new JTextField(5);
        campoProyecto = new JTextField(5);
        campoDescripcion = new JTextField(10);

        pane.add(new JLabel("Ingrese los datos del conteo"));
        pane.add(new JLabel(""));
        pane.add(new JLabel(""));
        pane.add(new JLabel(""));
        pane.add(new JLabel("Nombre de Usuario"));
        pane.add(campoUsuario);

        pane.add(new JLabel("Nombre del Proyecto"));
        pane.add(campoProyecto);

        pane.add(new JLabel("Descripción del Conteo"));
        pane.add(campoDescripcion);

        Object[] options = {"Agregar",
                "Cancelar"};

        int option = JOptionPane.showOptionDialog(frame, pane, "Guardar Conteo", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);

        if (option == JOptionPane.YES_OPTION) {

            usuario = campoUsuario.getText();
            nombreProyecto = campoProyecto.getText();
            descripcion = campoDescripcion.getText();

            pane = new JPanel();
            pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

            pane.add(new JLabel("Usuario:  " + usuario));
            pane.add(new JLabel("Proyecto:  " + nombreProyecto));
            pane.add(new JLabel("Descripción:  " + descripcion));

            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            JOptionPane.showMessageDialog(pane,

                    "El conteo se ha guardado correctamente \n\n" +
                            "    Nombre del Proyecto:  " + nombreProyecto +
                            "\n    Fecha y Hora:                 " + dateFormat.format(date) +
                            "\n    Usuario:                           " + usuario  +
                            "\n    Descripción:                   " +
                            descripcion + "\n          Hallazgos:" + "\n"
                            + "             Figura1:" + "         " + "10" + "\n" + "             Figura2:" + "         " + "10"
                            + "\n             Figura3:" + "         " + "10" , "Conteo Guardado", JOptionPane.PLAIN_MESSAGE, null);
        }
    }
}
