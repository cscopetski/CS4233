package escape.board;

import escape.required.Coordinate;

public class HexagonalCoordinate extends CoordinateImpl implements TileShapeCoordinate{

    public HexagonalCoordinate(int x, int y) {
        super(x, y);
    }

    @Override
    public int getDistance(Coordinate to) {
        return 0;
    }

    @Override
    public TileShapeCoordinate[] getNeighbors() {
        return new TileShapeCoordinate[] {
                new SquareCoordinate(getRow()+1, getColumn()),
                new SquareCoordinate(getRow()-1, getColumn()),
                new SquareCoordinate(getRow(), getColumn()+1),
                new SquareCoordinate(getRow(), getColumn()-1),
                new SquareCoordinate(getRow()+1, getColumn()-1),
                new SquareCoordinate(getRow()-1, getColumn()+1),
        };
    }

    @Override
    public TileShapeCoordinate getNeighbor(Direction direction) {
        return null;
    }

}
