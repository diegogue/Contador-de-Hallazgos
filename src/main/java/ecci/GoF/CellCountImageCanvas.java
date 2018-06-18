package ecci.GoF;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Wand;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
            ArrayList<Wand> wandArray = data.getWands().get(i);
            Integer j = 0;
            for (Point point : pointArray) {
                g.fillOval(point.x  - 2, point.y - 2, 4, 4);
                Wand wand = wandArray.get(j);
                g.drawPolygon(wand.xpoints, wand.ypoints, wand.npoints);
                char[] label = {(char)((int)'1' + i)};
                g.drawChars(label, 0, 1, point.x - 2, point.y - 2);
                ++j;
            }
            ++i;
        }
        BlobDetector blob = data.getBlob();
        if (blob != null) {
            for (int j = 0; j < blob.npoints; ++j) {
                g.fillOval(blob.xpoints[j] - 2, blob.ypoints[j] - 2, 4, 4);
            }
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
            //System.out.println(mousePoint);
            Wand newWand = new Wand(imp.getProcessor());
            newWand.autoOutline(mousePoint.x, mousePoint.y);
            data.getSelectedWands().add(newWand);
            data.addBlob(mousePoint);
        } else if (button == MouseEvent.BUTTON3) {
            /*
            int arraySize = points.size();
            if (arraySize > 0) {
                points.remove(arraySize - 1);
            }
            repaint();
            */
            data.getBlob().setBackgroundReference(event.getPoint());
        }
        repaint();
        notifyObserver();
    }
}
