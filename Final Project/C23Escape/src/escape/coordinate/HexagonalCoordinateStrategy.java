package escape.coordinate;

import escape.required.Coordinate;

public class HexagonalCoordinateStrategy implements CoordinateStrategy {


    /**
     * Get the manhattan distance between two coordinates
     *
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    @Override
    public int getDistance(Coordinate from, Coordinate to) {
        int colDiff = to.getColumn() - from.getColumn();
        int rowDiff = to.getRow() - from.getRow();

        if((colDiff < 0) == (rowDiff < 0)){
            return Math.abs(colDiff + rowDiff);
        }else{
            return Math.max(Math.abs(rowDiff), Math.abs(colDiff));
        }

    }

    /**
     * Get the neighboring coordinate in the given direction
     *
     * @param direction The direction to get the neighbor from
     * @return The neighboring coordinate in the given direction
     */
    @Override
    public Coordinate getNeighbor(Direction direction, Coordinate current) {
        int rowModifier = 0;
        int columnModifier = 0;

        switch (direction){
            case NORTH -> columnModifier = 1;
            case SOUTH -> columnModifier = -1;
            case NORTHEAST -> {
                rowModifier = 1;
            }
            case SOUTHEAST -> {
                rowModifier = 1;
                columnModifier = -1;
            }
            case NORTHWEST -> {
                rowModifier = -1;
                columnModifier = 1;
            }
            case SOUTHWEST -> {
                rowModifier = -1;
            }
        }

        return new CoordinateImpl(current.getRow()+rowModifier,current.getColumn()+columnModifier);
    }
}
