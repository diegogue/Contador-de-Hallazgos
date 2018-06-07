package ecci.GoF;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.gui.Wand;

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
    private ArrayList<ArrayList<Wand>> wands;
    //private CustomCanvas imageCanvas;
    private ImageWindow imageWindow;
    private ArrayList<Point> selectedPointArray;
    private int colorIndex;

    /**
     * Constructor de CellCountImageCanvas
     * @param imp imagen a mostrar
     */
    CellCountImageCanvas(ImagePlus imp) {
        initializePoints();
        initializeColors();
        initializeWands();
        setImage(imp);
    }

    /**
     * Constructor de CellCountImageCanvas
     */
    CellCountImageCanvas() {
        initializePoints();
        initializeColors();
        initializeWands();
    }

    /**
     * Metodo auxiliar para inicializar puntos
     */
    private void initializePoints() {
        points = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            points.add(new ArrayList<>());
        }
        selectedPointArray = points.get(0);
    }

    /**
     * Metodo auxiliar para inicializar colores
     */
    private void initializeColors() {
        colors = new ArrayList<>();
        colors.add(Color.MAGENTA);
        colors.add(Color.GREEN);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        colors.add(Color.RED);
    }

    private void initializeWands() {
        wands = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            wands.add(new ArrayList<>());
        }
    }

    /**
     * Agrega imaen a la ventana
     * @param imp imagen para mostrar
     */
    void setImage(ImagePlus imp) {
        if (imageWindow != null) {
            imageWindow.close();
        }
        CustomCanvas imageCanvas = new CustomCanvas(imp);
        imageWindow = new ImageWindow(imp, imageCanvas);
    }

    /**
     * Retorna la cantidad de puntos guardados
     * @return la cantidad de puntos guardados
     */
    int getPointCount() {
        return selectedPointArray.size();
    }

    /**
     * Selecciona el n-esimo tipo
     * @param n indice del tipo
     */
    void selectType(int n) {
        selectedPointArray = points.get(n);
        colorIndex = n;
    }

    /**
     * Cambia el color del tipo actual
     * @param color color elegido
     */
    void setColor(Color color) {
        colors.set(colorIndex, color);
    }

    /**
     * Fuerza una actualizacion del canvas
     */
    void repaint() {
        if (imageWindow != null) {
            imageWindow.getCanvas().repaint();
        }
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
                ArrayList<Wand> wandArray = wands.get(i);
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
                selectedPointArray.add(mousePoint);
                //System.out.println(mousePoint);
                Wand newWand = new Wand(imageWindow.getImagePlus().getProcessor());
                newWand.autoOutline(mousePoint.x, mousePoint.y);
                wands.get(colorIndex).add(newWand);
            } else if (button == MouseEvent.BUTTON3) {
                int arraySize  = selectedPointArray.size();
                if (arraySize > 0) {
                    selectedPointArray.remove(arraySize - 1);
                }
                repaint();
            }
            setChanged();
            notifyObservers();
        }
    }
}
