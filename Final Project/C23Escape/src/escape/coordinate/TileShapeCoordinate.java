package escape.coordinate;

import escape.required.Coordinate;

public interface TileShapeCoordinate extends Coordinate {

    enum Direction{NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST}

    static Direction[] getOrthogonalDirections() {
        return new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    }
    static Direction[] getDiagonalDirections() {
        return new Direction[]{Direction.NORTHEAST, Direction.NORTHWEST, Direction.SOUTHEAST, Direction.SOUTHWEST};
    }

    /**
     * Get the manhattan distance between two coordinates
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    int getDistance(Coordinate to);

    /**
     * Get a list of neighbors in the given directions
     *
     * @param directions The directions to get neighbors in
     * @return a list of neighboring coordinates in the given directions
     */
     default TileShapeCoordinate[] getNeighbors(Direction[] directions) {

            TileShapeCoordinate[] neighbors = new TileShapeCoordinate[directions.length];

            int count = 0;

            for (Direction direction: directions) {
                neighbors[count] = this.getNeighbor(direction);
                count++;
            }

            return neighbors;
    }

    /**
     * Get the neighboring coordinate in the given direction
     * @param direction The direction to get the neighbor from
     * @return The neighboring coordinate in the given direction
     */
    TileShapeCoordinate getNeighbor(Direction direction);

}
