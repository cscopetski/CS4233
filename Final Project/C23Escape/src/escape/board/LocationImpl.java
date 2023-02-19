package escape.board;

import escape.board.Location;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

public class LocationImpl implements Location {

    private EscapePiece piece;

    private LocationType locationType;

    public LocationImpl(EscapePiece piece, LocationType locationType){
        this.piece = piece;

        this.locationType = locationType;
    }

    /**
     * Get the piece at this location
     * @return The piece at this location
     */
    @Override
    public EscapePiece getPiece() {
        return piece;
    }

    /**
     * Set the piece at this location
     * @param piece The piece to set
     */
    @Override
    public void setPiece(EscapePiece piece) {
        this.piece = piece;
    }

    /**
     * Get the type of this location
     * @return
     */
    @Override
    public LocationType getLocationType() {
        return locationType;
    }
}
