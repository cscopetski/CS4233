package escape.builder;

import escape.CoordinateImpl;
import escape.EscapeGameManagerImpl;
import escape.builder.EscapeGameBuilder;
import escape.required.Coordinate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EscapeGameManagerTest {

    @Test
    void testSuccessfullyBuildGameManager(){
        EscapeGameManagerImpl gameManager = null;
        try {
            gameManager = (EscapeGameManagerImpl) new EscapeGameBuilder("configs/test1.egc").makeGameManager();
        } catch (Exception e) {
            fail("Builder Exception: " + e.getMessage());
        }
        assertNotNull(gameManager);
    }

    @Test
    void testBuild2x2Square(){
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
    void testBuild4x6Hex(){
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
}
