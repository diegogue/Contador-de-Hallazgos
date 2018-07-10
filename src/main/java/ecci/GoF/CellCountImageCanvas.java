package ecci.GoF;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
import ij.gui.Wand;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase auxiliar para el uso de ImageCanvas con metodos modificados
 */
public class CellCountImageCanvas extends ImageCanvas {
    private CellCountImageData data;
    private CellCountGUI observer;
    private int iX;
    private int iY;

    /**
     * Constructor de CustomCanvas
     * @param imp imagen a mostrar
     */
    CellCountImageCanvas (ImagePlus imp, CellCountImageData data) {
        super(imp);
        this.data = data;
        iX = 0;
        iY = 0;
    }

    CellCountImageCanvas(CellCountImageData data) {
        this(new ImagePlus(), data);
    }

    public void addObserver(CellCountGUI observer) {
        this.observer = observer;
    }

    public CellCountGUI getObserver() {
        return this.observer;
    }

    private void notifyObserver() {
        if (observer != null) {
            observer.update();
        }
    }

    private void notifyFrame(ImagePlus ip){
        if(ip != null){
            observer.setImageCounter(ip);
        }
    }

    /**
     * Muestra la imagen y los puntos en pantalla
     * @param g Objecto de manipulacion grafica
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Integer i = 0;
        for (ArrayList<Point> pointArray : data.getPoints()) {
            g.setColor(data.getColors().get(i));
            //ArrayList<BlobDetector> blobArray = data.getBlobs().get(i);
            Integer j = 0;
            for (Point point : pointArray) {
                g.fillOval(point.x  - 2, point.y - 2, 4, 4);

                char[] label = {(char)((int)'1' + i)};
                g.drawChars(label, 0, 1, point.x - 2, point.y - 2);

                //BlobDetector blob = blobArray.get(j);
                /*for (int k = 0; k < blob.npoints; ++k) {
                    g.fillOval(blob.xpoints[k] - 2, blob.ypoints[k] - 2, 4, 4);
                }*/
                ++j;
            }
            ++i;
        }
    }

    public void setImage(ImagePlus img) {
        super.imp = img;
    }

    /**
     * Listener del click el cursor.
     * Agrega punto de las coordenadas del mouse con respecto a el canvas.
     * Notifica a los observadores sobre un cambio.
     * @param event evento del mouse
     */
    @Override
    public void mousePressed(MouseEvent event) {
        iX =event.getX();
        iY =event.getY();
    }

    @Override
    public void mouseClicked(MouseEvent event){
        super.mousePressed(event);
        int button = event.getButton();
        List<Point> points = data.getSelectedPoints();
        if (button == MouseEvent.BUTTON1) {
            Point mousePoint = event.getPoint();
            points.add(mousePoint);
            Wand newWand = new Wand(imp.getProcessor());
            newWand.autoOutline(mousePoint.x, mousePoint.y);
            //data.addBlob(mousePoint);
        } else if (button == MouseEvent.BUTTON3) {

            int arraySize = points.size();
            if (arraySize > 0) {
                points.remove(arraySize - 1);
            }

            repaint();
            //data.setBlobReference(event.getPoint());
        }
        repaint();
        notifyObserver();
    }

    @Override
    public void mouseDragged(MouseEvent event){
        System.out.println("hola");
        super.mouseDragged(event);
        int x = event.getX();
        int y = event.getY();
        if(iX < x && iY < y){
            this.getImage().setRoi(iX, iY, x-iX, y-iY);//derecha-abajo
        } else if(iX > x && iY > y){
            this.getImage().setRoi(x, y, iX-x, iY-y);//izquierda-arriba
        } else if(iX < x && iY > y){
            this.getImage().setRoi(iX, y, x-iX, iY-y);//derecha-arriba
        } else{
            this.getImage().setRoi(x, iY, iX-x, y-iY);//izquierda-abajo
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent event){
        super.mouseReleased(event);
        this.getImage().deleteRoi();
        repaint();
        int fX = event.getX();
        int fY = event.getY();

        int size = 32;
        int minSize = 12;

        int width = size;
        int height = size;

        Roi roi = null;

        if(iX < fX && iY < fY){//derecha-abajo
            if(fX - iX < size){
                width = fX - iX;
            }
            if(fY - iY < size){
                height = fY - iY;
            }
            if(height > minSize && width > minSize){
                this.getImage().setRoi(iX, iY, width, height);
                roi = this.getImage().getRoi();
            }
        } else if(iX > fX && iY > fY){//izquierda-arriba
            if(iX -fX < size){
                width = iX -fX;
            }
            if(iY -fY < size){
                height = iY -fY;
            }
            if(height > minSize && width > minSize){
                this.getImage().setRoi(fX, fY, width, height);
                roi = this.getImage().getRoi();
            }
            this.getImage().setRoi(fX, fY, width, height);
        } else if(iX < fX && iY > fY){//derecha-arriba
            if(fX- iX < size){
                width = iX -fX;
            }
            if(iY -fY < size){
                height = iY -fY;
            }
            if(height > minSize && width > minSize){
                this.getImage().setRoi(iX, fY, width, height);
                roi = this.getImage().getRoi();
            }
        } else{//izquierda-abajo
            if(iX -fX < size){
                width = iX -fX;
            }
            if(fY- iY < size){
                height = iY -fY;
            }
            if(height > minSize && width > minSize){
                this.getImage().setRoi(fX, iY, width, height);
                roi = this.getImage().getRoi();
            }
        }
        ImagePlus imageCropped = null;
        if(roi != null){
            imageCropped = this.getImage().duplicate();
        }
        notifyFrame(imageCropped);
        this.getImage().deleteRoi();
    }

}
