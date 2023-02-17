package escape.board;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

public interface Location<C extends Coordinate> {

    EscapePiece getPiece();

    void setPiece(EscapePiece piece);

    C getCoordinate();

    LocationType getLocationType();

}
