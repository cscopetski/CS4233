package escape.movement;

import escape.board.TileShapeCoordinate;

@FunctionalInterface
public interface MoveValidator {
    boolean isLegalMovePattern(TileShapeCoordinate from, TileShapeCoordinate to);
}
