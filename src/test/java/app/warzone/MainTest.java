package app.warzone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testCalcSum() {
        assertEquals(6+3,Main.calcSum(6,3));
    }
}