package escape.board;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

public interface Location {

    /**
     * Get the piece at this location
     * @return The piece at this location
     */
    EscapePiece getPiece();

    /**
     * Set the piece at this location
     * @param piece The piece to set
     */
    void setPiece(EscapePiece piece);

    /**
     * Get the type of this location
     * @return
     */
    LocationType getLocationType();

}
