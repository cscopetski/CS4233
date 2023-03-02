package escape.rules;

import escape.ObserverImpl;
import escape.managers.EscapeGameManager;
import escape.builder.EscapeGameBuilder;
import escape.piece.EscapePieceImpl;
import escape.required.Coordinate;
import escape.required.GameStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombatTest {

    @Test
    public void CheckAttackerWin(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/combat1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }
        gameManager.addObserver(new ObserverImpl());
        Coordinate coordinate1 = gameManager.makeCoordinate(-1, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(0, 1);

        GameStatus status = gameManager.move(coordinate1, coordinate2);

        assertTrue(status.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status.getMoveResult());
        assertEquals(GameStatus.CombatResult.ATTACKER, status.getCombatResult());

        EscapePieceImpl piece = (EscapePieceImpl) gameManager.getPieceAt(coordinate2);

        assertNull(gameManager.getPieceAt(coordinate1));
        assertEquals(piece.getPlayer(), "Chris");
        assertEquals(piece.getValue(), 1);
    }

    @Test
    public void CheckDefenderWin(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/combat1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }
        gameManager.addObserver(new ObserverImpl());
        Coordinate coordinate1 = gameManager.makeCoordinate(0, -1);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 0);

        GameStatus status = gameManager.move(coordinate1, coordinate2);

        assertTrue(status.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status.getMoveResult());
        assertEquals(GameStatus.CombatResult.DEFENDER, status.getCombatResult());

        EscapePieceImpl piece = (EscapePieceImpl) gameManager.getPieceAt(coordinate2);

        assertNull(gameManager.getPieceAt(coordinate1));
        assertEquals(piece.getPlayer(), "Pat");
        assertEquals(piece.getValue(), 1);
    }

    @Test
    public void CheckDraw(){
        EscapeGameManager gameManager = null;

        try {
            gameManager = new EscapeGameBuilder("configurations/combat1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Exception from builder: " + e.getMessage());
        }
        gameManager.addObserver(new ObserverImpl());
        Coordinate coordinate1 = gameManager.makeCoordinate(-1, 0);
        Coordinate coordinate2 = gameManager.makeCoordinate(1, 0);

        GameStatus status = gameManager.move(coordinate1, coordinate2);

        assertTrue(status.isValidMove());
        assertEquals(GameStatus.MoveResult.NONE, status.getMoveResult());
        assertEquals(GameStatus.CombatResult.DRAW, status.getCombatResult());

        assertNull(gameManager.getPieceAt(coordinate1));
        assertNull(gameManager.getPieceAt(coordinate2));
    }
}
