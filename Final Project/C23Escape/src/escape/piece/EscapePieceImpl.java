package escape.piece;

import escape.coordinate.TileShapeCoordinate;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.PieceAttribute;

public class EscapePieceImpl implements EscapePiece {

    private PieceName name;
    private String player;
    private MovementPattern movementPattern;
    private PieceAttribute[] attributes;
    private MoveValidator moveValidator;
    private PathValidator pathValidator;
    private int distance;
    private boolean fly = false;
    private boolean jump = false;
    private boolean unblock = false;
    private int value = 1;

    public EscapePieceImpl(PieceName name, String player, MovementPattern movementPattern, PieceAttribute[] attributes){
        this.name = name;
        this.player = player;
        this.attributes = attributes;
        this.movementPattern = movementPattern;

        switch (movementPattern){
            case LINEAR -> {
                this.moveValidator = MoveChecker.linearValidator;
                this.pathValidator = PathChecker.linearPathValidator;
            }
            case ORTHOGONAL -> {
                this.moveValidator = MoveChecker.orthogonalValidator;
                this.pathValidator = PathChecker.orthogonalPathValidator;
            }
            case OMNI -> {
                this.moveValidator = MoveChecker.omniValidator;
                this.pathValidator = PathChecker.omniPathValidator;
            }
            case DIAGONAL -> {
                this.moveValidator = MoveChecker.diagonalValidator;
                this.pathValidator = PathChecker.diagonalPathValidator;
            }

        }

        for (PieceAttribute piece: attributes) {
            switch (piece.getId()){
                case DISTANCE -> distance = piece.getValue();
                case FLY -> fly = true;
                case JUMP -> jump = true;
                case UNBLOCK -> unblock = true;
                case VALUE -> value = piece.getValue();
            }
        }

    }

    /**
     * @return the name
     */
    @Override
    public PieceName getName() {
        return name;
    }

    /**
     * @return the owning player
     */
    @Override
    public String getPlayer() {
        return player;
    }

    /**
     *
     * @return the piece value
     */
    public int getValue(){
        return value;
    }

    public MovementPattern getMovementPattern(){
        return movementPattern;
    }

    public PieceAttribute[] getAttributes(){
        return attributes;
    }

    /**
     * Checks if the piece can move
     * @param from the starting location
     * @param to the ending location
     * @return true if the piece can move from the starting location to the end location, false otherwise
     */
    public boolean canMove(Coordinate from, Coordinate to){
       return moveValidator.isLegalMovePattern((TileShapeCoordinate) from, (TileShapeCoordinate)to) && pathValidator.isLegalPath((TileShapeCoordinate)from, (TileShapeCoordinate)to, distance, fly, jump, unblock);
    }
}
