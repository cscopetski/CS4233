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

    @Override
    public EscapePiece getPiece() {
        return piece;
    }

    @Override
    public void setPiece(EscapePiece piece) {
        this.piece = piece;
    }

    @Override
    public LocationType getLocationType() {
        return locationType;
    }
}
