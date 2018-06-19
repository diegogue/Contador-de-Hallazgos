package ecci.GoF;

import ij.ImagePlus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Observer;

import static org.junit.Assert.*;

public class CellCountImageCanvasTest {
    private CellCountImageData data;
    ImagePlus ip = new ImagePlus();

    CellCountImageCanvas imageCanvas = new CellCountImageCanvas(ip,data);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddObserver() {
        CellCountGUI observer = new CellCountGUI();
        imageCanvas.addObserver(observer);
        assertNotNull("Método addObserver no funcionó correctamente", imageCanvas.getObserver());
    }

    @Test
    public void testPaint() {
    }

    @Test
    public void testSetImage() {
        CellCountImageCanvas iCanvas = new CellCountImageCanvas(ip,data);
        ImagePlus image = new ImagePlus();
        iCanvas.setImage(image);
        assertNotNull("Método addObserver no funcionó correctamente", iCanvas.getImage());
    }

    @Test
    public void testMousePressed() {

    }
}