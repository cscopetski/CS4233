package escape.board;

import escape.EscapeGameManager;
import escape.EscapeGameManagerImpl;
import escape.EscapePieceImpl;
import escape.builder.EscapeGameBuilder;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.GameStatus;
import escape.required.LocationType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovementTest {

    @Test
    void testNoPieceAtStartLocation(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(1, 1);
        Coordinate endLocation = gameManager.makeCoordinate(1, 2);

        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);
    }

    @Test
    void testWrongPlayerPieceMove(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(2, 2);
        Coordinate endLocation = gameManager.makeCoordinate(1, 1);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);
    }

    @Test
    void testPieceAtEndLocation(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(2, 1);
        Coordinate endLocation = gameManager.makeCoordinate(2, 2);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);
    }

    @Test
    void testPlayerTurnOnInvalidMove(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String startingPlayer = gameManager.getCurrentPlayer();

        Coordinate startLocation = gameManager.makeCoordinate(2, 1);
        Coordinate endLocation = gameManager.makeCoordinate(2, 2);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);

        assertEquals(startingPlayer, gameManager.getCurrentPlayer());

    }

    @Test
    void testPlayerTurnOnValidMove(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String[] players = {"Chris", "Pat"};

        assertEquals(players[0], gameManager.getCurrentPlayer());;

        Coordinate startLocation1 = gameManager.makeCoordinate(2,1);
        Coordinate endLocation1 = gameManager.makeCoordinate(1, 1);

        GameStatus status = gameManager.move(startLocation1, endLocation1);

        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), endLocation1);

        assertEquals(players[1], gameManager.getCurrentPlayer());;

        Coordinate startLocation2 = gameManager.makeCoordinate(2, 2);
        Coordinate endLocation2 = gameManager.makeCoordinate(1, 2);

        GameStatus status2 = gameManager.move(startLocation2, endLocation2);

        assertTrue(status2.isValidMove());
        assertEquals(status2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status2.finalLocation(), endLocation2);

        assertEquals(players[0], gameManager.getCurrentPlayer());;

    }

    @Test
    void testLocationOutOfBounds(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(2, 1);
        Coordinate endLocation = gameManager.makeCoordinate(3, 2);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);
    }

    @Test
    void testInfiniteXBoardCreateNewLocation(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test4.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Coordinate startLocation = gameManager.makeCoordinate(2, 1);
        Coordinate endLocation = gameManager.makeCoordinate(3, 2);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), endLocation);

        Location location = gameManager.getLocation(endLocation);

        assertNotNull(location);
        assertEquals(location.getLocationType(), LocationType.CLEAR);
        assertNotNull(location.getPiece());
    }

    @Test
    void checkLinearMoveSquareBoardNoDistanceNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 2);

        EscapePieceImpl piece = (EscapePieceImpl)gameManager.getPieceAt(gameManager.makeCoordinate(2, 1));

        assertTrue(piece.canMove((TileShapeCoordinate) coordinate1, (TileShapeCoordinate) coordinate2));

    }

    @Test
    void checkNonLinearMoveSquareBoardNoDistanceNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 3);

        EscapePieceImpl piece = (EscapePieceImpl)gameManager.getPieceAt(coordinate1);

        assertFalse(piece.canMove((TileShapeCoordinate) coordinate1, (TileShapeCoordinate) coordinate2));

    }

    @Test
    void checkLinearMoveNonDiagSquareBoardNoObstaclesMaxDistance() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 1);

        EscapePieceImpl piece = (EscapePieceImpl)gameManager.getPieceAt(coordinate1);

        assertTrue(piece.canMove((TileShapeCoordinate) coordinate1, (TileShapeCoordinate) coordinate2));

    }

    @Test
    void checkLinearFailMoveNonDiagSquareBoardNoObstaclesOverMaxDistance() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(8, 1);

        EscapePieceImpl piece = (EscapePieceImpl)gameManager.getPieceAt(coordinate1);

        assertFalse(piece.canMove((TileShapeCoordinate) coordinate1, (TileShapeCoordinate) coordinate2));

    }

    @Test
    void checkLinearMoveDiagSquareBoardNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(6, 5);

        EscapePieceImpl piece = (EscapePieceImpl)gameManager.getPieceAt(coordinate1);

        assertTrue(piece.canMove((TileShapeCoordinate) coordinate1, (TileShapeCoordinate) coordinate2));

    }

    @Test
    void checkLinearMoveDiagNegativeSquareBoardNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(-2, -3);

        EscapePieceImpl piece = (EscapePieceImpl)gameManager.getPieceAt(coordinate1);

        assertTrue(piece.canMove((TileShapeCoordinate) coordinate1, (TileShapeCoordinate) coordinate2));

    }

    @Test
    void checkLinearBlockedByPiece() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 4);

        EscapePieceImpl piece = (EscapePieceImpl)gameManager.getPieceAt(coordinate1);

        assertFalse(piece.canMove((TileShapeCoordinate) coordinate1, (TileShapeCoordinate) coordinate2));

    }

}
