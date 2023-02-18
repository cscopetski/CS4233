package escape.movement;

import escape.board.TileShapeCoordinate;

public class MoveChecker {

    public static MoveValidator linearValidator = (from, to) -> isMoveLinear(from ,to);
    public static MoveValidator orthogonalValidator = (from, to) -> isMoveOrthogonal(from ,to);
    public static MoveValidator omniValidator = (from, to) -> isMoveOmni(from ,to);
    public static MoveValidator diagonalValidator = (from, to) -> isMoveDiagonal(from ,to);

    private static boolean isMoveLinear(TileShapeCoordinate from, TileShapeCoordinate to){
        return checkOrthogonal(from, to) || checkDiagonal(from ,to);
    }

    //TODO: TEST THIS
    private static boolean isMoveOrthogonal(TileShapeCoordinate from, TileShapeCoordinate to){
        //Moves are always possible orthogonally
        return true;
    }

    //TODO: TEST THIS
    private static boolean isMoveOmni(TileShapeCoordinate from, TileShapeCoordinate to){
        //Moves are always possible omni
        return true;
    }

    //TODO: TEST THIS
    private static boolean isMoveDiagonal(TileShapeCoordinate from, TileShapeCoordinate to){
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int columnDiff = Math.abs(from.getColumn() - to.getColumn());
        return rowDiff+columnDiff %2 == 0;
    }

    private static boolean checkOrthogonal(TileShapeCoordinate from, TileShapeCoordinate to){
        return from.getRow() == to.getRow() || from.getColumn() == to.getColumn();
    }

    private static boolean checkDiagonal(TileShapeCoordinate from, TileShapeCoordinate to){

        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int columnDiff = Math.abs(from.getColumn() - to.getColumn());
        return rowDiff == columnDiff;

    }
}
