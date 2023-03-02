package escape.piece.movement;

import escape.coordinate.CoordinateImpl;
import escape.coordinate.CoordinateStrategy;

public class MoveChecker {

    public static MoveValidator linearValidator = (from, to) -> isMoveLinear(from ,to);
    public static MoveValidator orthogonalValidator = (from, to) -> isMoveOrthogonal(from ,to);
    public static MoveValidator omniValidator = (from, to) -> isMoveOmni(from ,to);
    public static MoveValidator diagonalValidator = (from, to) -> isMoveDiagonal(from ,to);

    /**
     * Checks if a move is a valid linear move
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return True if the move is a valid linear move
     */
    private static boolean isMoveLinear(CoordinateImpl from, CoordinateImpl to){
        return checkOrthogonal(from, to) || checkDiagonal(from ,to);
    }

    /**
     * Checks if a move is a valid orthogonal move
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return True if the move is a valid orthogonal move
     */
    private static boolean isMoveOrthogonal(CoordinateImpl from, CoordinateImpl to){
        //Moves are always possible orthogonally
        return true;
    }

    /**
     * Checks if a move is a valid omni move
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return True if the move is a valid omni move
     */
    private static boolean isMoveOmni(CoordinateImpl from, CoordinateImpl to){
        //Moves are always possible omni
        return true;
    }

    /**
     * Checks if a move is a valid diagonal move
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return True if the move is a valid diagonal move
     */
    private static boolean isMoveDiagonal(CoordinateImpl from, CoordinateImpl to){
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int columnDiff = Math.abs(from.getColumn() - to.getColumn());
        return (rowDiff+columnDiff) %2 == 0;
    }

    /**
     * Helper method to check orthogonal movement
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return True if the move is orthogonal
     */
    private static boolean checkOrthogonal(CoordinateImpl from, CoordinateImpl to){
        return from.getRow() == to.getRow() || from.getColumn() == to.getColumn();
    }

    /**
     * Helper method to check diagonal movement
     * @param from the starting coordinate
     * @param to the ending coordinate
     * @return True if the move is diagonal
     */
    private static boolean checkDiagonal(CoordinateImpl from, CoordinateImpl to){

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int columnDiff = Math.abs(from.getColumn() - to.getColumn());
        return rowDiff == columnDiff;

    }
}
