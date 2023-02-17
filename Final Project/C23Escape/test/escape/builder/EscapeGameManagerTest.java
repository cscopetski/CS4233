package escape.builder;

import escape.*;
import escape.board.CoordinateImpl;
import escape.board.Location;
import escape.board.LocationImpl;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;
import escape.required.PieceAttribute;
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
            assertEquals(Coordinate.CoordinateType.SQUARE, gameManager.getCoordinateType());
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
            assertEquals(Coordinate.CoordinateType.HEX, gameManager.getCoordinateType());
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

        LocationImpl[] locations = {new LocationImpl(null, new CoordinateImpl(0, 0), LocationType.CLEAR),
                new LocationImpl(new EscapePieceImpl(EscapePiece.PieceName.SNAIL, player1, EscapePiece.MovementPattern.OMNI, new PieceAttribute[]{snailDistance}), new CoordinateImpl(1, 0), LocationType.CLEAR),
                new LocationImpl(null, new CoordinateImpl(0, 1), LocationType.CLEAR),
                new LocationImpl(new EscapePieceImpl(EscapePiece.PieceName.HORSE, player2, EscapePiece.MovementPattern.OMNI, new PieceAttribute[]{snailDistance}), new CoordinateImpl(1, 1), LocationType.CLEAR)};

        for (LocationImpl location : locations) {
            LocationImpl newLocation = (LocationImpl) gameManager.getLocation(location.getCoordinate());

            assertEquals(location.getLocationType(), newLocation.getLocationType());
            assertEquals(location.getCoordinate(), newLocation.getCoordinate());

            EscapePieceImpl piece = (EscapePieceImpl) location.getPiece();
            EscapePieceImpl newPiece = (EscapePieceImpl) gameManager.getPieceAt(newLocation.getCoordinate());

            if (piece == null) {
                assertNull(newPiece);
            } else {
                assertNotNull(newPiece);
                assertEquals(piece.getName(), newPiece.getName());
                assertEquals(piece.getPlayer(), newPiece.getPlayer());
                assertEquals(piece.getMovementPattern(), newPiece.getMovementPattern());

                PieceAttribute[] pieceAttributes = piece.getAttributes();
                PieceAttribute[] newPieceAttributes = newPiece.getAttributes();

                for (int i = 0; i < piece.getAttributes().length; i++) {

                    assertEquals(pieceAttributes[i].getId(), newPieceAttributes[i].getId());
                    assertEquals(pieceAttributes[i].getValue(), newPieceAttributes[i].getValue());

                }


            }
        }
    }
}
