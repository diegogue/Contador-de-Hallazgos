package ecci.GoF;

import java.awt.*;
import java.util.ArrayList;

public class CellCountImageData {

    private ArrayList<ArrayList<Point>> points;
    private ArrayList<Color> colors;
    private int typeIndex;

    public CellCountImageData() {
        init();
    }

    public void init() {
        initializePoints();
        initializeColors();
    }

    /**
     * Metodo auxiliar para inicializar puntos
     */
    private void initializePoints() {
        points = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            points.add(new ArrayList<>());
        }
        typeIndex = 0;
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

    public int getPointCount() {
        return points.get(typeIndex).size();
    }

    public ArrayList<ArrayList<Point>> getPoints() {
        return points;
    }

    public ArrayList<Point> getSelectedPoints() {
        return points.get(typeIndex);
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setType(int n) {
        typeIndex = n;
    }

    public void setColor(Color color) {
        colors.set(typeIndex, color);
    }
}
