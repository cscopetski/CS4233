package escape.piece;

import escape.coordinate.TileShapeCoordinate;

@FunctionalInterface
public interface MoveValidator {
    boolean isLegalMovePattern(TileShapeCoordinate from, TileShapeCoordinate to);
}
