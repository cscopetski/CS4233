package escape.board;

import escape.required.Coordinate;

public interface TileShapeCoordinate extends Coordinate {

    static enum Direction{NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST}

    int getDistance(Coordinate to);

    TileShapeCoordinate[] getNeighbors();

    TileShapeCoordinate getNeighbor(Direction direction);

}
