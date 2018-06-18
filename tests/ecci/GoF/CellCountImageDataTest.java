package ecci.GoF;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellCountImageDataTest {
    CellCountImageData imageData = new CellCountImageData();

    @Test
    public void init() {
        assertEquals("Inicializacion de puntos incorrecta", 0, imageData.getPointCount());
    }
}