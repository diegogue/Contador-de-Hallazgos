package ecci.GoF;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.io.Opener;

import javax.swing.*;
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
    private ImageCanvas imageCounter;
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
    //private JTextField nameChangeField;
    private JButton saveButton;
    private JButton queryButton;
    //private JButton cambiarColorButton;
    private JPanel testPane;
    private JButton editarFormaButton;
    private JButton cerrarButton;
    private JPanel icPanel0;
    private JPanel icPanel1;
    private JPanel icPanel2;
    private JPanel icPanel3;
    private JPanel icPanel4;
    private JPanel countersPane;
    private JPanel counterPanel;
    private JScrollPane scPane;

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

    //Contadores para mostrar al final
    int conteo0=0;
    int conteo1=0;
    int conteo2=0;
    int conteo3=0;
    int conteo4=0;

    //Nombres de las formas para mostrar al final
    String f1 = "Forma 1";
    String f2 = "Forma 2";
    String f3 = "Forma 3";
    String f4 = "Forma 4";
    String f5 = "Forma 5";



    /**
     * Constructor de CellCountGUI.
     * Inicializa los atributos y agrega los listeners correspondientes
     */
    public CellCountGUI() {
        box0.addActionListener(e -> selectLabel(0));
        box1.addActionListener(e -> selectLabel(1));
        box2.addActionListener(e -> selectLabel(2));
        box3.addActionListener(e -> selectLabel(3));
        box4.addActionListener(e -> selectLabel(4));
        selectedLabel = type0;
        selectedBox = box0;
        data = new CellCountImageData();
        editarFormaButton.addActionListener(e -> EditWindow.openEdit(data, selectedBox, imageCanvas));
        createMenu();
    }

    /**
     * Metodo principal de CellCountGUI.
     * @param args no utilizado
     */
    public static void main(String[] args) {

        frame = new JFrame("CHIM");
        frame.setContentPane(new CellCountGUI().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        frame.setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("Archivo");

        JMenuItem open = new JMenuItem("Abrir Imagen");
        open.addActionListener(e -> {
            Opener opener = new Opener();
            ImagePlus image = opener.openImage("");
            initializeImage(image);
            data.init();
            data.setImage(image);
            colorBoxes();
        });
        JMenuItem save = new JMenuItem("Guardar");
        save.addActionListener(e -> agregar());
        menuBar.add(file);
        file.add(open);
        file.add(save);

        frame.setJMenuBar(menuBar);
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
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x;
        int y;
        if (image != null) {
            imageCanvas = new CellCountImageCanvas(image, data);
            imageCanvas.addObserver(this);
            //new ImageWindow(image, imageCanvas);
            int imageWidth = image.getProcessor().getWidth();
            int imageHeight = image.getProcessor().getHeight();
            /* Numero magico, valor sensible */
            int newHeight = imageHeight+160;
            if (newHeight < 600) {
                newHeight = 600;
            }
            if (imageWidth < 400) {
                imageWidth = 400;
            }
            if (newHeight > (int)dimension.getHeight()) {
                newHeight = (int)dimension.getHeight()-100;
            }
            if (imageWidth > (int)dimension.getWidth()) {
                imageWidth = (int)dimension.getWidth();
            }

            frame.setSize(imageWidth + 260, newHeight);
            testPane.removeAll();
            testPane.add(imageCanvas);

            x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
            y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);


            frame.setLocation(x, y);
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

    private void colorBoxes(){
        int i = 0;
        box0.setBackground(data.getColors().get(i));
        i++;
        box1.setBackground(data.getColors().get(i));
        i++;
        box2.setBackground(data.getColors().get(i));
        i++;
        box3.setBackground(data.getColors().get(i));
        i++;
        box4.setBackground(data.getColors().get(i));
        i++;
    }

    public void update() {
        Integer count = data.getPointCount();
        selectedLabel.setText(count.toString());
        //Aumenta contadores para mostrar
        if (selectedBox == box0){
            conteo0++;
            f1 = selectedBox.getText();
        }
        if (selectedBox == box1){
            conteo1++;
            f2 = selectedBox.getText();
        }
        if (selectedBox == box2){
            conteo2++;
            f3 = selectedBox.getText();
        }
        if (selectedBox == box3){
            conteo3++;
            f4 = selectedBox.getText();
        }
        if (selectedBox == box4){
            conteo4++;
            f5 = selectedBox.getText();
        }

    }

    public void setImageCounter(ImagePlus imagePlus){
        imageCounter = new ImageCanvas(imagePlus);
        if (selectedBox == box0){
            icPanel0.removeAll();
            icPanel0.setLayout(new BoxLayout(icPanel0, BoxLayout.PAGE_AXIS));
            icPanel0.add(imageCounter);
            icPanel0.setSize(imageCounter.getWidth(),imageCounter.getHeight());
        }
        if (selectedBox == box1){
            icPanel1.removeAll();
            icPanel1.setLayout(new BoxLayout(icPanel1, BoxLayout.PAGE_AXIS));
            icPanel1.add(imageCounter);
            icPanel1.setSize(imageCounter.getWidth(),imageCounter.getHeight());
        }
        if (selectedBox == box2){
            icPanel2.removeAll();
            icPanel2.setLayout(new BoxLayout(icPanel2, BoxLayout.PAGE_AXIS));
            icPanel2.add(imageCounter);
            icPanel2.setSize(imageCounter.getWidth(),imageCounter.getHeight());
        }
        if (selectedBox == box3){
            icPanel3.removeAll();
            icPanel3.setLayout(new BoxLayout(icPanel3, BoxLayout.PAGE_AXIS));
            icPanel3.add(imageCounter);
            icPanel3.setSize(imageCounter.getWidth(),imageCounter.getHeight());
        }
        if (selectedBox == box4){
            icPanel4.removeAll();
            icPanel4.setLayout(new BoxLayout(icPanel4, BoxLayout.PAGE_AXIS));
            icPanel4.add(imageCounter);
            icPanel4.setSize(imageCounter.getWidth(),imageCounter.getHeight());
        }


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
                            descripcion + "\n          Hallazgos:"
                            + "\n             " + f1 + ":         " + conteo0
                            + "\n             " + f2 + ":         " + conteo1
                            + "\n             " + f3 + ":         " + conteo2
                            + "\n             " + f4 + ":         " + conteo3
                            + "\n             " + f5 + ":         " + conteo4 , "Conteo Guardado", JOptionPane.PLAIN_MESSAGE, null);
        }
    }
}
