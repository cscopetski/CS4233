package escape.coordinate;

import escape.board.CoordinateImpl;
import escape.EscapeGameManager;
import escape.board.TileShapeCoordinate;
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
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 1);

        assertEquals(coordinate2, coordinate1);
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
        Coordinate coordinate2 = gameManager.makeCoordinate(0, 0);
        Map<Coordinate, Integer> map = new HashMap<>();

        map.put(coordinate1,1);
        map.put(coordinate2,0);

        assertEquals(map.size(), 2);

        assertEquals(map.get(coordinate1), 1);
        assertEquals(map.get(coordinate2), 0);
    }

    @Test
    void checkSquareDistance() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        TileShapeCoordinate coordinate1 = (TileShapeCoordinate) gameManager.makeCoordinate(1, 1);
        TileShapeCoordinate coordinate2 = (TileShapeCoordinate) gameManager.makeCoordinate(0, 0);

        assertEquals(2, coordinate1.getDistance(coordinate2));
    }

    @Test
    void checkHexDistance() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test2.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        TileShapeCoordinate coordinate1 = (TileShapeCoordinate) gameManager.makeCoordinate(1, 1);
        TileShapeCoordinate coordinate2 = (TileShapeCoordinate) gameManager.makeCoordinate(0, 0);

        assertEquals(2, coordinate1.getDistance(coordinate2));

        TileShapeCoordinate coordinate3 = (TileShapeCoordinate) gameManager.makeCoordinate(-2, 1);
        TileShapeCoordinate coordinate4 = (TileShapeCoordinate) gameManager.makeCoordinate(0, 0);

        assertEquals(2, coordinate3.getDistance(coordinate4));

        TileShapeCoordinate coordinate5 = (TileShapeCoordinate) gameManager.makeCoordinate(-1, 1);
        TileShapeCoordinate coordinate6 = (TileShapeCoordinate) gameManager.makeCoordinate(0, 0);

        assertEquals(1, coordinate5.getDistance(coordinate6));

        TileShapeCoordinate coordinate7 = (TileShapeCoordinate) gameManager.makeCoordinate(3, 2);
        TileShapeCoordinate coordinate8 = (TileShapeCoordinate) gameManager.makeCoordinate(0, 0);

        assertEquals(5, coordinate7.getDistance(coordinate8));
    }

}