package ecci.GoF;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Clase auxiliar para el uso de ImageCanvas con metodos modificados
 */
class CellCountImageCanvas extends Observable {
    private ArrayList<ArrayList<Point>> points;
    private ArrayList<Color> colors;
    //private CustomCanvas imageCanvas;
    private ImageWindow imageWindow;
    private int selectedPointArray;

    /**
     * Constructor de CellCountImageCanvas
     * @param imp imagen a mostrar
     */
    CellCountImageCanvas(ImagePlus imp) {
        CustomCanvas imageCanvas = new CustomCanvas(imp);
        points = new ArrayList<>();
        points.add(new ArrayList<>());
        points.add(new ArrayList<>());
        points.add(new ArrayList<>());
        points.add(new ArrayList<>());
        points.add(new ArrayList<>());
        colors = new ArrayList<>();
        colors.add(Color.MAGENTA);
        colors.add(Color.CYAN);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        imageWindow = new ImageWindow(imp, imageCanvas);
    }

    /**
     * Retorna la cantidad de puntos guardados
     * @return la cantidad de puntos guardados
     */
    int getPointCount() {
        return points.get(selectedPointArray).size();
    }

    /**
     * Selecciona el n-esimo tipo
     * @param n indice del tipo
     */
    void selectType(int n) {
        selectedPointArray = n;
    }

    /**
     *  Cierra la ventana
     */
    void close() {
        imageWindow.close();
    }

    /**
     *  Clase que extiende la funcionalidad de ImageCanvas
     */
    private class CustomCanvas extends ImageCanvas {
        /**
         * Constructor de CustomCanvas
         * @param imp imagen a mostrar
         */
        CustomCanvas(ImagePlus imp) {
            super(imp);
        }

        /**
         * Muestra la imagen y los puntos en pantalla
         * @param g Objecto de manipulacion grafica
         */
        public void paint(Graphics g) {
            super.paint(g);
            Integer i = 0;
            for (ArrayList<Point> pointArray : points) {
                g.setColor(colors.get(i));
                for (Point point : pointArray) {
                    g.fillOval(point.x  - 2, point.y - 2, 4, 4);
                    char[] label = {(char)((int)'1' + i)};
                    g.drawChars(label, 0, 1, point.x - 2, point.y - 2);
                }
                ++i;
            }
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
            if (button == MouseEvent.BUTTON1) {
                Point mousePoint = event.getPoint();
                points.get(selectedPointArray).add(mousePoint);
                System.out.println(mousePoint);
            } else if (button == MouseEvent.BUTTON3) {
                ArrayList<Point> pointArray = points.get(selectedPointArray);
                if (pointArray.size() > 0) {
                    pointArray.remove(pointArray.size() - 1);
                }
            }
            repaint();
            setChanged();
            notifyObservers();
        }
    }
}
