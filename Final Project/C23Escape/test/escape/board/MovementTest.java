package escape.board;

import escape.EscapeGameManagerImpl;
import escape.builder.EscapeGameBuilder;
import escape.required.GameStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovementTest {

    @Test
    void testNoPieceAtStartLocation(){
        EscapeGameManagerImpl<CoordinateImpl> gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        GameStatus status = gameManager.move(new CoordinateImpl(0, 0), new CoordinateImpl(0, 1));

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), new CoordinateImpl(0,0));
    }

    @Test
    void testWrongPlayerPieceMove(){
        EscapeGameManagerImpl<CoordinateImpl> gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        CoordinateImpl startLocation = new CoordinateImpl(1, 1);
        CoordinateImpl endLocation = new CoordinateImpl(0, 0);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);
    }

    @Test
    void testPieceAtEndLocation(){
        EscapeGameManagerImpl<CoordinateImpl> gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        CoordinateImpl startLocation = new CoordinateImpl(1, 0);
        CoordinateImpl endLocation = new CoordinateImpl(1, 1);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);
    }

    @Test
    void testPlayerTurnOnInvalidMove(){
        EscapeGameManagerImpl<CoordinateImpl> gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String startingPlayer = gameManager.getCurrentPlayer();

        CoordinateImpl startLocation = new CoordinateImpl(1, 0);
        CoordinateImpl endLocation = new CoordinateImpl(1, 1);


        GameStatus status = gameManager.move(startLocation, endLocation);

        assertFalse(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), startLocation);

        assertEquals(startingPlayer, gameManager.getCurrentPlayer());

    }

    @Test
    void testPlayerTurnOnValidMove(){
        EscapeGameManagerImpl<CoordinateImpl> gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String[] players = {"Chris", "Pat"};

        assertEquals(players[0], gameManager.getCurrentPlayer());;

        CoordinateImpl startLocation1 = new CoordinateImpl(1, 0);
        CoordinateImpl endLocation1 = new CoordinateImpl(0, 0);

        GameStatus status = gameManager.move(startLocation1, endLocation1);

        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status.finalLocation(), endLocation1);

        assertEquals(players[1], gameManager.getCurrentPlayer());;

        CoordinateImpl startLocation2 = new CoordinateImpl(1, 1);
        CoordinateImpl endLocation2 = new CoordinateImpl(0, 1);

        GameStatus status2 = gameManager.move(startLocation2, endLocation2);

        assertTrue(status2.isValidMove());
        assertEquals(status2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(status2.finalLocation(), endLocation2);

        assertEquals(players[0], gameManager.getCurrentPlayer());;

    }

}
