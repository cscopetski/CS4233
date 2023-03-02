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

public class SquareMovementTest {

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
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
    }

    @Test
    void testMoveFromStartToStart(){
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
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
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
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
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
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
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
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);

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

        assertEquals(players[1], gameManager.getCurrentPlayer());;

        Coordinate startLocation2 = gameManager.makeCoordinate(2, 2);
        Coordinate endLocation2 = gameManager.makeCoordinate(1, 2);

        GameStatus status2 = gameManager.move(startLocation2, endLocation2);

        assertTrue(status2.isValidMove());
        assertEquals(status2.getMoveResult(), GameStatus.MoveResult.NONE);

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
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);
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

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 0);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

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

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

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

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

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

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

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

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

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

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

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

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOrthogonalHorizontalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test6.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 7);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOrthogonalVerticalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test6.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOrthogonalDiagonalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test6.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 7);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOrthogonalLMovementAroundObstacle() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test6.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 0);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOrthogonalBlockedByObstacleMaxLength() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test11.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 2);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniHorizontalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test7.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 7);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniVerticalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test7.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniDiagonalMovementNoObstacles() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test7.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 7);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniDiagonalAroundObstacleMaxLength() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test7.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 1);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniDiagonalObstacleBlockMaxLength() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test7.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 6);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkLinearFlyOverPiece() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test8.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 4);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkLinearFlyOverPieceEndBlocked() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test8.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 3);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOrthogonalFlyOverObstacleMaxLength() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test10.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkOmniFlyOverObstacleMaxLength() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test9.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(7, 6);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkFiniteBoardPathIsOutOfBounds() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/test12.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 3);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkDiagonalSimpleValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveDiagonalSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, 5);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }
    @Test
    void checkDiagonalMultiValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveDiagonalSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, -1);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkDiagonalInvalidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveDiagonalSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 0);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkUnblockLinearValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveUnblockSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 3);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkUnblockLinearInvalidMoveBlockAtEnd() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveUnblockSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(-1, 1);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkUnblockOrthogonalValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveUnblockSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(-2, -1);
        Coordinate coordinate2 = gameManager.makeCoordinate(-1, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkUnblockOmniValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveUnblockSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(-2, 0);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkJumpLinearValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, 0);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkJumpDoubleLinearInvalidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare2.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, 0);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkJumpLOrthogonalValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare3.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkJumpLOrthogonalInvalidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare4.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 2);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkJumpXOmniValidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare5.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 1);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkJumpXOmniInvalidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare6.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(5, 2);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkJumpOmniOutOfBoundsInvalidMove() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare7.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 2);

        assertFalse(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkFlyPrecedenceOverJumpOmni() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare9.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }

    @Test
    void checkUnblockPrecedenceOverJumpOmni() {
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/moveJumpSquare8.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(1, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(3, 2);

        assertTrue(gameManager.move(coordinate1, coordinate2).isValidMove());

    }
}
