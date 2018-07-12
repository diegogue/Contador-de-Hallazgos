package ecci.GoF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditWindow {
    private JTextField textFieldName;
    private JButton buttonName;
    private JButton colorButton;
    private JButton closeButton;
    private JPanel mainPane;
    private JPanel colorPane;
    private static JCheckBox box;
    private static JFrame frame;
    private static CellCountImageData data;
    private static CellCountImageCanvas imageCanvas;

    public EditWindow() {
        buttonName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameForm = textFieldName.getText();
                box.setText(nameForm);
                frame.setTitle(nameForm);
            }
        });

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "", Color.WHITE);
                if (newColor != null) {
                    data.setColor(newColor);
                    box.setBackground(newColor);
                    colorPane.setBackground(newColor);
                    if (imageCanvas != null) {
                        imageCanvas.repaint();
                    }
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    /**
     * Método principal
     */
    public static void openEdit(CellCountImageData imgData,JCheckBox sBox, CellCountImageCanvas canvas) {
        EditWindow editW = new EditWindow();
        box = sBox;
        data = imgData;
        imageCanvas = canvas;
        frame = new JFrame(box.getText());
        frame.setContentPane(new EditWindow().mainPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        frame.setVisible(true);

    }


}

