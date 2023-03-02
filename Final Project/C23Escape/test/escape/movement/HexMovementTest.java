package escape.movement;

import escape.managers.EscapeGameManager;
import escape.managers.EscapeGameManagerImpl;
import escape.board.Location;
import escape.builder.EscapeGameBuilder;
import escape.required.Coordinate;
import escape.required.GameStatus;
import escape.required.LocationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HexMovementTest {

    @Test
    void testNoPieceAtStartLocation(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(1, 1);
        Coordinate endLocation = gameManager.makeCoordinate(1, 2);

        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
    }

    @Test
    void testMoveFromStartToStart(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(0, 0);
        Coordinate endLocation = gameManager.makeCoordinate(0, 0);

        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
    }

    @Test
    void testWrongPlayerPieceMove(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(0, 1);
        Coordinate endLocation = gameManager.makeCoordinate(1, 1);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
    }

    @Test
    void testPieceAtEndLocation(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(0, 0);
        Coordinate endLocation = gameManager.makeCoordinate(0, 2);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
    }

    @Test
    void testPlayerTurnOnInvalidMove(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String startingPlayer = gameManager.getCurrentPlayer();

        Coordinate startLocation = gameManager.makeCoordinate(0, -1);
        Coordinate endLocation = gameManager.makeCoordinate(2, 2);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);

        assertEquals(startingPlayer, gameManager.getCurrentPlayer());

    }

    @Test
    void testPlayerTurnOnValidMove(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String[] players = {"Chris", "Pat"};

        assertEquals(players[0], gameManager.getCurrentPlayer());;

        Coordinate startLocation1 = gameManager.makeCoordinate(0,0);
        Coordinate endLocation1 = gameManager.makeCoordinate(0, -1);

        GameStatus status = gameManager.move(startLocation1, endLocation1);

        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);

        assertEquals(players[1], gameManager.getCurrentPlayer());;

        Coordinate startLocation2 = gameManager.makeCoordinate(0, 1);
        Coordinate endLocation2 = gameManager.makeCoordinate(0, 0);

        GameStatus status2 = gameManager.move(startLocation2, endLocation2);

        assertTrue(status2.isValidMove());
        assertEquals(status2.getMoveResult(), GameStatus.MoveResult.NONE);

        assertEquals(players[0], gameManager.getCurrentPlayer());;

    }

    @Test
    void testLocationOutOfBounds(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex2.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(1, 1);
        Coordinate endLocation = gameManager.makeCoordinate(2, 3);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
    }

    @Test
    void testInfiniteXBoardCreateNewLocation(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(0, 0);
        Coordinate endLocation = gameManager.makeCoordinate(0, -1);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);

        Location location = gameManager.getLocation(endLocation);

        assertNotNull(location);
        assertEquals(location.getLocationType(), LocationType.CLEAR);
        assertNotNull(location.getPiece());
    }

    @Test
    void checkLinearMoveHexBoardNoDistanceNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(-3, 3);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkNonLinearMoveHexBoardNoDistanceNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(-2, 1);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkLinearMoveNonDiagHexBoardNoObstaclesMaxDistance() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(0, -5);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkLinearFailMoveNonDiagHexBoardNoObstaclesOverMaxDistance() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(0, -6);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkLinearMoveDiagHexBoardNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, -5);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkLinearMoveDiagNegativeHexBoardNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(-5, 0);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkLinearBlockedByPiece() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(0, 5);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniHorizontalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(-5, 4);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniVerticalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(0, 7);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniDiagonalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniDiagonalAroundObstacleMaxLength() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex3.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(4, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniDiagonalObstacleBlockMaxLength() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex3.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, 2);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkFiniteBoardPathIsOutOfBounds() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/moveHex4.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 1);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }
}
