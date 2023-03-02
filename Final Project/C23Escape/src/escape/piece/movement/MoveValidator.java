package escape.piece.movement;

import escape.coordinate.CoordinateImpl;

@FunctionalInterface
public interface MoveValidator {
    boolean isLegalMovePattern(CoordinateImpl from, CoordinateImpl to);
}
