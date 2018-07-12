package ecci.GoF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private ImageData data;
    private BufferedImage image;
    private float zoom;
    private int scaledWidth;
    private int scaledHeight;

    public ImagePanel() {
        this.image = null;
        this.data = new ImageData();
        this.zoom = .5f;
    }

    public void setImage(File imageFile) {
        try {
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.scaledWidth = (int) (this.image.getWidth() * zoom);
        this.scaledHeight = (int) (this.image.getHeight() * zoom);
        //this.repaint();
    }

    public void setZoomLevel(int zoomLevel) {
        // normalize from int percentage
        this.zoom = zoomLevel / 100f;
        this.scaledWidth = (int) (this.image.getWidth() * zoom);
        this.scaledHeight = (int) (this.image.getHeight() * zoom);
        //this.repaint();
    }

    public void addPoint(Point p) {
        int scaledX = (int) (p.x / zoom);
        int scaledY = (int) (p.y / zoom);
        this.data.points.add(new Point(scaledX, scaledY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, scaledWidth, scaledHeight, this);
        g.setColor(Color.MAGENTA);
        for (Point p : data.points) {
            int scaledPointX = (int) ((p.x - 4) * zoom);
            int scaledPointY = (int) ((p.y - 4) * zoom);
            g.fillOval(scaledPointX, scaledPointY, 8, 8);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (image == null) return new Dimension(0, 0);
        return new Dimension(scaledWidth, scaledHeight);
    }

}
