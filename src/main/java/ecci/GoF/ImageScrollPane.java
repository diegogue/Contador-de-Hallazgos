package ecci.GoF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ImageScrollPane extends JScrollPane {
    private ImagePanel imagePane;

    public ImageScrollPane() {
        super();
        this.imagePane = new ImagePanel();
        setViewportView(this.imagePane);
        /* NOTE: numero magico */
        getVerticalScrollBar().setUnitIncrement(20);
        getHorizontalScrollBar().setUnitIncrement(20);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                addPoint(e.getPoint());
            }

        };
        addMouseListener(mouseAdapter);
        //addMouseMotionListener(mouseAdapter);
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
}
