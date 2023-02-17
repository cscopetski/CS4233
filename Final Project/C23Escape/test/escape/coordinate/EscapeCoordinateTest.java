package escape.coordinate;

import escape.board.CoordinateImpl;
import escape.EscapeGameManager;
import escape.builder.EscapeGameBuilder;
import escape.required.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EscapeCoordinateTest {

    @Test
    void checkCoordinateEquality() {
        assertEquals(new CoordinateImpl(1, 1), new CoordinateImpl(1, 1));
    }

    @Test
    void checkCoordinateInequality() {
        assertNotEquals(new CoordinateImpl(1, 1), new CoordinateImpl(1, 2));
    }

    @Test
    void checkMakeCoordinate() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);

        assertEquals(new CoordinateImpl(1, 1), coordinate1);
    }

    @Test
    void checkHashCoordinate() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Map<Coordinate, Integer> map = new HashMap<>();

        map.put(coordinate1,1);
        map.put(new CoordinateImpl(0,0),0);

        assertEquals(map.size(), 2);

        assertEquals(map.get(coordinate1), 1);
    }

}