package escape.board;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

public interface Location {

    EscapePiece getPiece();

    void setPiece(EscapePiece piece);

    LocationType getLocationType();

}
