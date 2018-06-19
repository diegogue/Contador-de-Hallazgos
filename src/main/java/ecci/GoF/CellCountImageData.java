package ecci.GoF;

import ij.ImagePlus;
import ij.gui.Wand;
import ij.plugin.ContrastEnhancer;
import ij.plugin.filter.BackgroundSubtracter;
import ij.plugin.filter.GaussianBlur;
import ij.process.ByteProcessor;

import java.awt.*;
import java.util.ArrayList;

public class CellCountImageData {
    private ArrayList<ArrayList<Point>> points;
    private ArrayList<Color> colors;
    private ArrayList<ArrayList<BlobDetector>> blobs;
    private int typeIndex;
    private ByteProcessor byteImage;

    public CellCountImageData() {
        init();
    }

    public void init() {
        initializePoints();
        initializeColors();
        initializeBlobs();
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

    private void initializeBlobs() {
        blobs = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            blobs.add(new ArrayList<>());
            blobs.get(i).add(new BlobDetector(100));
        }
    }

    public void addBlob(Point startPoint) {
        ArrayList<BlobDetector> blobArray = blobs.get(typeIndex);
        blobArray.get(blobArray.size() - 1).computeBlob(startPoint);
        BlobDetector newBlob = new BlobDetector(100);
        blobArray.add(newBlob);
    }

    public void setImage(ImagePlus image) {
        ImagePlus imp = image.duplicate();
        byteImage = (ByteProcessor) imp.getProcessor().convertToByte(true);

        BackgroundSubtracter subtracter = new BackgroundSubtracter();
        subtracter.run(byteImage);

        ContrastEnhancer equalizer = new ContrastEnhancer();
        equalizer.equalize(byteImage);

        GaussianBlur blur = new GaussianBlur();
        blur.blurGaussian(byteImage, 32, 32, 8);

        ArrayList<BlobDetector> blobArray = blobs.get(typeIndex);
        blobArray.get(blobArray.size() - 1).setImage(byteImage);

        /* Debug image */
        //(new ImagePlus("8-bit wonder", byteImage)).show();
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

    public ArrayList<ArrayList<BlobDetector>> getBlobs() {
        return blobs;
    }

    public void setBlobReference(Point p) {
        ArrayList<BlobDetector> blobArray = blobs.get(typeIndex);
        blobArray.get(blobArray.size() - 1).setImage(byteImage);
        blobArray.get(blobArray.size() - 1).setBackgroundReference(p);
    }

    public void setColor(Color color) {
        colors.set(typeIndex, color);
    }
}
