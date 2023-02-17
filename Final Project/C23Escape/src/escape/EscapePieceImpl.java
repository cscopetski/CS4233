package escape;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.PieceAttribute;

public class EscapePieceImpl implements EscapePiece {

    private PieceName name;
    private String player;
    private MovementPattern movementPattern;
    private PieceAttribute[] attributes;

    public EscapePieceImpl(PieceName name, String player, MovementPattern movementPattern, PieceAttribute[] attributes){
        this.name = name;
        this.player = player;
        this.movementPattern = movementPattern;
        this.attributes = attributes;
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

}
