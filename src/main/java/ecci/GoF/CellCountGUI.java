package ecci.GoF;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Interfaz principal del programa contador de celulas.
 */
public class CellCountGUI {
    private JPanel mainPanel;
    private JPanel formas;
    private ImageScrollPane imagePane;
    private JScrollPane scrollCount;
    private Box countersPane;

    private JFileChooser chooser;

    private ArrayList<CellCounter> counters;
    private ButtonGroup cellCountGroup;

    private static JFrame frame;

    /**
     * Constructor de CellCountGUI.
     * Inicializa los atributos y agrega los listeners correspondientes
     */
    public CellCountGUI() {

        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imagenes (.jpg, .jpeg, .gif, .png)",
                "jpg", "jpeg", "gif", "png"
        );
        chooser.setFileFilter(filter);
        createMenu();
        countersPane = new Box(BoxLayout.Y_AXIS);
        cellCountGroup = new ButtonGroup();
        scrollCount.setViewportView(countersPane);
        addCellCounter();
        addCellCounter();
        addCellCounter();
        addCellCounter();
    }

    /**
     * Metodo principal de CellCountGUI.
     * @param args no utilizado
     */
    public static void main(String[] args) {
        frame = new JFrame("CHIM");
        frame.setContentPane(new CellCountGUI().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setSize(800, 600);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);
    }

    private void addCellCounter() {
        CellCounter newCount = new CellCounter();
        cellCountGroup.add(newCount.radio);
        countersPane.add(newCount);
        counters.add(newCount);
    }

    /**
     * Inicializa el contador y abre la imagen
     */
    private void initializeImage() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //imageCanvas.addObserver(this);
        int imageWidth = imagePane.getWidth();
        int imageHeight = imagePane.getHeight();

        /* Numero magico, valor sensible */
        int newHeight = imageHeight+110;
        if (newHeight < 500) {
            newHeight = 500;
        }
        if (newHeight > (int)dimension.getHeight()) {
            newHeight = (int)dimension.getHeight()-100;
        }
        if (imageWidth > (int)dimension.getWidth()) {
            imageWidth = (int)dimension.getWidth();
        }

        frame.setSize(imageWidth + 210, newHeight);

        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Archivo");
        JMenuItem open = new JMenuItem("Abrir Imagen");
        open.addActionListener(e -> {
            int chooseOption = chooser.showOpenDialog(frame);
            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                imagePane.setImage(chooser.getSelectedFile());
                initializeImage();
            }
        });
        JMenuItem save = new JMenuItem("Guardar");
        save.addActionListener(e -> agregar());
        file.add(open);
        file.add(save);
        menuBar.add(file);
        frame.setJMenuBar(menuBar);
    }

    private void colorBoxes(){
    }

    public void update() {
    }

    public void setImageCounter(Image image){
    }

    public void agregar() {

        /*Variables para las ventanas de agregación*/
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        JTextField campoUsuario = new JTextField(5);
        JTextField campoProyecto = new JTextField(5);
        JTextField campoDescripcion = new JTextField(10);

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

            String usuario = campoUsuario.getText();
            String nombreProyecto = campoProyecto.getText();
            String descripcion = campoDescripcion.getText();

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
                            "\n    Usuario:                           " + usuario +
                            "\n    Descripción:                   " +
                            descripcion + "\n          Hallazgos:",
                    "Conteo Guardado", JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    private class CellCounter extends JPanel {
        public JRadioButton radio;
        public JLabel image;
        public JLabel textCount;

        public CellCounter() {
            super(new GridLayout(1, 3));
            this.radio = new JRadioButton();
            this.image = new JLabel(new ImageIcon(
                    new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB)));
            this.textCount = new JLabel("0");
            super.add(radio);
            super.add(image);
            super.add(textCount);
            validate();
        }
    }
}
