package ecci.GoF;

import ij.ImagePlus;
import ij.gui.Wand;
import ij.plugin.ContrastEnhancer;
import ij.plugin.filter.GaussianBlur;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

import java.awt.*;
import java.util.ArrayList;

public class CellCountImageData {
    private ArrayList<ArrayList<Point>> points;
    private ArrayList<Color> colors;
    private ArrayList<ArrayList<Wand>> wands;
    private int typeIndex;
    private BlobDetector blob;

    public CellCountImageData() {
        init();
        blob = null;
    }

    public void init() {
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

    private void initializeWands() {
        wands = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            wands.add(new ArrayList<>());
        }
    }

    public void addBlob(Point startPoint) {
        blob.computeBlob(startPoint);
    }

    public void setImage(ImagePlus imp) {
        ByteProcessor byteImage = (ByteProcessor) imp.getProcessor().convertToByte(true);
        GaussianBlur blur = new GaussianBlur();
        blur.blurGaussian(byteImage, 10, 10, 2);
        ContrastEnhancer equalizer = new ContrastEnhancer();
        equalizer.equalize(byteImage);
        blob = new BlobDetector(8);
        blob.setImage((ByteProcessor) imp.getProcessor().convertToByte(true));
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

    public ArrayList<Wand> getSelectedWands() {
        return wands.get(typeIndex);
    }

    public BlobDetector getBlob() {
        return blob;
    }

    public void setColor(Color color) {
        colors.set(typeIndex, color);
    }

    public ArrayList<ArrayList<Wand>> getWands() {
        return wands;
    }
}
