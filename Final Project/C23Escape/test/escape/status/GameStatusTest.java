package escape.status;

import escape.EscapeGameManager;
import escape.builder.EscapeGameBuilder;
import escape.required.Coordinate;
import escape.required.GameStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStatusTest {

    @Test
    public void CheckLossAfterInvalidMove(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusTimeLimit1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 2);

        GameStatus status = gameManager.move(coordinate1, coordinate2);

        assertFalse(status.isValidMove());
        assertEquals(GameStatus.MoveResult.LOSE, status.getMoveResult());
    }

    @Test
    public void CheckTurnLimitWin(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusTimeLimit1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 1);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 0);
        Coordinate coordinate4 = gameManager.makeCoordinate(0, 0);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertTrue(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.LOSE,status2.getMoveResult());

    }

    @Test
    public void CheckTurnLimitDraw(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusTimeLimit1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 1);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 0);
        Coordinate coordinate4 = gameManager.makeCoordinate(1, 1);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertTrue(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.DRAW,status2.getMoveResult());

    }

    @Test
    public void CheckRemoveAllPiecesWin(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusRemoveAllPieces.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 1);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.WIN, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 0);
        Coordinate coordinate4 = gameManager.makeCoordinate(1, 1);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertFalse(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.LOSE,status2.getMoveResult());

    }

    @Test
    public void CheckScoreReachedWin1(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusScore1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 1);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.WIN, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 0);
        Coordinate coordinate4 = gameManager.makeCoordinate(1, 1);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertFalse(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.LOSE,status2.getMoveResult());

    }

    @Test
    public void CheckScoreReachedWin2(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusScore1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(0, 3);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 0);
        Coordinate coordinate4 = gameManager.makeCoordinate(1, 1);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertTrue(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.WIN,status2.getMoveResult());

    }

    @Test
    public void CheckScoreTimeLimitWinByScore(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusScoreTimeLimit1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 1);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 0);
        Coordinate coordinate4 = gameManager.makeCoordinate(1, 1);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertTrue(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE,status2.getMoveResult());

        Coordinate coordinate5 = gameManager.makeCoordinate(0, 2);
        Coordinate coordinate6 = gameManager.makeCoordinate(0, 1);

        GameStatus status3 = gameManager.move(coordinate5, coordinate6);

        assertTrue(status3.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE,status3.getMoveResult());

        Coordinate coordinate7 = gameManager.makeCoordinate(2, 0);
        Coordinate coordinate8 = gameManager.makeCoordinate(1, 1);

        GameStatus status4 = gameManager.move(coordinate7, coordinate8);

        assertTrue(status4.isValidMove());
        assertEquals(GameStatus.MoveResult.WIN,status4.getMoveResult());

    }

    @Test
    public void CheckScoreTimeLimitDrawByTime(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusScoreTimeLimit1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(0, 1);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 1);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 0);
        Coordinate coordinate4 = gameManager.makeCoordinate(1, 1);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertTrue(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE,status2.getMoveResult());

        Coordinate coordinate5 = gameManager.makeCoordinate(0, 2);
        Coordinate coordinate6 = gameManager.makeCoordinate(0, 1);

        GameStatus status3 = gameManager.move(coordinate5, coordinate6);

        assertTrue(status3.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE,status3.getMoveResult());

        Coordinate coordinate7 = gameManager.makeCoordinate(2, 0);
        Coordinate coordinate8 = gameManager.makeCoordinate(1, 0);

        GameStatus status4 = gameManager.move(coordinate7, coordinate8);

        assertTrue(status4.isValidMove());
        assertEquals(GameStatus.MoveResult.DRAW,status4.getMoveResult());

    }

    @Test
    public void CheckPieceCannotMove(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configs/gameStatusPieceCannotMove1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }

        Coordinate coordinate1 = gameManager.makeCoordinate(2, 2);
        Coordinate coordinate2 = gameManager.makeCoordinate(2, 1);

        GameStatus status1 = gameManager.move(coordinate1, coordinate2);

        assertTrue(status1.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status1.getMoveResult());

        Coordinate coordinate3 = gameManager.makeCoordinate(1, 2);
        Coordinate coordinate4 = gameManager.makeCoordinate(2, 2);

        GameStatus status2 = gameManager.move(coordinate3, coordinate4);

        assertTrue(status2.isValidMove());
        assertEquals(GameStatus.MoveResult.WIN,status2.getMoveResult());

    }

}
