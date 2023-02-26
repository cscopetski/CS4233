package escape.board;

import escape.required.Coordinate;

public class SquareCoordinate extends CoordinateImpl implements TileShapeCoordinate{

    public SquareCoordinate(int x, int y) {
        super(x, y);
    }

    /**
     * Get the manhattan distance between two coordinates
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    @Override
    public int getDistance(Coordinate to) {
        return Math.abs(this.getRow() - to.getRow()) + Math.abs(this.getColumn() - to.getColumn());
    }

    /**
     * Get the neighboring coordinate in the given direction
     * @param direction The direction to get the neighbor from
     * @return The neighboring coordinate in the given direction
     */
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
