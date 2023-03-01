package escape.piece.movement;

import escape.coordinate.TileShapeCoordinate;

@FunctionalInterface
public interface PathValidator {
    boolean isLegalPath(TileShapeCoordinate from, TileShapeCoordinate to, int distance, boolean fly, boolean jump, boolean unblock);
}
