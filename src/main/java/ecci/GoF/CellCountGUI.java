package ecci.GoF;

import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.io.Opener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Interfaz principal del programa contador de celulas.
 */
public class CellCountGUI {
    private JPanel mainPanel;
    private JPanel counterPanel;
    private JPanel actionsPanel;
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
    private JPanel databasePane;
    private JButton cambiarColorButton;

    private JLabel selectedLabel;
    private JCheckBox selectedBox;
    private CellCountImageCanvas imageCanvas;
    private CellCountImageData data;

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
        queryButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"No implementado",
                    "No implementado", JOptionPane.INFORMATION_MESSAGE);
        });
        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"No implementado",
                    "No implementado", JOptionPane.INFORMATION_MESSAGE);
        });
        box0.addActionListener(e -> selectLabel(0));
        box1.addActionListener(e -> selectLabel(1));
        box2.addActionListener(e -> selectLabel(2));
        box3.addActionListener(e -> selectLabel(3));
        box4.addActionListener(e -> selectLabel(4));
        selectedLabel = type0;
        selectedBox = box0;
        data = new CellCountImageData();
    }

    /**
     * Metodo principal de CellCountGUI.
     * @param args no utilizado
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CHIM");
        frame.setContentPane(new CellCountGUI().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
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
            new ImageWindow(image, imageCanvas);
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
}
