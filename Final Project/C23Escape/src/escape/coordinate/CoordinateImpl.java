package escape.coordinate;

import escape.required.Coordinate;

import java.util.Objects;

public class CoordinateImpl implements Coordinate {

    private int row;
    private int column;

    public CoordinateImpl(int x, int y){
        row = x;
        column = y;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    /**
     * Checks if two coords are equal
     * @param obj The object to compare with
     * @return true if the row and column attributes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if(obj.getClass() == this.getClass()){
            return ((CoordinateImpl) obj).getRow() == this.row &&
                    ((CoordinateImpl) obj).getColumn() == this.column;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Get the manhattan distance between two coordinates
     *
     * @param to The coordinate to compare to
     * @return The manhattan distance between two coordinates
     */
    public int getDistance(Coordinate to, CoordinateStrategy strategy) {
        return strategy.getDistance(this, to);
    }

    /**
     * Get the neighboring coordinate in the given direction
     * @param direction The direction to get the neighbor from
     * @return The neighboring coordinate in the given direction
     */
    public Coordinate getNeighbor(CoordinateStrategy.Direction direction,  CoordinateStrategy strategy) {
        return strategy.getNeighbor(direction, this);
    }
}
