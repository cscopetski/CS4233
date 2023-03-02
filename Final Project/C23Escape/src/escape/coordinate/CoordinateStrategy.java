package escape.coordinate;

import escape.required.Coordinate;

public interface CoordinateStrategy {

    enum Direction{NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST}

    static Direction[] getOrthogonalDirections() {
        return new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    }
    static Direction[] getDiagonalDirections() {
        return new Direction[]{Direction.NORTHEAST, Direction.NORTHWEST, Direction.SOUTHEAST, Direction.SOUTHWEST};
    }

    /**
     * Get the manhattan distance between two coordinates
     *
     * @param from
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    int getDistance(Coordinate from, Coordinate to);

    /**
     * Get the neighboring coordinate in the given direction
     * @param direction The direction to get the neighbor from
     * @param current the current coordinate
     * @return The neighboring coordinate in the given direction
     */
    Coordinate getNeighbor(Direction direction, Coordinate current);

}
