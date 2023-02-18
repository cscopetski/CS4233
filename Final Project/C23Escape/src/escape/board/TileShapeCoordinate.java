package escape.board;

import escape.required.Coordinate;

public interface TileShapeCoordinate extends Coordinate {

    enum Direction{NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST}

    int getDistance(Coordinate to);

    TileShapeCoordinate[] getAllNeighbors();

    static Direction[] getOrthogonalDirections() {
        return new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    }

    TileShapeCoordinate[] getNeighbors(Direction[] directions);

    TileShapeCoordinate getNeighbor(Direction direction);

}
