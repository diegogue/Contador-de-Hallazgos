package ecci.GoF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel() {
        this.image = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }

    @Override
    public Dimension getPreferredSize() {
        if (image == null) return new Dimension(0, 0);
        return new Dimension(this.image.getWidth(), this.image.getHeight());
    }

    public void addImage(File imageFile) {
        try {
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
