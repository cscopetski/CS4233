package escape.board;

import escape.required.Coordinate;

public class SquareCoordinate extends CoordinateImpl implements TileShapeCoordinate{

    public SquareCoordinate(int x, int y) {
        super(x, y);
    }

    @Override
    public int getDistance(Coordinate to) {
        return Math.abs(this.getRow() - to.getRow()) + Math.abs(this.getColumn() - to.getColumn());
    }

    @Override
    public TileShapeCoordinate[] getAllNeighbors() {
        return new TileShapeCoordinate[] {
            new SquareCoordinate(getRow()+1, getColumn()),
            new SquareCoordinate(getRow()-1, getColumn()),
            new SquareCoordinate(getRow(), getColumn()+1),
            new SquareCoordinate(getRow(), getColumn()-1),
            new SquareCoordinate(getRow()+1, getColumn()+1),
            new SquareCoordinate(getRow()-1, getColumn()-1),
            new SquareCoordinate(getRow()+1, getColumn()-1),
            new SquareCoordinate(getRow()-1, getColumn()+1),
        };
    }

    @Override
    public TileShapeCoordinate[] getNeighbors(Direction[] directions) {

        TileShapeCoordinate[] neighbors = new TileShapeCoordinate[directions.length];

        int count = 0;

        for (Direction direction: directions) {
            neighbors[count] = this.getNeighbor(direction);
            count++;
        }

        return neighbors;
    }

    @Override
    public TileShapeCoordinate getNeighbor(Direction direction) {

        int rowModifier = 0;
        int columnModifier = 0;

        switch (direction){
            case NORTH -> rowModifier = 1;
            case SOUTH -> rowModifier = -1;
            case EAST -> columnModifier = 1;
            case WEST -> columnModifier = -1;
            case NORTHEAST -> {
                rowModifier = 1;
                columnModifier = 1;
            }
            case SOUTHEAST -> {
                rowModifier = -1;
                columnModifier = 1;
            }
            case NORTHWEST -> {
                rowModifier = 1;
                columnModifier = -1;
            }
            case SOUTHWEST -> {
                rowModifier = -1;
                columnModifier = -1;
            }
        }

        return new SquareCoordinate(this.getRow()+rowModifier,this.getColumn()+columnModifier);
    }

}
