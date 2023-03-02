package escape.piece.movement;

import escape.coordinate.CoordinateImpl;

@FunctionalInterface
public interface PathValidator {
    boolean isLegalPath(CoordinateImpl from, CoordinateImpl to, int distance, boolean fly, boolean jump, boolean unblock, boolean anyMove);
}
