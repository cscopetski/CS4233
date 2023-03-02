package escape.coordinate;

import escape.required.Coordinate;

public class SquareCoordinateStrategy implements CoordinateStrategy {

    /**
     * Get the manhattan distance between two coordinates
     *
     * @param from
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    @Override
    public int getDistance(Coordinate from, Coordinate to) {
        return Math.abs(from.getRow() - to.getRow()) + Math.abs(from.getColumn() - to.getColumn());
    }

    /**
     * Get the neighboring coordinate in the given direction
     * @param direction The direction to get the neighbor from
     * @param current
     * @return The neighboring coordinate in the given direction
     */
    @Override
    public Coordinate getNeighbor(Direction direction, Coordinate current) {

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

        return new CoordinateImpl(current.getRow()+rowModifier,current.getColumn()+columnModifier);
    }

}
