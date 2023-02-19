package escape.board;

import escape.required.Coordinate;

public class HexagonalCoordinate extends CoordinateImpl implements TileShapeCoordinate{

    public HexagonalCoordinate(int x, int y) {
        super(x, y);
    }


    /**
     * Get the manhattan distance between two coordinates
     *
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    @Override
    public int getDistance(Coordinate to) {
        return 0;
    }

    /**
     * Get neighbors in all directions
     *
     * @return Neighboring coordinates in all directions
     */
    @Override
    public TileShapeCoordinate[] getAllNeighbors() {
        return new TileShapeCoordinate[0];
    }

    /**
     * Get a list of neighbors in the given directions
     *
     * @param directions The directions to get neighbors in
     * @return a list of neighboring coordinates in the given directions
     */
    @Override
    public TileShapeCoordinate[] getNeighbors(Direction[] directions) {
        return new TileShapeCoordinate[0];
    }

    /**
     * Get the neighboring coordinate in the given direction
     *
     * @param direction The direction to get the neighbor from
     * @return The neighboring coordinate in the given direction
     */
    @Override
    public TileShapeCoordinate getNeighbor(Direction direction) {
        return null;
    }
}
