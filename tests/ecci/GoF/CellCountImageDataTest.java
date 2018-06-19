package ecci.GoF;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellCountImageDataTest {
    CellCountImageData imageData = new CellCountImageData();


    @Test
    public void testGetPoint() {
        imageData.init();
        assertEquals("Inicializacion de puntos incorrecta", 0, imageData.getPointCount());
    }


    @Test
    public void testInitializePoints() {
        imageData.init();
        assertNotNull("Método InitializePoints no se ejecutó correctamente", imageData.getPoints());
    }

    @Test
    public void testInitializeColors() {
        imageData.init();
        assertNotNull("Método InitializeColors no se ejecutó correctamente", imageData.getColors());
    }

    @Test
    public void testSetImage() {

    }
}

