package escape;

import escape.board.Board;
import escape.board.TileShapeCoordinate;
import escape.movement.MoveChecker;
import escape.movement.MoveValidator;
import escape.movement.PathChecker;
import escape.movement.PathValidator;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.PieceAttribute;

import java.util.Arrays;

public class EscapePieceImpl implements EscapePiece {

    private PieceName name;
    private String player;
    private MovementPattern movementPattern;
    private PieceAttribute[] attributes;
    private MoveValidator moveValidator;
    private PathValidator pathValidator;
    private int distance;
    private boolean fly;
    private boolean jump;
    private boolean unblock;
    private int value;

    public EscapePieceImpl(PieceName name, String player, MovementPattern movementPattern, PieceAttribute[] attributes){
        this.name = name;
        this.player = player;
        this.attributes = attributes;

        switch (movementPattern){
            //TODO: add rest of movement patterns
            case LINEAR -> {
                this.moveValidator = MoveChecker.linearValidator;
                this.pathValidator = PathChecker.linearPathValidator;
            }
            case ORTHOGONAL -> this.moveValidator = MoveChecker.orthogonalValidator;
            case OMNI -> this.moveValidator = MoveChecker.omniValidator;
            case DIAGONAL -> this.moveValidator = MoveChecker.diagonalValidator;
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
    public boolean canMove(TileShapeCoordinate from, TileShapeCoordinate to){
       return moveValidator.isLegalMovePattern(from, to) && pathValidator.isLegalPath(from, to, distance, fly, jump, unblock);
    }
}
