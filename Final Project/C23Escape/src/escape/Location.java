package escape;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

public interface Location<C extends Coordinate> {

    EscapePiece getPiece();

    C getCoordinate();

    LocationType getLocationType();

}
