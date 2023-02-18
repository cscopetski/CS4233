package escape.movement;

import escape.board.Board;
import escape.board.TileShapeCoordinate;

@FunctionalInterface
public interface PathValidator {
    boolean isLegalPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock);
}
