package ecci.GoF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    public ImageData data;
    private BufferedImage image;
    private BufferedImage scaledImage;
    private float zoom;
    private int scaledWidth;
    private int scaledHeight;

    public ImagePanel() {
        this.image = null;
        this.data = new ImageData();
        this.zoom = .5f;
    }

    public void addImage(File imageFile) {
        try {
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.scaledWidth = (int) (this.image.getWidth() * zoom);
        this.scaledHeight = (int) (this.image.getHeight() * zoom);
    }

    public void setZoomLevel(int zoomLevel) {
        // normalize from int percentage
        this.zoom = zoomLevel / 100f;
        this.scaledWidth = (int) (this.image.getWidth() * zoom);
        this.scaledHeight = (int) (this.image.getHeight() * zoom);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, scaledWidth, scaledHeight, this);
        g.setColor(Color.MAGENTA);
        for (Point p : data.points) {
            g.fillOval(p.x - 4, p.y - 4, 8, 8);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (image == null) return new Dimension(0, 0);
        return new Dimension(scaledWidth, scaledHeight);
    }

}
