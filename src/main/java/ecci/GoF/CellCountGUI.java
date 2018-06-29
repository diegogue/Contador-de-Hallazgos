package ecci.GoF;

import ij.ImagePlus;
import ij.io.Opener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Interfaz principal del programa contador de celulas.
 */
public class CellCountGUI {
    private JPanel mainPanel;
    private JPanel counterPanel;
    private JButton openButton;
    private JCheckBox box3;
    private JCheckBox box4;
    private JCheckBox box2;
    private JCheckBox box1;
    private JCheckBox box0;
    private JLabel type0;
    private JLabel type1;
    private JLabel type2;
    private JLabel type3;
    private JLabel type4;
    private JTextField nameChangeField;
    private JLabel nameChange;
    private JButton saveButton;
    private JButton queryButton;
    private JButton cambiarColorButton;
    private JPanel testPane;
    private JSlider slider1;

    private JLabel selectedLabel;
    private JCheckBox selectedBox;
    private CellCountImageCanvas imageCanvas;
    private CellCountImageData data;

    private static JFrame frame;

    /*Variables para las ventanas de agregación*/
    private JFrame frame1;
    private JPanel pane;
    private JTextField campoUsuario;
    private JTextField campoProyecto;
    private JTextField campoDescripcion;
    private String usuario = "";
    private String nombreProyecto = "";
    private String descripcion = "";

    /**
     * Constructor de CellCountGUI.
     * Inicializa los atributos y agrega los listeners correspondientes
     */
    public CellCountGUI() {
        openButton.addActionListener(e -> {
            Opener open = new Opener();
            ImagePlus image = open.openImage("");
            initializeImage(image);
            data.init();
            data.setImage(image);
        });
        nameChangeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectedBox.setText(nameChangeField.getText());
                }
            }
        });
        cambiarColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "", Color.WHITE);
            if (newColor != null) {
                data.setColor(newColor);
                selectedBox.setBackground(newColor);
                if (imageCanvas != null) {
                    imageCanvas.repaint();
                }
            }
        });
        //queryButton.addActionListener(e -> {
         //   JOptionPane.showMessageDialog(null,"No implementado",
         //           "No implementado", JOptionPane.INFORMATION_MESSAGE);
        //});
        saveButton.addActionListener(e -> {
            //JOptionPane.showMessageDialog(null,"No implementado",
              //      "No implementado", JOptionPane.INFORMATION_MESSAGE);
            agregar();
        });
        box0.addActionListener(e -> selectLabel(0));
        box1.addActionListener(e -> selectLabel(1));
        box2.addActionListener(e -> selectLabel(2));
        box3.addActionListener(e -> selectLabel(3));
        box4.addActionListener(e -> selectLabel(4));
        selectedLabel = type0;
        selectedBox = box0;
        data = new CellCountImageData();
        slider1.addChangeListener(e -> data.setTolerance(slider1.getValue()));
    }

    /**
     * Metodo principal de CellCountGUI.
     * @param args no utilizado
     */
    public static void main(String[] args) {
        frame = new JFrame("CHIM");
        frame.setContentPane(new CellCountGUI().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 500);
        frame.setLocationRelativeTo(null);
        frame.pack();
        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
        }
        catch (Exception  e) {
            System.err.println("Could not load LookAndFeel");
        }
        frame.setVisible(true);


    }

    /**
     * Inicializa el contador y abre la imagen
     */
    private void initializeImage(ImagePlus image) {
        type0.setText("0");
        type1.setText("0");
        type2.setText("0");
        type3.setText("0");
        type4.setText("0");
        if (image != null) {
            imageCanvas = new CellCountImageCanvas(image, data);
            imageCanvas.addObserver(this);
            //new ImageWindow(image, imageCanvas);
            int imageWidth = image.getProcessor().getWidth();
            int imageHeight = image.getProcessor().getHeight();
            /* Numero magico, valor sensible */
            int newHeight = imageHeight + 80;
            if (newHeight < 500) {
                newHeight = 500;
            }
            frame.setSize(imageWidth + 250, newHeight);
            frame.setLocationRelativeTo(null);
            testPane.removeAll();
            testPane.add(imageCanvas);
        }
    }

    /**
     * Cambia la variable seleect label por type-n
     * @param n indice de label
     */
    private void selectLabel(int n) {
        switch (n) {
            case 0:
                selectedLabel = type0;
                selectedBox = box0;
                break;
            case 1:
                selectedLabel = type1;
                selectedBox = box1;
                break;
            case 2:
                selectedLabel = type2;
                selectedBox = box2;
                break;
            case 3:
                selectedLabel = type3;
                selectedBox = box3;
                break;
            case 4:
                selectedLabel = type4;
                selectedBox = box4;
                break;
        }
        data.setType(n);
    }

    public void update() {
        Integer count = data.getPointCount();
        selectedLabel.setText(count.toString());
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

        int option = JOptionPane.showOptionDialog(frame1, pane, "Guardar Conteo", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);

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
