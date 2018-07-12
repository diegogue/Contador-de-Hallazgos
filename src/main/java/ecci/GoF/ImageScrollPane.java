package ecci.GoF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageScrollPane extends JScrollPane {
    private ImagePanel imagePane;

    public ImageScrollPane() {
        super();
        this.imagePane = new ImagePanel();
        setViewportView(this.imagePane);

        InputListener inListener = new InputListener();
        addMouseListener(inListener);
        //addMouseMotionListener(mouseAdapter);
        //addKeyListener(inListener);
        addMouseWheelListener(inListener);
    }

    public ImagePanel getImagePane() {
        return imagePane;
    }

    public void setImage(File image) {
        imagePane.setImage(image);
        revalidate();
        repaint();
    }

    public void setZoomLevel(int zoom) {
        imagePane.setZoomLevel(zoom);
        revalidate();
        repaint();
    }

    public void addPoint(Point p) {
        int scrollX = getHorizontalScrollBar().getValue();
        int scrollY = getVerticalScrollBar().getValue();
        Point imagePoint = new Point(p.x + scrollX, p.y + scrollY);
        this.imagePane.addPoint(imagePoint);
        repaint();
    }

    public BufferedImage getSubimage(int x, int y, int width, int height) {
        int xPoint = x + (width/2) + getHorizontalScrollBar().getValue();
        int yPoint = y + (height/2) + getVerticalScrollBar().getValue();
        return this.imagePane.getSubimage(xPoint, yPoint, width, height);
    }

    private class InputListener implements MouseListener, MouseWheelListener {

        @Override
        public void mouseClicked(MouseEvent e) { }
        @Override
        public void mousePressed(MouseEvent e) {
            Dimension imageDimension = imagePane.getPreferredSize();
            if (e.getY() < imageDimension.height && e.getX() < imageDimension.width) {
                addPoint(e.getPoint());
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (imagePane == null) return;
            if (e.isControlDown()) {
                imagePane.addZoomLevel(-e.getWheelRotation() * 2);
                revalidate();
                repaint();
            }
        }
    }
}

