package escape.builder;

import escape.board.LocationImpl;
import escape.managers.EscapeGameManagerImpl;
import escape.piece.EscapePieceImpl;
import escape.required.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EscapeGameManagerTest {

    @Test
    void testSuccessfullyBuildGameManager() {
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Builder Exception: " + e.getMessage());
        }
        assertNotNull(gameManager);
    }

    @Test
    void testBuild2x2Square() {
        EscapeGameManagerImpl gameManager;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test1.egc").makeGameManager();
            assertEquals(2, gameManager.getxMax());
            assertEquals(2, gameManager.getyMax());

        } catch (Exception e) {
            fail("Builder Exception: " + e.getMessage());
        }

    }

    @Test
    void testBuild4x6Hex() {
        EscapeGameManagerImpl gameManager;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test2.egc").makeGameManager();
            assertEquals(4, gameManager.getxMax());
            assertEquals(6, gameManager.getyMax());

        } catch (Exception e) {
            fail("Builder Exception: " + e.getMessage());
        }

    }

    @Test
    void testBuild2x2Square4Locations() throws Exception {
        EscapeGameManagerImpl gameManager;
        gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test3.egc").makeGameManager();
        String player1 = "Chris";
        String player2 = "Pat";

        PieceAttribute snailDistance = new PieceAttribute();
        snailDistance.setId(EscapePiece.PieceAttributeID.DISTANCE);
        snailDistance.setValue(1);

        Coordinate[] coordinates = {gameManager.makeCoordinate(1, 1), gameManager.makeCoordinate(2, 1), gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(2, 2)};
        LocationImpl[] locations = {new LocationImpl(null, LocationType.CLEAR),
                new LocationImpl(new EscapePieceImpl(EscapePiece.PieceName.SNAIL, player1, EscapePiece.MovementPattern.OMNI, new PieceAttribute[]{snailDistance}), LocationType.CLEAR),
                new LocationImpl(null, LocationType.CLEAR),
                new LocationImpl(new EscapePieceImpl(EscapePiece.PieceName.HORSE, player2, EscapePiece.MovementPattern.OMNI, new PieceAttribute[]{snailDistance}), LocationType.CLEAR)};

        for (int i = 0; i < coordinates.length; i++) {

            Coordinate coordinate = coordinates[i];
            LocationImpl location = locations[i];
            LocationImpl newLocation = (LocationImpl) gameManager.getLocation(coordinate);

            assertEquals(location.getLocationType(), newLocation.getLocationType());

            EscapePieceImpl piece = (EscapePieceImpl) location.getPiece();
            EscapePieceImpl newPiece = (EscapePieceImpl) gameManager.getPieceAt(coordinate);

            if (piece == null) {
                assertNull(newPiece);
            } else {
                assertNotNull(newPiece);
                assertEquals(piece.getName(), newPiece.getName());
                assertEquals(piece.getPlayer(), newPiece.getPlayer());
                assertEquals(piece.getMovementPattern(), newPiece.getMovementPattern());

                PieceAttribute[] pieceAttributes = piece.getAttributes();
                PieceAttribute[] newPieceAttributes = newPiece.getAttributes();

                for (int j = 0; j < piece.getAttributes().length; j++) {

                    assertEquals(pieceAttributes[j].getId(), newPieceAttributes[j].getId());
                    assertEquals(pieceAttributes[j].getValue(), newPieceAttributes[j].getValue());

                }
            }
        }
    }

    @Test
    void testBuildRules() {
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/ruleBuilder.egc").makeGameManager();
        } catch (Exception e) {
            fail("Builder Exception: " + e.getMessage());
        }

        Map<Rule.RuleID, Integer> ruleMap = gameManager.getRules();

        assertEquals(ruleMap.get(Rule.RuleID.TURN_LIMIT),1);
        assertEquals(ruleMap.get(Rule.RuleID.SCORE),5);
        assertEquals(ruleMap.get(Rule.RuleID.POINT_CONFLICT),0);
    }
}
