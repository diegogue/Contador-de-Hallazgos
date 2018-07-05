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
            //ArrayList<BlobDetector> blobArray = data.getBlobs().get(i);
            Integer j = 0;
            for (Point point : pointArray) {
                g.fillOval(point.x  - 2, point.y - 2, 4, 4);

                char[] label = {(char)((int)'1' + i)};
                g.drawChars(label, 0, 1, point.x - 2, point.y - 2);
                /*
                BlobDetector blob = blobArray.get(j);
                for (int k = 0; k < blob.npoints; ++k) {
                    g.fillOval(blob.xpoints[k] - 2, blob.ypoints[k] - 2, 4, 4);
                }*/
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
            //Wand newWand = new Wand(imp.getProcessor());
            //newWand.autoOutline(mousePoint.x, mousePoint.y);
          //  data.addBlob(mousePoint);
        } else if (button == MouseEvent.BUTTON3) {
            /*
            int arraySize = points.size();
            if (arraySize > 0) {
                points.remove(arraySize - 1);
            }
            */
            repaint();
            //data.setBlobReference(event.getPoint());
        }
        repaint();
        //notifyObserver();
    }


}
