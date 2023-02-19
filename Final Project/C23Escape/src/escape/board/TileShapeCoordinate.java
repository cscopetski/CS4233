package escape.board;

import escape.required.Coordinate;

public interface TileShapeCoordinate extends Coordinate {

    enum Direction{NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST}

    static Direction[] getOrthogonalDirections() {
        return new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    }

    /**
     * Get the manhattan distance between two coordinates
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    int getDistance(Coordinate to);

    /**
     * Get neighbors in all directions
     * @return Neighboring coordinates in all directions
     */
    TileShapeCoordinate[] getAllNeighbors();

    /**
     * Get a list of neighbors in the given directions
     * @param directions The directions to get neighbors in
     * @return a list of neighboring coordinates in the given directions
     */
    TileShapeCoordinate[] getNeighbors(Direction[] directions);

    /**
     * Get the neighboring coordinate in the given direction
     * @param direction The direction to get the neighbor from
     * @return The neighboring coordinate in the given direction
     */
    TileShapeCoordinate getNeighbor(Direction direction);

}
