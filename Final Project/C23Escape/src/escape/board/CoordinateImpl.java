package escape.board;

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

        if(obj.getClass() == CoordinateImpl.class){
            return ((CoordinateImpl) obj).getRow() == this.row &&
                    ((CoordinateImpl) obj).getColumn() == this.column;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
